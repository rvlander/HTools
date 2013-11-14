/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.hollermap.algorithms;

import htools.hollermap.traces.STrace;
import Jama.Matrix;
import htools.hollermap.MCJImports.AlgoMethods;
import htools.hollermap.MCJImports.Wdouble;
import htools.core.traces.Trace;
import htools.hollermap.mat2java.Functions;
import htools.hollermap.mat2java.Signal;
import htools.hollermap.mat2java.StdStats;
import htools.hollermap.mat2java.plotter.PlotType;
import htools.hollermap.mat2java.plotter.Plotter;
import java.awt.Color;
import java.io.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mc.mcjavacore.MCJMatrixDimensionsException;

/**
 *
 * @author rvlander
 */
public class TraceAnalyzerMCJ extends TraceAnalyzer implements Serializable {

    static final long serialVersionUID = 1515L;
    private double c;

    public TraceAnalyzerMCJ(Trace t, double angl, boolean c_zero) {
        super(t, 0, true);
    }

    //return la constante des déplacement en x
    public double getC() {
        return c;
    }

    @Override
    public STrace computeAll() {
        try {
            
            this.sampling = trace.T;
            
            
            crx = new CrossingReturn();
            cry = new CrossingReturn();


            Wdouble nT = new Wdouble();
            Wdouble dXsdT = new Wdouble();
            Wdouble dYsdT = new Wdouble();
            Wdouble ind0xs = new Wdouble();
            Wdouble t0xs = new Wdouble();
            Wdouble x0s = new Wdouble();
            Wdouble ind0ys = new Wdouble();
            Wdouble t0ys = new Wdouble();
            Wdouble y0s = new Wdouble();
            Wdouble c = new Wdouble();
            Wdouble filtereddYsdT = new Wdouble();

            AlgoMethods.init(trace.T, trace.X, trace.Y,
                    nT, dXsdT, dYsdT, ind0xs, t0xs, x0s, ind0ys, t0ys, y0s, c, filtereddYsdT);



            Wdouble a = new Wdouble();
            Wdouble b = new Wdouble();
            Wdouble wx = new Wdouble();
            Wdouble wy = new Wdouble();
            Wdouble phix = new Wdouble();
            Wdouble phiy = new Wdouble();
            AlgoMethods.direct_method(dXsdT.v, dYsdT.v, t0xs.v, t0ys.v, ind0xs.v, ind0ys.v,
                    a, b, wx, wy, phix, phiy);

            Wdouble nX = new Wdouble();
            Wdouble nY = new Wdouble();
            Wdouble signalx = new Wdouble();
            Wdouble signaly = new Wdouble();
            Wdouble tanB = new Wdouble();
            Wdouble psi = new Wdouble();
            double[] X0 = {trace.X[0]};
            double[] Y0 = {trace.Y[0]};
            AlgoMethods.resample_hw_sin(this.sampling, t0xs.v, t0ys.v, a.v, b.v, wx.v, wy.v, phix.v, phiy.v, c.v, X0, Y0, nX, nY, signalx, signaly, tanB, psi);

            this.nT = nT.v;
            this.sampledx = signalx.v;
            this.sampledy = signaly.v;
            this.samplex = nX.v;
            this.sampley = nY.v;
            
            
            this.dXsdT = dXsdT.v;
            this.dYsdT = dYsdT.v;

            crx.dind = ind0xs.v;
            crx.t0 = t0xs.v;
            crx.s0 = x0s.v;

            cry.dind = ind0ys.v;
            cry.t0 = t0ys.v;
            cry.s0 = y0s.v;


            this.Ox_x = new double[crx.t0.length];
            this.Ox_y = new double[crx.t0.length];
            for (int i = 0; i < crx.t0.length; i++) {
                int ix = Functions.getLesser(trace.T, crx.t0[i]);
                this.Ox_x[i] = samplex[ix];
                this.Ox_y[i] = sampley[ix];
            }

            this.Oy_x = new double[cry.t0.length];
            this.Oy_y = new double[cry.t0.length];
            for (int i = 0; i < cry.t0.length; i++) {
                int iy = Functions.getLesser(trace.T, cry.t0[i]);
                this.Oy_x[i] = samplex[iy];
                this.Oy_y[i] = sampley[iy];
            }

            
          //on pack le résultat
        STrace st = new STrace();
        st.a = a.v;
        st.b = b.v;
        st.phix = phix.v;
        st.phiy = phiy.v;
        st.tx = t0xs.v;
        st.ty = t0ys.v;
        st.wx = wx.v;
        st.wy = wy.v;
        st.c = c.v[0];


        //on reconstrui les signaux et les affichent

        int[] indices = Functions.borders(trace.Z);
        st.tz = new Matrix(trace.T, trace.T.length).getMatrix(indices, Functions.trivialIndex()).getColumnPackedCopy();
        st.down = new Matrix(trace.Z, trace.Z.length).getMatrix(indices, Functions.trivialIndex()).getColumnPackedCopy();


        st.startx = trace.X[0];
        st.starty = trace.Y[0];
        st.tfinal = trace.T[trace.T.length - 1];


        this.strace = st;    

           


        } catch (MCJMatrixDimensionsException ex) {
            Logger.getLogger(TraceAnalyzerMCJ.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (Exception ex) {
            Logger.getLogger(TraceAnalyzerMCJ.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (Throwable ex) {
            Logger.getLogger(TraceAnalyzerMCJ.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        
        tap.plot();

        return new STrace();

    }

    @Override
    public String toString() {
        String fin = "C not null";
        if (c_zero) {
            fin = "C null";
        }
        return "TraceAnalyzerMCJ, Axes Angle = " + this.angle_axes + ", " + fin;
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {

        BufferedReader br = new BufferedReader(new FileReader("/home/rvlander/Desktop/trace_enregistree_"));
        br.readLine();
        Trace t = Trace.read(br);
        /*
         * PrintWriter out = new PrintWriter(new
         * File("/home/rvlander/Desktop/cptrac1e_")); t.export(new
         * PrintWriter(out)); out.close();
         */


        TraceAnalyzerMCJ ma = new TraceAnalyzerMCJ(t, 0, false);
        ma.computeAll();
    }
    public int getType(){
        return TraceAnalyzer.MCJ_TYPE;
    }
}
