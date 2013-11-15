/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.expdo.manager;

import htools.expdo.experience.Manip;
import htools.expdo.experience.Experience;
import htools.core.input.InteractivePanel;
import htools.core.input.InteractivePanelListener;
import htools.core.input.Options;
import htools.core.traces.Trace;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author rvlander
 */
public class TraceManager implements InteractivePanelListener,Serializable {

    static final long serialVersionUID = 1515L;
    private DefaultMutableTreeNode tmanip;
    private DefaultMutableTreeNode selected;
    private String name;
    private Vector<Trace> vts = new Vector<Trace>();
    //   private int selectedStudy;
    transient private Vector<TraceManagerListener> vtml = new Vector<TraceManagerListener>();
    //private String base_study_name = "Study";
    private InteractivePanel interactivePanel;

    public void setManip(DefaultMutableTreeNode tmanip, String name) {
        this.tmanip = tmanip;
        setSelectedNode(tmanip);
        this.name = name;
        for (TraceManagerListener tml : vtml) {
            tml.newManip(tmanip);
        }
       /*if (!selected.isLeaf()) {
            //  System.out.println(selected.getNextLeaf());
            setSelectedNode(nextLeaf(selected));
        }*/
        nextStep();
    }

    public void nextStep() {
        this.vts = new Vector<Trace>();





        if (selected == null) {
            for (TraceManagerListener tml : vtml) {
                tml.manipEnds();
                // System.out.println("in");
            }
        } else {
            if (!selected.isLeaf()) {
                //  System.out.println(selected.getNextLeaf());
                setSelectedNode(nextLeaf(selected));
            }
            Experience e = (Experience) ((DefaultMutableTreeNode) selected.getParent()).getUserObject();
            Integer iter = (Integer) selected.getUserObject();
            String consigne = e.getConsigne();
            for (TraceManagerListener tml : vtml) {
                tml.manipNext(consigne + " -- " + iter,e.getWidth(),e.getHeight());
                // System.out.println("in");
            }
        }

    }

