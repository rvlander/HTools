/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.hollermap.algorithms;

import htools.hollermap.traces.STrace;
import Jama.Matrix;
import htools.core.traces.Trace;
import htools.hollermap.mat2java.Functions;
import htools.hollermap.mat2java.Signal;
import htools.hollermap.mat2java.StdStats;
import htools.hollermap.mat2java.plotter.PlotType;
import htools.hollermap.mat2java.plotter.Plotter;
import htools.hollermap.output.AnalyzerPlotter;
import htools.hollermap.output.TraceAnalyzerPlotter;
import java.awt.Color;
import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author rvlander
 */
public class TraceAnalyzer implements Serializable {

    public static final int DEFAULT_TYPE = 0;
    public static final int MCJ_TYPE = 1;
    public static final int TTL_TYPE = 2;
    
    public static String[] getTypes(){
        return new String[]{"TraceAnalyzer","TraceAnalyzerMCJ","TwoThird"};
    }
    
    public static int typeFromString(String s){
        if(s.equals("TraceAnalyzer")) return DEFAULT_TYPE;
        if(s.equals("TraceAnalyzerMCJ")) return MCJ_TYPE;
        if(s.equals("TwoThird")) return TTL_TYPE;
        return DEFAULT_TYPE;
        
    }
    
    
    AnalyzerPlotter tap;
    
    static final long serialVersionUID = 1515L;
    protected int sampling_step = 20;
    protected Trace trace;
    protected STrace strace;
    protected double[] dXsdT;
    protected double[] dYsdT;
    protected double[] fdXsdT;
    protected double[] fdYsdT;
    protected double magic = (1. / 2.) * (4. + Math.sqrt(2.) * Math.sqrt(-8. + Math.pow(Math.PI, 2.))) / Math.PI;

    public double[] getFdXsdT() {
        return fdXsdT;
    }

    public double[] getFdYsdT() {
        return fdYsdT;
    }
    protected double[] nT;
    protected double[] sampledx;
    protected double[] sampledy;
    protected double[] samplex;
    protected double[] sampley;
    protected double[] Ox_x;
    protected double[] Ox_y;
    protected double[] Oy_x;
    protected double[] Oy_y;
    protected CrossingReturn crx;
    protected CrossingReturn cry;
    protected double[] sampling;
    protected boolean c_zero;
    protected double angle_axes;
    protected int n;

    public TraceAnalyzer(Trace t, double angle, boolean c_zero) {

        //&System.out.println(magic);
        trace = t;
        // System.out.println("TA creation : "+Arrays.toString(t.Y));
        if (t != null) {
            n = trace.T.length;
        }
        this.angle_axes = angle;
        this.c_zero = c_zero;
        tap = new TraceAnalyzerPlotter(this);
    }

    public AnalyzerPlotter getTraceAnalyzerPlotter() {
        return tap;
    }

    //return la constante des déplacement en x
    public double getC() {
        if (!c_zero) {
            return (trace.X[n - 1] - trace.X[0]) / (trace.T[n - 1] - trace.T[0]);
        } else {
            return 0;
        }
    }

