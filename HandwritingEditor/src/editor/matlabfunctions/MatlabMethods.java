/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.matlabfunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;
import matlabcontrol.RemoteMatlabProxyFactory;
import matlabcontrol.extensions.MatlabNumericArray;
import matlabcontrol.extensions.MatlabTypeConverter;

/**
 *
 * @author rvlander
 */
public class MatlabMethods {

    private static MatlabProxy proxy;
    private static MatlabTypeConverter processor;
    private static Process process;

    public static void init() {
        try {
            MatlabProxyFactoryOptions options = new MatlabProxyFactoryOptions.Builder()
                    //.setHidden(true)
                    // .setProxyTimeout(30000L)
                    .build();
            MatlabProxyFactory factory = new MatlabProxyFactory(options);
            proxy = factory.getProxy();
            process = ((RemoteMatlabProxyFactory) factory.getProxyFactory()).getProcess();
            try {
                proxy.eval("path(path,'./m_includes/')");
                proxy.eval("path(path,'./m_includes/Base/')");
            } catch (MatlabInvocationException ex) {
                Logger.getLogger(MatlabMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
            processor = new MatlabTypeConverter(proxy);
        } catch (MatlabConnectionException ex) {
            Logger.getLogger(MatlabMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void close() {
        proxy.disconnect();
        System.out.println("Destroy process");
        process.destroy();
    }

    public static double[] moveSignal(double[] X, double[] mask, double dx) throws MatlabInvocationException {

        proxy.setVariable("X", X);
        proxy.setVariable("mask", mask);
        proxy.setVariable("dx", dx);
        proxy.eval("nX = moveSignal(X,mask,dx);");
        double[] res = (double[]) proxy.getVariable("nX");



        return res;
    }

    /*   public static void testMatlabControl() throws MatlabInvocationException {
     double[][] array = new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
     System.out.println("Original: ");
     for (int i = 0; i < array.length; i++) {
     System.out.println(Arrays.toString(array[i]));
     }

     //Send the array to MATLAB, transpose it, then retrieve it and convert it to a 2D double array

     processor.setNumericArray("array", new MatlabNumericArray(array, null));
     proxy.eval("array = transpose(array)");
     double[][] transposedArray = processor.getNumericArray("array").getRealArray2D();

     //Print the returned array, now transposed
     System.out.println("Transposed: ");
     for (int i = 0; i < transposedArray.length; i++) {
     System.out.println(Arrays.toString(transposedArray[i]));
     }

     }*/
    public static double[] timeShift(double[] sampling, double[] mask, double[] X, double dt) throws MatlabInvocationException {

        proxy.setVariable("sampling", sampling);
        proxy.setVariable("mask", mask);
        proxy.setVariable("X", X);
        proxy.setVariable("dt", dt);
        proxy.eval("nX = timeShift(sampling,mask,X,dt);");

        double[] res = (double[]) proxy.getVariable("nX");

        return res;
    }

    public static double[] derive(double[] T, double[] X) throws MatlabInvocationException {

        proxy.setVariable("sampling", T);
        proxy.setVariable("X", X);
        proxy.eval("V = derive(sampling,X);");

        double[] res = (double[]) proxy.getVariable("V");

        return res;
    }

    public static double[] integre(double[] T, double[] V, double x0) throws MatlabInvocationException {

        proxy.setVariable("sampling", T);
        proxy.setVariable("V", V);
        proxy.setVariable("X0", x0);
        proxy.eval("X = integre(sampling,V,X0);");

        double[] res = (double[]) proxy.getVariable("X");

        return res;
    }

    public static double[] smooth(double[] X, double[] Y) throws MatlabInvocationException {

        proxy.setVariable("X", X);
        proxy.setVariable("Y", Y);
        proxy.eval("R = smooth(X,Y,11);");

        double[] res = (double[]) proxy.getVariable("R");

        return res;
    }

    public static double[] smooth(double[] X, double[] mask, double[] Y) throws MatlabInvocationException {

        proxy.setVariable("X", X);
        proxy.setVariable("mask", mask);
        proxy.setVariable("Y", Y);
        proxy.eval("R = local_smooth(X,mask,Y,5);");

        double[] res = (double[]) proxy.getVariable("R");

        return res;
    }

    public static double mean(double[] x, double[] selection) throws MatlabInvocationException {
        proxy.setVariable("X", x);
        proxy.setVariable("mask", selection);
        proxy.eval("m = lemean(X,mask);");

        double[] res = (double[]) proxy.getVariable("m");

        return res[0];
    }

    public static double[] moveSelection(double[] selection, double dt, double[] x) throws MatlabInvocationException {
        proxy.setVariable("mask", selection);
        proxy.setVariable("sampling", x);
        proxy.setVariable("dt", dt);
        proxy.eval("R = moveSelection(sampling,mask,dt);");

        double[] res = (double[]) proxy.getVariable("R");

        return res;
    }

    public static double[] reSizeHeight(double[] X, double[] selection, double orix, double ratio_x) throws MatlabInvocationException {
        proxy.setVariable("X", X);
        proxy.setVariable("mask", selection);
        proxy.setVariable("ox", orix);
        proxy.setVariable("rx", ratio_x);
        proxy.eval("nX = resizeSignal(X,mask,ox,rx);");
        double[] res = (double[]) proxy.getVariable("nX");

        return res;
    }

    public static double[] reSizeWidth(double[] sampling, double[] mask, double[] X, double orit, double ratio_t) throws MatlabInvocationException {
        proxy.setVariable("sampling", sampling);
        proxy.setVariable("mask", mask);
        proxy.setVariable("X", X);
        proxy.setVariable("ot", orit);
        proxy.setVariable("rt", ratio_t);
        proxy.eval("nX = resizeTime(sampling,mask,X,ot,rt);");

        double[] res = (double[]) proxy.getVariable("nX");

        return res;
    }

    public static double[] resizeSelection(double[] sampling, double[] mask, double ot, double rt) throws MatlabInvocationException {
        proxy.setVariable("mask", mask);
        proxy.setVariable("sampling", sampling);
        proxy.setVariable("ot", ot);
        proxy.setVariable("rt", rt);
        proxy.eval("R = resizeSelection(sampling,mask,ot,rt);");

        double[] res = (double[]) proxy.getVariable("R");

        return res;
    }

    public static double[] handSignal(double[] sampling, double[] X, double[] time, double[] ampli) throws MatlabInvocationException {
        proxy.setVariable("sampling", sampling);
        proxy.setVariable("X", X);
        proxy.setVariable("time", time);
        proxy.setVariable("ampli", ampli);
        proxy.eval("R = handSignal(sampling,X,time,ampli);");

        double[] res = (double[]) proxy.getVariable("R");

        return res;
    }

    public static ArrayList<double[]> quickDM(double[] T, double[] X, double[] Y) throws MatlabInvocationException {
        proxy.setVariable("T", T);
        proxy.setVariable("X", X);
        proxy.setVariable("Y", Y);
        proxy.eval("[t0xs,t0ys,a,b,wx,wy,phix,phiy,c] = quickDM(T,X,Y);");

        ArrayList<double[]> ret = new ArrayList();


        ret.add((double[]) proxy.getVariable("t0xs"));
        ret.add((double[]) proxy.getVariable("t0ys"));
        ret.add((double[]) proxy.getVariable("a"));
        ret.add((double[]) proxy.getVariable("b"));
        ret.add((double[]) proxy.getVariable("wx"));
        ret.add((double[]) proxy.getVariable("wy"));
        ret.add((double[]) proxy.getVariable("phix"));
        ret.add((double[]) proxy.getVariable("phiy"));
        ret.add((double[]) proxy.getVariable("c"));


        return ret;
    }

    public static double[] reconstructHollerSignal(double[] T, double[] t0, double[] a, double[] w, double phi0, double c) throws MatlabInvocationException {
        proxy.setVariable("T", T);
        proxy.setVariable("t0xs", t0);
        proxy.setVariable("a", a);
        proxy.setVariable("wx", w);
        proxy.setVariable("phix0", phi0);
        proxy.setVariable("c", c);


        proxy.eval("R = resample_hw_sin_nophi_onedir(T,t0xs,a,wx,phix0,c);");

        double[] res = (double[]) proxy.getVariable("R");

        return res;
    }

    public static double[] selectedHollerPoints(double[] T, double[] selection, double[] t0) throws MatlabInvocationException {
        proxy.setVariable("sampling", T);
        proxy.setVariable("t0s", t0);
        proxy.setVariable("selection", selection);

        proxy.eval("[indexes,maski] = selectHollerPoints(sampling,selection,t0s);");

        double[] res = (double[]) proxy.getVariable("maski");

        return res;
    }

    public static ArrayList<double[]> moveHollerPoints(double[] T, double[] selection, double[] t0s, double dt) throws MatlabInvocationException {

        proxy.setVariable("mask", selection);
        proxy.setVariable("dt", dt);
        proxy.setVariable("T", T);
        proxy.setVariable("t0s", t0s);
        proxy.eval("[R,w] = moveHollerPoints(T,mask,t0s,dt);");

        ArrayList<double[]> ret = new ArrayList();

        ret.add((double[]) proxy.getVariable("R"));
        ret.add((double[]) proxy.getVariable("w"));

        return ret;
    }

    public static double[] reSizeHollerHeight(double[] time, double[] selection, double[] t0, double[] a, double orix, double ratio_x) throws MatlabInvocationException {
        proxy.setVariable("sampling", time);
        proxy.setVariable("selection", selection);
        proxy.setVariable("t0", t0);
        proxy.setVariable("a", a);
        proxy.setVariable("ox", orix);
        proxy.setVariable("rx", ratio_x);
        proxy.eval("nA = resizeHollerAmplitudes(sampling,mask,t0,a,ox,rx);");

        double[] res = (double[]) proxy.getVariable("nA");

        return res;
    }

    public static ArrayList<double[]> reSizeHollertWidth(double[] time, double[] selection, double[] t0, double orit, double ratio_t) throws MatlabInvocationException {
        proxy.setVariable("sampling", time);
        proxy.setVariable("selection", selection);
        proxy.setVariable("t0", t0);
        proxy.setVariable("ot", orit);
        proxy.setVariable("rt", ratio_t);
        proxy.eval("[R,w] = resizeHollerTimes(sampling,mask,t0,ot,rt);");
    
        
        ArrayList<double[]> ret = new ArrayList();

        ret.add((double[]) proxy.getVariable("R"));
        ret.add((double[]) proxy.getVariable("w"));

        return ret;
    }

    public static ArrayList<double[]> deleteHollerPoints(double[] time, double[] selection, double[] t0, double[] a, double[] w) throws MatlabInvocationException {
        proxy.setVariable("sampling", time);
        proxy.setVariable("selection", selection);
        proxy.setVariable("t0", t0);
        proxy.setVariable("a", a);
        proxy.setVariable("w", w);
        proxy.eval("[t0,a,w] = deleteHollerPoints(sampling,selection,t0,a,w);");
    
        
        ArrayList<double[]> ret = new ArrayList();

        ret.add((double[]) proxy.getVariable("t0"));
        ret.add((double[]) proxy.getVariable("a"));
        ret.add((double[]) proxy.getVariable("w"));
        
        return ret;
    }
}
