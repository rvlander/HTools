/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.hollermap.manager;

import htools.hollermap.algorithms.TraceAnalyzer;
import htools.hollermap.algorithms.TraceAnalyzerMCJ;
import htools.core.traces.Trace;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author rvlander
 */
public class TraceStudy implements Serializable {
    static final long serialVersionUID = 1515L;

    private int ID;
    private static int nbrStudy;
    private Trace trace;
    
    private String name;

    public Trace getTrace() {
        return trace;
    }
    private Vector<TraceAnalyzer> vta;
    private int selectedTraceAnalyzer;

    public TraceStudy(Trace t) {
        nbrStudy++;
        this.ID = nbrStudy;
        this.trace = t;
        vta = new Vector<TraceAnalyzer>();
        //vta.add(new TraceAnalyzer(t, 0, false));
        //vta.add(new TraceAnalyzerMCJ(t));
        //vta.lastElement().computeAll();
    }
    
    public TraceStudy(Trace t, String name, Vector<TraceAnalyzer> vv){
        this(t);
        this.name = name;
        for(TraceAnalyzer ta : vv){
            //System.out.println(t);
            vta.add(TraceAnalyzer.createTraceAnalyzer(ta.getType(),t,ta.getAngle(),ta.isCZero()));
        }
        for(TraceAnalyzer ta : vta){
            ta.computeAll();
        }
        
        
    }

    public void addTraceAnalyzer(TraceAnalyzer ta) {
        vta.add(ta);
        this.selectedTraceAnalyzer = vta.size() - 1;
    }

    public void paintSTrace(Graphics2D g, double[] x, double[] y, double[] t, long time, int height) {
        int n = t.length;

        double tfin = t[n - 1];

        tfin = Math.min(tfin, time);
        for (int i = 1; i < n && t[i] <= tfin; i++) {
            double down = this.trace.Z[i];
            if (down == 1) {
                g.setColor(Color.red);
            } else {
                g.setColor(Color.lightGray);
            }
            g.drawLine((int) x[i - 1],height-(int) y[i - 1], (int) x[i], height-(int) y[i]);


        }
    }

    void paint(Graphics2D g, boolean traceBleu, boolean traceRouge,long time,int height) {
        TraceAnalyzer ta = this.vta.get(this.selectedTraceAnalyzer);

        if (traceBleu) {
            trace.paint(g, time, height);
        }
        if (traceRouge && ta.getType()!=TraceAnalyzer.TTL_TYPE) {
            paintSTrace(g, ta.getSamplex(), ta.getSampley(), ta.getSampling(), time,height);
        }
    }

    public int getSelectedTraceAnalyzer() {
        return selectedTraceAnalyzer;
    }

    public TraceAnalyzer getTraceAnalyzer(int i) {
        return this.vta.get(i);
    }

    public Vector<TraceAnalyzer> getVTA() {
        return vta;
    }

    public void setSelectedTraceAnalyzer(int selectedTraceAnalyzer) {
        this.selectedTraceAnalyzer = selectedTraceAnalyzer;
    }

    @Override
    public String toString() {
        String res;
        if(name != null){
            res =name+ " ";
        }else{
            res = "Study ";
        } 
        return res + this.ID;
    }
}