    public STrace computeAll() {

        //Affichage
        Plotter plot = new Plotter();

        //calcul de c
        double c = 0;
        if (!c_zero) {
            c = getC();
        }

        double cx = Functions.rotateX(angle_axes, c, 0);
        double cy = Functions.rotateY(angle_axes, c, 0);

        //System.out.println(c+" "+cx +" "+cy);


        double[] tempX = Functions.rotateX(angle_axes, trace.X, trace.Y);
        double[] tempY = Functions.rotateY(angle_axes, trace.X, trace.Y);




        /*
         * System.out.println("trace.T " + Functions.affiche(trace.T));
         * System.out.println("trace.X " + Functions.affiche(trace.X));
         * System.out.println("trace.Y " + Functions.affiche(trace.Y));
         */

        //affichage





        //matrice T
        Matrix T = new Matrix(trace.T, trace.T.length);

        //dérivées
        Matrix dT = new Matrix(Functions.diff(trace.T), trace.T.length - 1);
        Matrix dX = new Matrix(Functions.diff(tempX), trace.T.length - 1);
        Matrix dY = new Matrix(Functions.diff(tempY), trace.T.length - 1);

        /*
         * old Matrix dXsdT = dX.arrayRightDivide(dT).minus(new
         * Matrix(dX.getRowDimension(),dY.getColumnDimension(),c)); Matrix dYsdT
         * = dY.arrayRightDivide(dT);
         */

        //ajoue d'un zero en debut et fin
        Matrix dXsdT = new Matrix(dX.getRowDimension() + 2, dX.getColumnDimension(), 0);
        dXsdT.setMatrix(Functions.createIndex(1, n - 1), Functions.trivialIndex(),
                dX.arrayRightDivide(dT).minus(new Matrix(dX.getRowDimension(), dY.getColumnDimension(), cx)));
        Matrix dYsdT = new Matrix(dY.getRowDimension() + 2, dY.getColumnDimension(), 0);
        dYsdT.setMatrix(Functions.createIndex(1, n - 1), Functions.trivialIndex(),
                dY.arrayRightDivide(dT).minus(new Matrix(dX.getRowDimension(), dY.getColumnDimension(), cy)));


        //new T
        Matrix aT = T.getMatrix(Functions.createIndex(0, n - 2), Functions.trivialIndex()).plus(T.getMatrix(Functions.createIndex(1, n - 1), Functions.trivialIndex()));
        // old :Matrix nT = aT.times(0.5);
        Matrix nT = new Matrix(n + 1, 1);
        nT.set(0, 0, trace.T[0]);
        nT.set(n, 0, trace.T[n - 1]);
        nT.setMatrix(Functions.createIndex(1, n - 1), Functions.trivialIndex(), aT.times(0.5));
        this.nT = nT.getColumnPackedCopy();

        //System.out.println("nt : " + Functions.affiche(nT.getColumnPackedCopy()));
        //System.out.println("dYsdT : " + Functions.affiche(dYsdT.getColumnPackedCopy()));

        //affichage
        //plot.plot(dYsdT.getColumnPackedCopy(), nT.getColumnPackedCopy());

        this.dYsdT = dYsdT.getColumnPackedCopy();
        this.dXsdT = dXsdT.getColumnPackedCopy();

        //points where dY/dT crosses zero
        this.fdYsdT = Signal.filtfilt6(this.dYsdT);
        this.cry = Crossing.crossing(this.fdYsdT, this.nT);
        //filtering pour enlever les zeros trop proches
        //cry.filter();

        /*
         * plot.hold_on(); plot.plot(cry.s0, cry.t0, PlotType.XYPLOTS,
         * Color.green);/*
         */


        int nb_arcsy = cry.t0.length - 1;
        double[] t0sy = cry.t0;
        //System.out.println("ind : " + Functions.affiche(cry.ind));
        //System.out.println("t0sy : " + Functions.affiche(t0sy));
        //System.out.println("s0 : " + Functions.affiche(cry.s0));


        //pour y
        Matrix wy = new Matrix(t0sy.length - 1, 1, Math.PI).arrayRightDivide(new Matrix(Functions.diff(t0sy), t0sy.length - 1));
        //System.out.println("wy : " + Functions.affiche(wy.getColumnPackedCopy()));

        Matrix phiy = wy.uminus().arrayTimes(new Matrix(t0sy, t0sy.length).getMatrix(Functions.createIndex(0, nb_arcsy - 1), Functions.trivialIndex()));

        double[] b = new double[nb_arcsy];


        for (int i = 0; i < nb_arcsy; i++) {
            double[] part = dYsdT.getMatrix(Functions.createIndex(cry.ind[i], cry.ind[i + 1]),
                    Functions.trivialIndex()).getColumnPackedCopy();
            double[] abspart = Functions.abs(part);
            int mid = Functions.index_max(abspart);
            double val = StdStats.mean(abspart);
            double sval = StdStats.stddev(abspart);

            b[i] = Math.signum(part[mid]) * (val + sval) / this.magic;

            //correcting signs
            double sig = Math.signum(b[i] * Math.sin(wy.getColumnPackedCopy()[i] * (t0sy[i] + 20) + phiy.getColumnPackedCopy()[i]));
            //int lind = (cry.ind[i] + cry.ind[i + 1]) / 2;
            double sig2 = Math.signum(StdStats.mean(part));
            if (sig == -sig2) {
                b[i] = -b[i];
            }
        }



        //points where dX/dT crosses zero
        this.fdXsdT = Signal.filtfilt6(this.dXsdT);
        this.crx = Crossing.crossing(this.fdXsdT, this.nT);

        //on vire les zero trop proches
        //crx.filter();

        //affichage
        /*
         * plot.plot(dXsdT.getColumnPackedCopy(),
         * nT.getColumnPackedCopy(),PlotType.XYLINE,Color.red); plot.hold_on();
         * plot.plot(crx.s0, crx.t0, PlotType.XYPLOTS, Color.green);/*
         */

        //System.out.println("nT : " + Functions.affiche(nT.getColumnPackedCopy()));
        //System.out.println("dXsdT : " + Functions.affiche(dXsdT.getColumnPackedCopy()));


        int nb_arcsx = crx.t0.length - 1;
        double[] t0sx = crx.t0;
        //System.out.println("t0sx : " + Functions.affiche(t0sx));

        //et maintenant pour x
        Matrix wx = new Matrix(t0sx.length - 1, 1, Math.PI).arrayRightDivide(new Matrix(Functions.diff(t0sx), t0sx.length - 1));
        Matrix phix = wx.uminus().arrayTimes(new Matrix(t0sx, t0sx.length).getMatrix(Functions.createIndex(0, nb_arcsx - 1), Functions.trivialIndex()));

        double[] a = new double[nb_arcsx];


        for (int i = 0; i < nb_arcsx; i++) {
            double[] part = dXsdT.getMatrix(Functions.createIndex(crx.ind[i], crx.ind[i + 1]),
                    Functions.trivialIndex()).getColumnPackedCopy();
            double[] abspart = Functions.abs(part);
            int mid = Functions.index_max(abspart);
            double val = StdStats.mean(abspart);
            double sval = StdStats.stddev(abspart);
            a[i] = Math.signum(part[mid]) * (val + sval) / this.magic;

            //correcting signs
            double sig = Math.signum(a[i] * Math.sin(wx.getColumnPackedCopy()[i] * (t0sx[i] + 20) + phix.getColumnPackedCopy()[i]));
            //int lind = (crx.ind[i] + crx.ind[i + 1]) / 2;
            double sig2 = Math.signum(StdStats.mean(part));
            if (sig == -sig2) {
                a[i] = -a[i];
            }
        }






        //on pack le résultat
        STrace st = new STrace();
        st.a = a;
        st.b = b;
        st.phix = phix.getColumnPackedCopy();
        st.phiy = phiy.getColumnPackedCopy();
        st.tx = new Matrix(t0sx, t0sx.length).getMatrix(Functions.createIndex(0, nb_arcsx - 1), Functions.trivialIndex()).getColumnPackedCopy();
        st.ty = new Matrix(t0sy, t0sy.length).getMatrix(Functions.createIndex(0, nb_arcsy - 1), Functions.trivialIndex()).getColumnPackedCopy();
        st.wx = wx.getColumnPackedCopy();
        st.wy = wy.getColumnPackedCopy();
        st.c = c;

        ///on effectue quelques corerecitons

        for (int i = 0; i < st.a.length; i++) {
            if (st.a[i] < 0) {
                st.phix[i] = st.phix[i] - Math.PI;
                st.a[i] = -st.a[i];
            }

        }

        for (int i = 0; i < st.b.length; i++) {
            if (st.b[i] < 0) {
                st.phiy[i] = st.phiy[i] - Math.PI;
                st.b[i] = -st.b[i];
            }

        }

        for (int i = 0; i < st.a.length; i++) {
            st.phix[i] = st.phix[i] % (2 * Math.PI);
        }
        for (int i = 0; i < st.b.length; i++) {
            st.phiy[i] = st.phiy[i] % (2 * Math.PI);
        }

        //on reconstrui les signaux et les affichent
        sampling = Functions.createSample(trace.T[0], this.sampling_step, trace.T[trace.T.length - 1]);

        //voyons
        sampling = trace.T;

        this.sampledx = new double[sampling.length];
        this.sampledy = new double[sampling.length];
        for (int i = 0; i < sampling.length; i++) {
            int ix = Functions.getLesser(st.tx, sampling[i]);
            sampledx[i] = st.a[ix] * Math.sin(st.wx[ix] * sampling[i] + st.phix[ix]);

            int iy = Functions.getLesser(st.ty, sampling[i]);
            sampledy[i] = st.b[iy] * Math.sin(st.wy[iy] * sampling[i] + st.phiy[iy]);

        }


        //affichage
       /*
         * plot.figure(0); plot.hold_on(); plot.plot(sampledy, sample,
         * PlotType.XYLINE, Color.CYAN);
         *
         * plot.figure(1); plot.hold_on(); plot.plot(sampledx, sample,
         * PlotType.XYLINE, Color.magenta);/*
         */

        //on doit enlever le premier élément avec des infinity (why ... TODO) -- bug corrigé
        /*
         * st.a = Functions.removeFirst(st.a); st.b =
         * Functions.removeFirst(st.b); st.tx = Functions.removeFirst(st.tx);
         * st.ty = Functions.removeFirst(st.ty); st.wx =
         * Functions.removeFirst(st.wx); st.wy = Functions.removeFirst(st.wy);
         * st.phix = Functions.removeFirst(st.phix); st.phiy =
         * Functions.removeFirst(st.phiy);
         */


        //les z
        int[] indices = Functions.borders(trace.Z);
        st.tz = new Matrix(trace.T, trace.T.length).getMatrix(indices, Functions.trivialIndex()).getColumnPackedCopy();
        st.down = new Matrix(trace.Z, trace.Z.length).getMatrix(indices, Functions.trivialIndex()).getColumnPackedCopy();

        //st.affiche();


        //ici les analyses grossière de aprametres
        /*
         * double[] phi_rel = Functions.substract(st.phiy, st.phix);
         * //System.out.println("Phase relatives :
         * "+Functions.affiche(phi_rel)); double[] repphirelx = new
         * double[phi_rel.length]; double[] repphirely = new
         * double[phi_rel.length];
         *
         * for(int i=0;i<phi_rel.length;i++){ repphirelx[i] =
         * 30*Math.cos(phi_rel[i]); repphirely[i] = 30*Math.sin(phi_rel[i]); }
         *
         * plot.figure(); plot.plot(repphirely, repphirely, PlotType.XYPLOTS,
         * Color.red);
         */

        st.startx = trace.X[0];
        st.starty = trace.Y[0];
        st.tfinal = trace.T[trace.T.length - 1];

        double tfin = st.tfinal;
        double oldx = tempX[0];
        double oldy = tempY[0];

        samplex = new double[trace.T.length];
        sampley = new double[trace.T.length];
        samplex[0] = tempX[0];
        sampley[0] = tempY[0];

        double[] tt = trace.T;





        for (int i = 1; i < tt.length; i++) {
            double t = tt[i];
            int ix = Functions.getLesser(st.tx, t);
            //System.out.println(t+ " " +Functions.affiche(rouge.tx));
            int iy = Functions.getLesser(st.ty, t);
            //System.out.println(ix +" "+iy);
            int iz = Functions.getLesser(st.tz, t);
            double down = st.down[iz];
            // double down = 1;
            double nt;

            samplex[i] = oldx + (st.a[ix] * Math.sin(st.wx[ix] * t + st.phix[ix]) + cx) * (t - tt[i - 1]);
            sampley[i] = oldy + (st.b[iy] * Math.sin(st.wy[iy] * t + st.phiy[iy]) + cy) * (t - tt[i - 1]);

            oldx = samplex[i];
            oldy = sampley[i];
        }



        double[] tempsX = Functions.rotateX(-angle_axes, samplex, sampley);
        double[] tempsY = Functions.rotateY(-angle_axes, samplex, sampley);
        samplex = tempsX;
        sampley = tempsY;


        this.Ox_x = new double[crx.t0.length];
        this.Ox_y = new double[crx.t0.length];
        for (int i = 0; i < crx.t0.length; i++) {
            int ix = Functions.getLesser(tt, crx.t0[i]);
            this.Ox_x[i] = samplex[ix];
            this.Ox_y[i] = sampley[ix];
        }

        this.Oy_x = new double[cry.t0.length];
        this.Oy_y = new double[cry.t0.length];
        for (int i = 0; i < cry.t0.length; i++) {
            int iy = Functions.getLesser(tt, cry.t0[i]);
            this.Oy_x[i] = samplex[iy];
            this.Oy_y[i] = sampley[iy];
        }

        this.strace = st;

        tap.plot();
        
        return st;

    }

