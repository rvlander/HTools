/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.hollermap.traces;

import htools.hollermap.mat2java.Functions;
import htools.core.traces.Trace;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author rvlander
 */
public class STrace implements Serializable {
    
    static final long serialVersionUID = 1515L;

    public double c;
    //d'où on part
    public double startx;
    public double starty;
    public double tfinal;
    // enx tout est censé avoir la même longueur
    public double[] tx;
    public double[] wx;
    public double[] phix;
    public double[] a;
    // eny tout est censé avoir la même longueur
    public double[] ty;
    public double[] wy;
    public double[] phiy;
    public double[] b;
    //tz
    public double[] tz;
    public double[] down;
    public Trace tr = null;
    public Vector<ZeroPoint> vzp;

    public void affiche() {
        System.out.println("c : " + this.c);

        System.out.println("tfinal : " + tfinal);

        System.out.println("startx : " + startx + ", starty : " + starty);

        System.out.println("Pour les x : ");
        System.out.println("tx (" + tx.length + "): " + Functions.affiche(tx));
        System.out.println("a (" + a.length + "): " + Functions.affiche(a));
        System.out.println("wx (" + wx.length + "): " + Functions.affiche(wx));
        System.out.println("phix (" + phix.length + "): " + Functions.affiche(phix));

        System.out.println("\nPour les y : ");
        System.out.println("ty (" + ty.length + "): " + Functions.affiche(ty));
        System.out.println("b (" + b.length + "): " + Functions.affiche(b));
        System.out.println("wy (" + wy.length + "): " + Functions.affiche(wy));
        System.out.println("phiy (" + phiy.length + "): " + Functions.affiche(phiy));

        System.out.println("\nPour les z : ");
        System.out.println("tz (" + tz.length + "): " + Functions.affiche(tz));
        System.out.println("down (" + down.length + "): " + Functions.affiche(down));


    }

    public String getCvsHeaders() {
        String newline = System.getProperty("line.separator");
        return "t,wx,phix,a,wy,phiy,b" + newline;
    }

    public String getCvsData() {

        String newline = System.getProperty("line.separator");

        String[] torender = new String[this.phix.length + this.phiy.length];
        double[] times = new double[this.phix.length + this.phiy.length];

        String res = "";

        res += this.startx + "," + this.starty + "," + this.tfinal + newline;

        int l = 0;
        for (int i = 0; i < this.phiy.length; i++) {
            int j = Functions.getLesser(this.tx, this.ty[i]);
            //System.out.println(j);
            torender[l] = ty[i] + "," + wx[j] + "," + phix[j] + "," + a[j] + ","
                    + wy[i] + "," + phiy[i] + "," + b[i] + newline;
            times[l] = ty[i];
            l++;
        }
        for (int j = 0; j < this.phix.length; j++) {
            int i = Functions.getLesser(this.ty, this.tx[j]);
            torender[l] = tx[j] + "," + wx[j] + "," + phix[j] + "," + a[j] + ","
                    + wy[i] + "," + phiy[i] + "," + b[i] + newline;
            times[l] = tx[j];
            l++;

        }

        int[] inds = Functions.index_sort(times);

        for (int i = 0; i < inds.length; i++) {
            res += torender[inds[i]];
        }


        return res;

    }

    public Trace getTrace() {
        if (this.tr == null) {
            tr = this.calculTrace();
        }
        return tr;
    }

    public Vector<ZeroPoint> getZeroPoints() {
        if (this.vzp == null) {
            vzp = this.calculZeroPoints();
        }
        return vzp;
    }

    public Trace calculTrace() {
        Vector<Double> vd = new Vector<Double>();
        Vector<Double> X = new Vector<Double>();
        Vector<Double> Y = new Vector<Double>();
        Vector<Double> Z = new Vector<Double>();

        double tfin = this.tfinal;
        double oldx = this.startx;
        double oldy = this.starty;

        vd.add(0.0);
        X.add(startx);
        Y.add(starty);
        Z.add(0.0);

        int ix = -1;
        int iy = -1;

        for (double t = 0; t <= tfin + 20; t += 20) {
            int nix = Functions.getLesser(this.tx, t);
            //System.out.println(t+ " " +Functions.affiche(rouge.tx));
            int niy = Functions.getLesser(this.ty, t);
            //System.out.println(ix +" "+iy);
            int iz = Functions.getLesser(this.tz, t);
            double down = this.down[iz];
            // double down = 1;
            double x = oldx + (this.a[nix] * Math.sin(this.wx[nix] * t + this.phix[nix]) + this.c) * 20;
            double y = oldy + (this.b[niy] * Math.sin(this.wy[niy] * t + this.phiy[niy])) * 20;

            vd.add(t);
            X.add(x);
            Y.add(y);
            Z.add(0.0);

            if (t >= tfin) {
                x = oldx;
                y = oldy;
            }


            oldx = x;
            oldy = y;


        }
        Trace tr = new Trace();
        tr.T = STrace.DoubleVector2DoubleArray(vd);
        tr.X = STrace.DoubleVector2DoubleArray(X);
        tr.Y = STrace.DoubleVector2DoubleArray(Y);
        tr.Z = STrace.DoubleVector2DoubleArray(Z);

        this.tr = tr;
        return tr;
    }

