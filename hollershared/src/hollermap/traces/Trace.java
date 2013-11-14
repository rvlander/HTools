/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hollermap.traces;


import hollermap.mat2java.Functions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class Trace implements Serializable {

    static final long serialVersionUID = 1515L;
    public double[] T;
    public double[] X;
    public double[] Y;
    public double[] Z;

    public double getX(double t) {
        int i = Functions.getLesser(T, t);
        return X[i];
    }

    public double getY(double t) {
        int i = Functions.getLesser(T, t);
        return Y[i];
    }

    public void export(PrintWriter pw) {
        for (int i = 0; i < X.length; i++) {
            double del = T[i];// - trace.T[0]; // System.out.println(del);
            if (Z != null) {
                pw.println(del + "," + X[i] + "," + Y[i] + "," + Z[i]);
            } else {
                pw.println(del + "," + X[i] + "," + Y[i] + "," + 1.0);
            }
        }

    }

    public static double[] doubleArrayFromDoubleVector(Vector<Double> vd) {
        int n = vd.size();
        double[] res = new double[n];
        for (int i = 0; i < n; i++) {
            res[i] = vd.get(i);
        }
        return res;

    }

    public static Trace read(BufferedReader pr) throws IOException {

        
      //  System.out.println("ParceBegin !" );
        String line = " ";
        Vector<Double> t = new Vector<Double>();
        Vector<Double> x = new Vector<Double>();
        Vector<Double> y = new Vector<Double>();
        Vector<Double> z = new Vector<Double>();
        line = pr.readLine();
           line = pr.readLine();
        while (line != null && !line.contains("#")) {
            //  System.out.println(line);
            String[] params = line.split(",");
            t.add(Double.parseDouble(params[0]));
            x.add(Double.parseDouble(params[1]));
            y.add(Double.parseDouble(params[2]));
            z.add(0.0);
             //System.out.println(params[0] +" "+params[2] );
            line = pr.readLine();
        }

        Trace tr = new Trace();

        tr.T = Trace.doubleArrayFromDoubleVector(t);
        tr.X = Trace.doubleArrayFromDoubleVector(x);
        tr.Y = Trace.doubleArrayFromDoubleVector(y);
        tr.Z = Trace.doubleArrayFromDoubleVector(z);


        return tr;

    }
}