    public void newStepReturn() {
        Experience e = (Experience) ((DefaultMutableTreeNode) selected.getParent()).getUserObject();
        Integer iter = (Integer) selected.getUserObject();
        String path = Options.getExportPath();
        //System.out.println(path+"/"+name+"/"+e.getName());
        new File(path + "/" + name + "/" + e.getName() + "/").mkdirs();
        if (new File(path + "/" + name + "/" + e.getName() + "/" + iter + ".ex").exists()) {
            int verif = JOptionPane.showConfirmDialog(this.vtml.firstElement().getYou(), "File already exists !!!");
            if (verif != 0) {
                return;
            }
        }
        exportTraces(path + "/" + name + "/" + e.getName() + "/" + iter + ".ex");
        
        if(((Manip)tmanip.getUserObject()).isSaveImage()){
            try {
//                System.out.println(this.interactivePanel);
//                System.out.println(this.interactivePanel.getImage());
                ImageIO.write( this.interactivePanel.getImage(), "png", new File(path + "/" + name + "/" + e.getName() + "/" + iter + ".png"));
            } catch (IOException ex) {
                Logger.getLogger(TraceManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        

        setSelectedNode(nextLeaf(selected));
        nextStep();
    }

    public void addTrace(Trace t) {
        vts.add(t);
        Experience e = (Experience) ((DefaultMutableTreeNode) selected.getParent()).getUserObject();
        System.out.println(e.getName() +" "+e.getAutoNext());
        if(e.getAutoNext()){
            this.newStepReturn();
        }
    }

    public boolean hasBeenDone(DefaultMutableTreeNode n) {
        Experience e = (Experience) ((DefaultMutableTreeNode) n.getParent()).getUserObject();
        Integer iter = (Integer) n.getUserObject();
        String path = Options.getExportPath();

        return (new File(path + "/" + name + "/" + e.getName() + "/" + iter + ".ex").exists());
    }

    /*
     * public void removeTrace() { vts.remove(selectedStudy);
     * this.selectedStudy--; if (this.selectedStudy < 0) { this.selectedStudy =
     * 0; } for (TraceManagerListener tlm : vtml) { tlm.removedTraceStudy(); } }
     */

    /*
     * public void setStudyBaseName(String name) { this.base_study_name = name;
     * }
     *
     * public String getStudyBaseName() { return this.base_study_name; }
     *
     * public int getSelectedStudy() { return selectedStudy; }
     */
    public void addListener(TraceManagerListener tlm) {
        vtml.add(tlm);
    }

    /*
     * public void paint(Graphics2D g, boolean traceBleu, boolean traceRouge) {
     * if (!vts.isEmpty()) { vts.get(selectedStudy).paint(g, traceBleu,
     * traceRouge); } }
     *
     * public void setSelectedStudy(int selectedStudy) { this.selectedStudy =
     * selectedStudy; }
     *
     * public void setPlay(boolean p) { if (!vts.isEmpty()) {
     * this.vts.get(selectedStudy).setPlay(p); } }
     *
     */ public void setKnow(long know) {
        if (!vts.isEmpty()) {
            //  this.vts.get(selectedStudy).setKnow(know);
        }
    }/*
     *
     *
     * public void loadListener(TraceManagerListener tlm) { this.vtml = new
     * Vector<TraceManagerListener>(); this.addListener(tlm); }
     */


    public void exportTraces(String file) {
        try {
            File ff = new File(file);
            // ff.mkdirs();
            //System.out.println(ff.getAbsolutePath());
            //new File(ff.getAbsolutePath()).mkdirs();
            PrintWriter out = new PrintWriter(ff);
            for (Trace t : vts) {
                out.println("#");
                t.export(out);
            }
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TraceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Vector<Trace> getVts() {
        return vts;
    }

    public DefaultMutableTreeNode getManip() {
        return tmanip;
    }

    /*
     * void nodeSelected(DefaultMutableTreeNode node) { DefaultMutableTreeNode
     * e; if (!node.isLeaf()) { e = node.getNextLeaf(); } else { e = node; }
     * nextStep(); }
     */
    private int getIndex(DefaultMutableTreeNode node) {
        int i = 0;
        while (node.getPreviousLeaf() != null) {
            i++;
        }
        return i;
    }

    public void setSelectedNode(DefaultMutableTreeNode tn) {

        if (selected != tn) {
            this.selected = tn;

            for (TraceManagerListener tml : this.vtml) {
                tml.selectedNodeChanged(tn);
            }
            nextStep();
        }
    }

    public DefaultMutableTreeNode nextLeafNodeUp(DefaultMutableTreeNode p, int i) {
        DefaultMutableTreeNode n;
        if (p.isLeaf()) {

            DefaultMutableTreeNode par = (DefaultMutableTreeNode) p.getParent();
            if (par == null) {
                return null;
            }
            n = nextLeafNodeUp(par, par.getIndex(p));

        } else {
            DefaultMutableTreeNode nextChild;
            try {
                nextChild = (DefaultMutableTreeNode) p.getChildAt(i + 1);
            } catch (ArrayIndexOutOfBoundsException e) {
                nextChild = null;
            }

            if (nextChild == null) {
                DefaultMutableTreeNode par = (DefaultMutableTreeNode) p.getParent();
                if (par == null) {
                    return null;
                }
                n = nextLeafNodeUp(par, par.getIndex(p));
            } else {

                n = nextLeafNodeDown(nextChild);
            }

        }
        return n;
    }

    public DefaultMutableTreeNode nextLeafNodeDown(DefaultMutableTreeNode p) {
        DefaultMutableTreeNode n;
        if (p.isLeaf()) {
            n = p;

        } else {
            n = nextLeafNodeDown((DefaultMutableTreeNode) p.getFirstChild());

        }
        return n;
    }

    public DefaultMutableTreeNode nextLeaf(DefaultMutableTreeNode p) {
        if (p.isLeaf()) {
            return nextLeafNodeUp(p, 0);
        } else {
            return nextLeafNodeDown(p);
        }
    }

    public void setInteractivePanel(InteractivePanel aThis) {
        this.interactivePanel = aThis;
    }

    @Override
    public void paint(Graphics2D g,int h) {
        for(Trace t:getVts()){
            t.paint(g, Long.MAX_VALUE, h);
        }
    }
}