    public double[] getOx_x() {
        return Ox_x;
    }

    public double[] getOx_y() {
        return Ox_y;
    }

    public double[] getOy_x() {
        return Oy_x;
    }

    public double[] getOy_y() {
        return Oy_y;
    }

    public double[] getSamplex() {
        return samplex;
    }

    public double[] getSampley() {
        return sampley;
    }

    public CrossingReturn getCrx() {
        return crx;
    }

    public CrossingReturn getCry() {
        return cry;
    }

    public double[] getdXsdT() {
        return dXsdT;
    }

    public double[] getdYsdT() {
        return dYsdT;
    }

    public double[] getnT() {
        return nT;
    }

    public double[] getSampledx() {
        return sampledx;
    }

    public double[] getSampledy() {
        return sampledy;
    }

    public double[] getSampling() {
        return sampling;
    }

    public STrace getStrace() {
        return strace;
    }

    public Trace getTrace() {
        // System.out.println("ta get " +Arrays.toString(trace.Y));
        return trace;
    }

    public double getDegAngle() {
        return this.angle_axes * 180.0 / Math.PI;
    }

    public double getAngle() {
        return this.angle_axes;
    }

    public Trace getTraceFromSTrace() {
        Trace toto = new Trace();
        toto.T = sampling;
        toto.X = samplex;
        toto.Y = sampley;
        return toto;
    }

    @Override
    public String toString() {
        String fin = "C not null";
        if (c_zero) {
            fin = "C null";
        }
        return "TraceAnalyzer, Axes Angle = " + this.angle_axes + ", " + fin;
    }

    public boolean isCZero() {
        return c_zero;
    }

    public static TraceAnalyzer createTraceAnalyzer(int type, Trace t, double arg1, boolean arg2) {
        TraceAnalyzer res;
        switch (type) {
            case TraceAnalyzer.MCJ_TYPE:
                res = new TraceAnalyzerMCJ(t, arg1, arg2);
                break;
            case TraceAnalyzer.TTL_TYPE:
                res = new TTLAnalyzer(t);
                break;
            default:
                res = new TraceAnalyzer(t, arg1, arg2);
                break;

        }

        return res;
    }

    public int getType() {
        return TraceAnalyzer.DEFAULT_TYPE;
    }
}
