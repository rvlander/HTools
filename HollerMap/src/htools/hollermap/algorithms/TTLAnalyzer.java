/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.hollermap.algorithms;

import htools.hollermap.MCJImports.AlgoMethods;
import htools.hollermap.MCJImports.Wdouble;
import htools.hollermap.output.TTLPlotter;
import htools.hollermap.traces.STrace;
import htools.core.traces.Trace;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mc.mcjavacore.MCJMatrixDimensionsException;

/**
 *
 * @author rvlander
 */
public class TTLAnalyzer extends TraceAnalyzer {

    double[] logcurve;
    double[] logv;
    double[] t;
    double[] y;
    double a;
    double b;
    
    
    

    public TTLAnalyzer(Trace ta) {
        super(ta, 0.0, false);
        trace =ta;
       // System.out.println(trace);
        tap = new TTLPlotter(this);
       // this.computeAll();
        
    }
    

    @Override
    public STrace computeAll() {
        try {
            Wdouble logcurve = new Wdouble();
            Wdouble logv = new Wdouble();
            Wdouble t = new Wdouble();
            Wdouble y = new Wdouble();
            Wdouble a = new Wdouble();
            Wdouble b = new Wdouble();
            //System.out.println("Compute All : " +this.trace.T);//+" "+ this.trace.X+" "+ this.trace.Y);
            AlgoMethods.twothird(this.trace.T, this.trace.X, this.trace.Y, logcurve, logv,t,y,a,b);
            
            this.logcurve = logcurve.v;
            this.logv = logv.v;
            this.t= t.v;
            this.y= y.v;
            this.a= a.v[0];
            this.b= b.v[0];
            
            tap.plot();
        } catch (MCJMatrixDimensionsException ex) {
            Logger.getLogger(TTLAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TTLAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(TTLAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getType() {
        return TraceAnalyzer.TTL_TYPE;
    }

    public double[] getLogcurve() {
        return logcurve;
    }

    public double[] getLogv() {
        return logv;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double[] getT() {
        return t;
    }

    public double[] getY() {
        return y;
    }
    
    @Override
    public String toString(){
        return "TwoThirdLaw analysis";
    }
    
}
