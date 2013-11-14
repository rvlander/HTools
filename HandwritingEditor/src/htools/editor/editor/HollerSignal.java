/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.editor.editor;

import htools.editor.matlabfunctions.MatlabMethods;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import matlabcontrol.MatlabInvocationException;

/**
 *
 * @author rvlander
 */
public class HollerSignal extends Signal {

    public double c;
    public double phi0;
    public double[] a;
    public double[] w;
    public double[] t0;
    public double[] T;

    public HollerSignal(double[] T, double[] t0, double[] a, double[] w, double phi0, double c) {
        super(null);
        this.T = T;
        this.c = c;
        this.a = a;
        this.w = w;
        this.t0 = t0;
        this.phi0 = phi0;
        try {
            signal = MatlabMethods.reconstructHollerSignal(T, t0, a, w, phi0, c);
        } catch (MatlabInvocationException ex) {
            Logger.getLogger(HollerSignal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static double[] copyTab(double[] signal) {
        int n = signal.length;
        double[] ndata = new double[n];
        for (int i = 0; i < n; i++) {
            ndata[i] = signal[i];
        }
        return ndata;
    }
    
    public static double[]  appendTab(double[] ori,double[] toadd,double off) {
        int m = ori.length;
        int n = m+toadd.length;
        double[] res = Arrays.copyOf(ori, n);
        
        for (int i = 0; i < toadd.length; i++) {
            res[m+i] = toadd[i]+off;
        }
        return res;
    }

    
    public static double[]  appendTabminus1(double[] ori,double[] toadd,double off) {
        int m = ori.length-1;
        int n = m+toadd.length;
        double[] res = Arrays.copyOf(ori, n);
        
        for (int i = 1; i < toadd.length; i++) {
            res[i+m] = toadd[i]+off;
        }
        return res;
    }
    
    @Override
    public Signal copy() {
        return new HollerSignal(copyTab(T), copyTab(t0), copyTab(a), copyTab(w), phi0, c);
    }

    @Override
    public void moveAndTimeShift(double[] time, double[] selection, double dt, double dx) {
        try {
            ArrayList<double[]> res = MatlabMethods.moveHollerPoints(T, selection, t0, dt);
            this.t0 = res.get(0);
            this.w = res.get(1);
            //System.out.println(Arrays.toString(this.t0));
            signal = MatlabMethods.reconstructHollerSignal(T, t0, a, w, phi0, c);
        } catch (MatlabInvocationException ex) {
            Logger.getLogger(HollerSignal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void reSize(double[] time, double[] selection, double orit, double ratio_t, double orix, double ratio_x) {

        try {

            this.a = MatlabMethods.reSizeHollerHeight(time, selection, t0, a, orix, ratio_x);

            ArrayList<double[]> res = MatlabMethods.reSizeHollertWidth(time, selection, t0, orit, ratio_t);
            this.t0 = res.get(0);
            this.w = res.get(1);

            signal = MatlabMethods.reconstructHollerSignal(T, t0, a, w, phi0, c);
        } catch (MatlabInvocationException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void smooth(double[] time) {
    }

    @Override
    public void smooth(double[] time, double[] selection) {
    }

    @Override
    public void handSignal(double[] this_time, double[] time, double[] ampli) {
    }

    @Override
    public void delete(double[] time, double[] selection) {
        try {
            ArrayList<double[]> res = MatlabMethods.deleteHollerPoints(time, selection, t0, a, w);
            this.t0 = res.get(0);
            this.a = res.get(1);
            this.w = res.get(2);

            signal = MatlabMethods.reconstructHollerSignal(T, t0, a, w, phi0, c);
        } catch (MatlabInvocationException ex) {
            Logger.getLogger(HollerSignal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    @Override
    public void append(double toff, double[] T, double[] t0, double[] a, double[] w, double phi0, double c) {
        this.T = T;
        this.a = appendTab(this.a, a, 0);
        this.w = appendTab(this.w, w, 0);
        this.t0 = appendTabminus1(this.t0, t0, toff); 
        try {
            signal = MatlabMethods.reconstructHollerSignal(this.T, this.t0, this.a, this.w, this.phi0, this.c);
        } catch (MatlabInvocationException ex) {
            Logger.getLogger(HollerSignal.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
