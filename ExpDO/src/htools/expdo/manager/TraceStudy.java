/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.expdo.manager;


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

    private int selectedTraceAnalyzer;
    private boolean play = false;
    private long know = 0;

    public TraceStudy(Trace t) {
        nbrStudy++;
        this.ID = nbrStudy;
        this.trace = t;

    }
    
    public TraceStudy(Trace t, String name){
        this(t);
        this.name = name;

        
        
    }

   

    public void paintTrace(Graphics2D g, Trace tra) {
        g.setColor(Color.blue);
        for (int i = 1; i < tra.T.length; i++) {
            if (!this.play || tra.T[i] < this.know) {
                if (tra.Z[i] == 1) {
                    g.setColor(Color.blue);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.drawLine((int) tra.X[i - 1], 800-(int) tra.Y[i - 1], (int) tra.X[i], 800-(int) tra.Y[i]);
            } else {
                break;
            }
        }
    }

    

    void paint(Graphics2D g, boolean traceBleu, boolean traceRouge) {
    

        if (traceBleu) {
            paintTrace(g, trace);
        }

    }

    void setPlay(boolean p) {
        play = p;
    }

    void setKnow(long know) {
        this.know = know;
    }

    public int getSelectedTraceAnalyzer() {
        return selectedTraceAnalyzer;
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
