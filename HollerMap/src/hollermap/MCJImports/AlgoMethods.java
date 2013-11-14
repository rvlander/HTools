/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hollermap.MCJImports;

import org.mc.mcfunctions.MCJHelpers;
import org.mc.mcfunctions.MCJOutputArg;
import org.mc.mcjavacore.MCJMatrixDimensionsException;

/**
 *
 * @author rvlander
 */
public class AlgoMethods {

    public static void init(/*
             * in
             */double[] T, double[] X, double[] Y,/*
             * out
             */ Wdouble nT, Wdouble dXsdT,
            Wdouble dYsdT, Wdouble ind0xs, Wdouble t0xs, Wdouble x0s, Wdouble ind0ys,
            Wdouble t0ys, Wdouble y0s, Wdouble c, Wdouble filtereddYsdT) throws MCJMatrixDimensionsException, Exception, Throwable {

        double[][][] in_args = {MCJHelpers.VectorFromArray(T, true),
            MCJHelpers.VectorFromArray(X, true),
            MCJHelpers.VectorFromArray(Y, true),
            new double[][]{{1.0}},
            new double[][]{{0.0}}    
        };

        MCJOutputArg[] out_args = MCJHelpers.createOutputArgArray(12);

        init.init(out_args, in_args);

        nT.v = MCJHelpers.ArrayFromVector(out_args[0].val);
        dXsdT.v = MCJHelpers.ArrayFromVector(out_args[1].val);
        dYsdT.v = MCJHelpers.ArrayFromVector(out_args[2].val);
        ind0xs.v = MCJHelpers.ArrayFromVector(out_args[3].val);
        t0xs.v = MCJHelpers.ArrayFromVector(out_args[4].val);
        x0s.v = MCJHelpers.ArrayFromVector(out_args[5].val);
        ind0ys.v = MCJHelpers.ArrayFromVector(out_args[6].val);
        t0ys.v = MCJHelpers.ArrayFromVector(out_args[7].val);
        y0s.v = MCJHelpers.ArrayFromVector(out_args[8].val);
        c.v = MCJHelpers.ArrayFromVector(out_args[9].val);
        filtereddYsdT.v = MCJHelpers.ArrayFromVector(out_args[11].val);


    }

    public static void direct_method(/*
             * in
             */double[] dXsdT,
            double[] dYsdT, double[] t0xs, double[] t0ys, double[] ind0xs, double[] ind0ys,
            /*
             * out
             */
            Wdouble a, Wdouble b, Wdouble wx, Wdouble wy, Wdouble phix, Wdouble phiy) throws MCJMatrixDimensionsException, Exception, Throwable {

        double[][][] in_args = {
            MCJHelpers.VectorFromArray(dXsdT, true),
            MCJHelpers.VectorFromArray(dYsdT, true),
            MCJHelpers.VectorFromArray(t0xs, true),
            MCJHelpers.VectorFromArray(t0ys, true),
            MCJHelpers.VectorFromArray(ind0xs, true),
            MCJHelpers.VectorFromArray(ind0ys, true)
        };

        MCJOutputArg[] out_args = MCJHelpers.createOutputArgArray(11);

        direct_method.direct_method(out_args, in_args);
        a.v = MCJHelpers.ArrayFromVector(out_args[0].val);
        b.v = MCJHelpers.ArrayFromVector(out_args[1].val);
        wx.v = MCJHelpers.ArrayFromVector(out_args[2].val);
        wy.v = MCJHelpers.ArrayFromVector(out_args[3].val);
        phix.v = MCJHelpers.ArrayFromVector(out_args[4].val);
        phiy.v = MCJHelpers.ArrayFromVector(out_args[5].val);
    }

    public static void resample_hw_sin(/*
             *
             * [nX,nY,signalx,signaly,tanB,psi] =
             * resample_hw_sin(sampling,t0xs,t0ys,a,b,wx,wy,phix,phiy,c,X0,Y0)
             * in
             */double[] sampling, double[] t0xs, double[] t0ys, double[] a, double[] b,
            double[] wx, double[] wy, double[] phix, double[] phiy, double[] c, double[] X0, double[] Y0,
            /*
             * out
             */
            Wdouble nX, Wdouble nY, Wdouble signalx, Wdouble signaly, Wdouble tanB, Wdouble psi) throws MCJMatrixDimensionsException, Exception, Throwable {

        double[][][] in_args = {
            MCJHelpers.VectorFromArray(sampling, true),
            MCJHelpers.VectorFromArray(t0xs, true),
            MCJHelpers.VectorFromArray(t0ys, true),
            MCJHelpers.VectorFromArray(a, true),
            MCJHelpers.VectorFromArray(b, true),
            MCJHelpers.VectorFromArray(wx, true),
            MCJHelpers.VectorFromArray(wy, true),
            MCJHelpers.VectorFromArray(phix, true),
            MCJHelpers.VectorFromArray(phiy, true),
            MCJHelpers.VectorFromArray(c, true),
            new double[][]{{0}},
            MCJHelpers.VectorFromArray(X0, true),
            MCJHelpers.VectorFromArray(Y0, true),
        };

        MCJOutputArg[] out_args = MCJHelpers.createOutputArgArray(11);

        resample_hw_sin.resample_hw_sin(out_args, in_args);
        nX.v = MCJHelpers.ArrayFromVector(out_args[0].val);
        nY.v = MCJHelpers.ArrayFromVector(out_args[1].val);
        signalx.v = MCJHelpers.ArrayFromVector(out_args[2].val);
        signaly.v = MCJHelpers.ArrayFromVector(out_args[3].val);
        tanB.v = MCJHelpers.ArrayFromVector(out_args[4].val);
        psi.v = MCJHelpers.ArrayFromVector(out_args[5].val);
    }
    
    
     public static void twothird(/*
              * in
             */double[] T, double[] X, double[] Y,/*
             * out
             */ Wdouble nX, Wdouble nY,Wdouble t,Wdouble y,Wdouble a,Wdouble b
           ) throws MCJMatrixDimensionsException, Exception, Throwable {

        double[][][] in_args = {MCJHelpers.VectorFromArray(T, true),
            MCJHelpers.VectorFromArray(X, true),
            MCJHelpers.VectorFromArray(Y, true)};

        MCJOutputArg[] out_args = MCJHelpers.createOutputArgArray(11);

        twothird.twothird(out_args, in_args);
        nX.v = MCJHelpers.ArrayFromVector(out_args[0].val);
        nY.v = MCJHelpers.ArrayFromVector(out_args[1].val);
        t.v = MCJHelpers.ArrayFromVector(out_args[2].val);
        y.v = MCJHelpers.ArrayFromVector(out_args[3].val);
        a.v = MCJHelpers.ArrayFromVector(out_args[4].val);
        b.v = MCJHelpers.ArrayFromVector(out_args[5].val);
    }
}
