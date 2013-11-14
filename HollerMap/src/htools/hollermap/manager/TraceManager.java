/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.hollermap.manager;

import htools.hollermap.algorithms.TTLAnalyzer;
import htools.hollermap.algorithms.TraceAnalyzer;
import htools.hollermap.algorithms.TraceAnalyzerMCJ;
import htools.core.traces.Trace;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class TraceManager implements Serializable {

    static final long serialVersionUID = 1515L;
    private Vector<TraceStudy> vts = new Vector<TraceStudy>();
    private int selectedStudy;
    transient private Vector<TraceManagerListener> vtml = new Vector<TraceManagerListener>();
    private String base_study_name = "Study";
    private int selectedDefaultAnalyzer;
    private Vector<TraceAnalyzer> defaultAnalysers;

    public Vector<TraceStudy> getVTS() {
        return vts;
    }

    public TraceManager() {
        vts = new Vector<TraceStudy>();
        this.initializedDefaultAnalyzers();
    }
    
    private void initializedDefaultAnalyzers(){
        this.defaultAnalysers = new Vector<TraceAnalyzer>();
        //this.defaultAnalysers.add(new TTLAnalyzer(null));
        //this.defaultAnalysers.add(new TraceAnalyzer(null, 0, false));
        this.defaultAnalysers.add(new TraceAnalyzerMCJ(null, 0, false));
        
    }

    public void addTrace(Trace t) {
        if (this.defaultAnalysers == null) {
            this.initializedDefaultAnalyzers();
        }
        vts.add(new TraceStudy(t, this.base_study_name, this.defaultAnalysers));
        this.selectedStudy = vts.size() - 1;
        for (TraceManagerListener tlm : vtml) {
            tlm.addedTraceStudy();
        }
    }

    public void removeTrace() {
        vts.remove(selectedStudy);
        this.selectedStudy--;
        if (this.selectedStudy < 0) {
            this.selectedStudy = 0;
        }
        for (TraceManagerListener tlm : vtml) {
            tlm.removedTraceStudy();
        }
    }

    public void setStudyBaseName(String name) {
        this.base_study_name = name;
    }

    public String getStudyBaseName() {
        return this.base_study_name;
    }

    public int getSelectedStudy() {
        return selectedStudy;
    }

    public void addListener(TraceManagerListener tlm) {
        vtml.add(tlm);
    }

    public void paint(Graphics2D g, boolean traceBleu, boolean traceRouge, int height) {
        if (!vts.isEmpty()) {
            vts.get(selectedStudy).paint(g, traceBleu, traceRouge,height);
        }
    }

    public void setSelectedStudy(int selectedStudy) {
        this.selectedStudy = selectedStudy;
    }

    public void setPlay(boolean p) {
        if (!vts.isEmpty()) {
            this.vts.get(selectedStudy).setPlay(p);
        }
    }

    public void setKnow(long know) {
        if (!vts.isEmpty()) {
            this.vts.get(selectedStudy).setKnow(know);
        }
    }

    void addTraceAnalyzer(int type,double varang, boolean cz) {
        TraceStudy ts = this.vts.get(selectedStudy);
        TraceAnalyzer ta = TraceAnalyzer.createTraceAnalyzer(type,ts.getTrace(), varang, cz);
        ta.computeAll();
        ts.addTraceAnalyzer(ta);

        for (TraceManagerListener tlm : vtml) {
            tlm.addedTraceAnalyzer();
        }
    }

    void removeTraceAnalyzer() {
        TraceStudy ts = this.vts.get(selectedStudy);
        ts.getVTA().remove(ts.getSelectedTraceAnalyzer());
        ts.setSelectedTraceAnalyzer(ts.getSelectedTraceAnalyzer() - 1);
        if (ts.getSelectedTraceAnalyzer() < 0) {
            ts.setSelectedTraceAnalyzer(0);
        }

        for (TraceManagerListener tlm : vtml) {
            tlm.removedTraceAnalyzer();
        }
    }

    public void loadListener(TraceManagerListener tlm) {
        this.vtml = new Vector<TraceManagerListener>();
        this.addListener(tlm);
    }

    public static void exportTrace(String file, Trace t) {
        try {
            PrintWriter out = new PrintWriter(new File(file));
            out.println("#");
            t.export(out);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TraceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exportSelectedTrace(String file) {
        Trace t = this.vts.get(selectedStudy).getTrace();
        exportTrace(file, t);
    }

    public void exportAllTrace(String file) {
        int i = 1;
        for (TraceStudy ts : this.vts) {
            Trace t = ts.getTrace();
            exportTrace(file + " " + i, t);
            i++;
        }
    }

    public void exportTraceFromSTrace(String file, TraceAnalyzer ta) {

        try {
            PrintWriter out = new PrintWriter(new File(file));
            out.println("#");
            ta.getTraceFromSTrace().export(out);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TraceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exportSelectedTraceFromSTrace(String file) {
        TraceStudy ts = this.vts.get(selectedStudy);
        Trace t = ts.getTrace();
        int tai = ts.getSelectedTraceAnalyzer();
        TraceAnalyzer ta = ts.getTraceAnalyzer(tai);
        exportTraceFromSTrace(file, ta);
    }

    public void exportAllTraceFromSTrace(String file) {
        int i = 1;
        for (TraceStudy ts : this.vts) {
            int j = 1;
            for (TraceAnalyzer ta : ts.getVTA()) {
                exportTraceFromSTrace(file + "_" + i + "_" + j, ta);
                j++;
            }
            i++;
        }

    }

    public void exportSTrace(String file, TraceAnalyzer ta) {
        try {
            PrintWriter out = new PrintWriter(new File(file));
            out.println("#");
            ta.getStrace().export(out);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TraceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exportSelectedSTrace(String file) {
        TraceStudy ts = this.vts.get(selectedStudy);
        Trace t = ts.getTrace();
        int tai = ts.getSelectedTraceAnalyzer();
        TraceAnalyzer ta = ts.getTraceAnalyzer(tai);
        exportSTrace(file, ta);
    }

    public void exportAllSTrace(String file) {
        int i = 1;
        for (TraceStudy ts : this.vts) {
            int j = 1;
            for (TraceAnalyzer ta : ts.getVTA()) {
                exportSTrace(file + "_" + i + "_" + j, ta);
                j++;
            }
            i++;
        }

    }

    public Vector<TraceAnalyzer> getDefaultAnalysers() {
        return defaultAnalysers;
    }

    public int getSelectedDefaultAnalyzer() {
        return selectedDefaultAnalyzer;
    }

    public void setSelectedDefaultAnalyzer(int selectedDefaultAnalyzer) {
        this.selectedDefaultAnalyzer = selectedDefaultAnalyzer;
    }

    public void addDefaultAnalyzer(int type,double ang, boolean czero) {
        this.defaultAnalysers.add(TraceAnalyzer.createTraceAnalyzer(type,null, ang, czero));
        for (TraceManagerListener tlm : vtml) {
            tlm.addedDefaultAnalyzer();
        }
    }

    public void removeSelectedDefaultAnalyzer() {
        this.defaultAnalysers.remove(this.selectedDefaultAnalyzer);
        for (TraceManagerListener tlm : vtml) {
            tlm.addedDefaultAnalyzer();
        }
    }
}
