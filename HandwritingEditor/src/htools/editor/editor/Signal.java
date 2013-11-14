/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.editor.editor;

import htools.editor.matlabfunctions.MatlabMethods;
import java.util.logging.Level;
import java.util.logging.Logger;
import matlabcontrol.MatlabInvocationException;

/**
 *
 * @author rvlander
 */
public class Signal {

    public double[] signal;

    public Signal(double[] data) {
        this.signal = data;
    }

    public double[] getSignal() {
        return signal;
    }

    public Signal copy() {
        int n = signal.length;
        double[] ndata = new double[n];
        for (int i = 0; i < n; i++) {
            ndata[i] = signal[i];
        }
        return new Signal(ndata);
    }

    public void setSignal(double[] sig) {
        this.signal = sig;
    }

    public void moveAndTimeShift(double[] time, double[] selection, double dt, double dx) {

        try {
            this.signal = MatlabMethods.moveSignal(this.signal, selection, dx);
            this.signal = MatlabMethods.timeShift(time, selection, this.signal, dt);
        } catch (MatlabInvocationException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void reSize(double[] time, double[] selection, double orit, double ratio_t, double orix, double ratio_x) {

        try {

            this.signal = MatlabMethods.reSizeHeight(this.signal, selection, orix, ratio_x);
            this.signal = MatlabMethods.reSizeWidth(time, selection, this.signal, orit, ratio_t);

        } catch (MatlabInvocationException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void smooth(double[] time) {
        try {
            this.signal = MatlabMethods.smooth(time, this.signal);

        } catch (MatlabInvocationException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void smooth(double[] time, double[] selection) {

        try {
            this.signal = MatlabMethods.smooth(time, selection, this.signal);

        } catch (MatlabInvocationException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public void handSignal(double[] this_time, double[] time, double[] ampli) {

            try {
                this.signal = MatlabMethods.handSignal(this_time, this.signal, time, ampli);

            } catch (MatlabInvocationException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

    public void delete(double[] time,double[] selection) {
    }
    
     public void append(double toff,double[] T, double[] t0, double[] a, double[] w, double phi0, double c) {
    }
    
    
}
