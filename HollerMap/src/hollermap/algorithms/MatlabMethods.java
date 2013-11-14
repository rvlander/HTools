/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hollermap.algorithms;

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

}