    public static double[] DoubleVector2DoubleArray(Vector<Double> v) {
        double[] r = new double[v.size()];
        int i = 0;
        for (Double d : v) {
            r[i] = d;
            i++;
        }
        return r;
    }

    public static double[] removeAt(double[] tab, int i) {
        double[] res = new double[tab.length - 1];
        boolean in = false;
        for (int j = 0; j < tab.length; j++) {
            if (j == i) {
                in = true;
            } else if (!in) {
                res[j] = tab[j];
            } else {
                res[j - 1] = tab[j];
            }
        }
        return res;
    }

    private Vector<ZeroPoint> calculZeroPoints() {
        Vector<ZeroPoint> zp = new Vector<ZeroPoint>();
        for (int i = 0; i < tx.length; i++) {
            ZeroPoint z = new ZeroPoint();
            double t = tx[i];
            z.x = this.getTrace().getX(t);
            z.y = this.getTrace().getY(t);
            z.i = i;
            z.dim = ZeroPoint.X;
            zp.add(z);

        }
        for (int i = 0; i < ty.length; i++) {
            ZeroPoint z = new ZeroPoint();
            double t = ty[i];
            z.x = this.getTrace().getX(t);
            z.y = this.getTrace().getY(t);
            z.i = i;
            z.dim = ZeroPoint.Y;
            zp.add(z);

        }
        this.vzp = zp;
        return zp;

    }
    
    public Trace calculXZeroPoints(double angl) {
        Trace res = new Trace();
        res.X = new double[tx.length];
        res.Y = new double[tx.length];
        for (int i = 0; i < tx.length; i++) {
            double t = tx[i];
            res.X[i] = this.getTrace().getX(t);
            res.Y[i] = this.getTrace().getY(t);
        }
        double[] tempsX = Functions.rotateX(-angl, res.X, res.Y);
        double[] tempsY = Functions.rotateY(-angl, res.X, res.Y);
        res.X = tempsX;
        res.Y = tempsY;
        return res;
    }
    public Trace calculYZeroPoints(double angl) {
        Trace res = new Trace();
        res.X = new double[ty.length];
        res.Y = new double[ty.length];
        for (int i = 0; i < ty.length; i++) {
            ZeroPoint z = new ZeroPoint();
           double t = ty[i];
            res.X[i] = this.getTrace().getX(t);
            res.Y[i] = this.getTrace().getY(t);

        }
        double[] tempsX = Functions.rotateX(-angl, res.X, res.Y);
        double[] tempsY = Functions.rotateY(-angl, res.X, res.Y);
        res.X = tempsX;
        res.Y = tempsY;
        return res;
    }

    public void remove(ZeroPoint selected) {
        tr = null;
        vzp = null;
        int i = selected.i;
        switch (selected.dim) {
            case ZeroPoint.X:
                tx = STrace.removeAt(tx, i);
                a = STrace.removeAt(a, i);
                wx = STrace.removeAt(wx, i);
                phix = STrace.removeAt(phix, i);
                break;
            case ZeroPoint.Y:
                ty = STrace.removeAt(ty, i);
                b = STrace.removeAt(b, i);
                wy = STrace.removeAt(wy, i);
                phiy = STrace.removeAt(phiy, i);
                break;
        }
    }
    public void reCalc(){
        tr = null;
        vzp = null;
    }
    
    public void export(PrintWriter pw){
       //pw.println(c+","+startx+","+starty+","+tfinal);
        /*pw.println(tx.length);
        for(int i=0;i<tx.length;i++)
            pw.println(tx[i]+","+a[i]+","+wx[i]+","+phix[i]);
        pw.println(ty.length);
        for(int i=0;i<ty.length;i++)
            pw.println(ty[i]+","+b[i]+","+wy[i]+","+phiy[i]);
        */

        pw.print("schemax = [");
        for(int i=0;i<a.length;i++)
            pw.print("("+tx[i]+","+a[i]+","+wx[i]+","+phix[i]+"),");
        pw.print("]\nschemay = [");
        for(int i=0;i<b.length;i++)
            pw.print("("+ty[i]+","+b[i]+","+wy[i]+","+phiy[i]+"),");
        pw.print("]\n");
        pw.println("c="+c);
        pw.println("tfin="+tfinal);
    }
}
