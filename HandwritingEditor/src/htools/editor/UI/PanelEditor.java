/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.editor.UI;

import htools.editor.editor.Editor;
import htools.editor.editor.EditorListener;
import htools.editor.editor.SignalPair;
import htools.editor.matlabfunctions.MatlabMethods;
import hollermap.mat2java.plotter.*;
import htools.core.traces.Trace;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.synth.SynthSplitPaneUI;
import matlabcontrol.MatlabInvocationException;

/**
 *
 * @author rvlander
 */
public class PanelEditor extends JPanel implements FocusListener, EditorListener, ItemListener, MouseListener, MouseMotionListener, Darkenable {

    public static final int DEFAULT_MODE = 0;
    public static final int SELECTION_MODE = 1;
    public static final int MOVE_SELECTION_MODE = 2;
    public static final int RESIZE_SELECTION_MODE = 3;
    public static final int HAND_EDITION_MODE = 4;
    SignalPlotter ap;
    // private int selected_figure = 1;
    private boolean drawSelector = false;
    private int ptype = PlotType.XYLINE;
    private Editor editor;
    private JComboBox jl;
    private PanelEditorPopupMenu menu;
    private int mode;
    // private double[] selection;
    
    private DefaultModeMouseListener dml;
    private SelectionModeMouseListener sml;
    private MoveModeMouseListener mml;
    private HandEditionMouseListener heml;
    
    private PanelReportModeMouseListener current_listener;
    private int mouse_x;
    private int mouse_y;
    boolean dark;
    private Component me;
    private boolean dummySplit;
    private int dummySplitOrientation;
    private int dummySplitPos;
    
    private Player player;
    private boolean playing;

    public PanelEditor(Editor editor, PanelEditorPopupMenu menu) {
        me = this;

        this.mode = DEFAULT_MODE;

        this.editor = editor;
        this.editor.addListener(this);

        this.setFocusable(true);
        this.addFocusListener(this);

        dml = new DefaultModeMouseListener(this);
        sml = new SelectionModeMouseListener(this);
        mml = new MoveModeMouseListener(this);
        heml = new HandEditionMouseListener(this);

        this.setAllMouseListeners(dml);


        this.menu = menu;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.initializeKeyBindings();

        if (ap != null) {
            this.plotReport();

        }


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);



        jl = new JComboBox();
        jl.setModel(new javax.swing.DefaultComboBoxModel(Editor.EDS));
        //    this.add(jl);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(jl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 657, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 407, Short.MAX_VALUE)
                .addComponent(jl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));


        jl.addItemListener(this);
        jl.setFocusable(false);

        this.initializeAP();





    }

    private void initializeAP() {
          //System.out.println("there");
        if (editor.isWorkingTrace()) {
           // System.out.println("there");
            SignalPair sp = editor.getSignal(this.jl.getSelectedIndex());
            
            
          //  System.out.println("Here : "+sp.x+" " +Arrays.toString(sp.x));
            
            /*switch (this.jl.getSelectedIndex()) {
             case Editor.X_SIGNAL:
             sp = editor.getXsignal();
             break;
             case Editor.Y_SIGNAL:
             sp = editor.getYsignal();
             break;
             default:
             sp = editor.getGeometry();
             }*/
            this.ap = new SignalPlotter(sp);
            this.plotReport();
            this.repaint();
        }
    }

    /* public void setAnalyzerPlotter(AnalyzerPlotter ap) {
     this.ap = ap;
     this.ap.setSelected(0);
     this.plotReport();
     this.repaint();

     }*/
    @Override
    public void paintComponent(Graphics g1) {

        Graphics2D g = (Graphics2D) g1;
        int i = 0;


        if (this.hasFocus()) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(new Color(240, 245, 255));
        }
        g.fillRect(0, 0, this.getWidth(), this.getHeight());







        if (ap != null && ap.getSelected() != null) {
            Figure f = ap.getSelected();
            //    System.out.println(ap.plot.getLesfigures().get(0));


            /*
             * if (fixedMode) { if (this.selected_figure == 0) {
             * f.getBox().correct(640, 400); } else { f.getBox().correct(3000,
             * 4); } }
             */

            ToPanelResizer tpr = f.getTpr();
            Box box = f.getBox();

            tpr.width = this.getWidth();
            tpr.height = this.getHeight();


            /*
             * double stepx = 100; double stepy = 0.1; String divx = "100ms";
             * String divy = "0.1px/s";
             *
             * if (this.selected_figure == 0) { stepx = 10; stepy = 10; divx =
             * "10px"; divy = "10px"; }
             */


            double stepx = f.getStepX();
            double stepy = f.getStepY();
            String divx = f.getDivX();
            String divy = f.getDivY();


            if (drawSelector) {
                g.setColor(new Color(240, 245, 255));
                int xmin = Math.min(sml.getOrix(), sml.getDestx());
                int xmax = Math.max(sml.getOrix(), sml.getDestx());
                g.fillRect(xmin, 0, xmax - xmin, this.getHeight());
            }

            //  System.out.println(stepx +" " +stepy );

            g.setColor(Color.LIGHT_GRAY);

            for (double d = 0; d <= 30000; d += stepx) {
                if (d >= box.xmin
                        && d <= box.xmax) {
                    g.drawLine(tpr.getX(d), tpr.getY(box.ymin),
                            tpr.getX(d), tpr.getY(box.ymax));
                }
                if (-d >= box.xmin && -d
                        <= box.xmax) {
                    g.drawLine(tpr.getX(-d), tpr.getY(box.ymin),
                            tpr.getX(-d), tpr.getY(box.ymax));
                }
            }
            for (double d = 0; d
                    <= 30000; d += stepy) {
                if (d >= box.ymin && d <= box.ymax) {
                    g.drawLine(tpr.getX(box.xmin), tpr.getY(d), tpr.getX(box.xmax),
                            tpr.getY(d));
                }
                if (-d >= box.ymin && -d <= box.ymax) {
                    g.drawLine(tpr.getX(box.xmin), tpr.getY(-d), tpr.getX(box.xmax),
                            tpr.getY(-d));
                }
            }






            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g.setStroke(new BasicStroke(2));


            g.setColor(Color.darkGray);
            if (0 >= box.xmin && 0 <= box.xmax) {
                g.drawLine(tpr.getX(0), tpr.getY(box.ymin),
                        tpr.getX(0), tpr.getY(box.ymax));
            }
            if (0 >= box.ymin && 0 <= box.ymax) {
                g.drawLine(tpr.getX(box.xmin), tpr.getY(0),
                        tpr.getX(box.xmax), tpr.getY(0));
            }





            f.paint(g, this.getWidth(), this.getHeight());


            g.setColor(Color.DARK_GRAY);

            g.drawString(f.getComment(), this.getWidth() / 4, this.getHeight() / 25);

            g.drawString(divx, tpr.getX(box.xmin + 0.2 * box.getWidth()
                    / 10), tpr.getY(box.ymax + 0.1 * box.getHeight() / 10));
            g.drawString(divy, tpr.getX(box.xmin - 0.5 * box.getWidth()
                    / 10), tpr.getY(box.ymax - 0.5 * box.getHeight() / 10));

            Vector<String> ps = f.getParams();
            Vector<Double> vs = f.getValues();

            for (int j = 0; j < ps.size(); j++) {
                g.drawString(ps.get(i) + " : " + vs.get(i), tpr.getX(box.xmax - 0.3 * box.getWidth()), tpr.getY(box.ymax - (0.5 * box.getHeight() * (i + 2)) / 10));
            }


            g.setColor(Color.RED);
            g.drawString(this.getModeToString(), 100, this.getHeight() - 5);



            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);





            if (this.mode == MOVE_SELECTION_MODE || this.mode == RESIZE_SELECTION_MODE) {
                g.setColor(Color.DARK_GRAY);
                if (mml.isXContrained()) {
                    try {
                        int y = tpr.getY(MatlabMethods.mean(this.ap.signal.y.signal, this.ap.selection));
                        g.drawLine(0, y, this.getWidth(), y);
                    } catch (MatlabInvocationException ex) {
                        Logger.getLogger(PanelEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (mml.isYContrained()) {
                    try {
                        int x = tpr.getX(MatlabMethods.mean(this.ap.signal.x.signal, this.ap.selection));
                        g.drawLine(x, 0, x, this.getHeight());
                    } catch (MatlabInvocationException ex) {
                        Logger.getLogger(PanelEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            if(this.mode == HAND_EDITION_MODE){
                double[] x = this.heml.getTime();
                double[] y = this.heml.getAmpli();
                if(x!=null && y!=null){
                    for(int j=0;j<x.length-1;j++){
                        g.drawLine(tpr.getX(x[j]), tpr.getY(y[j]), tpr.getX(x[j+1]), tpr.getY(y[j+1]));
                    }
                }
            }
            


            if (dark) {
                g.setColor(new Color(0, 0, 0, 200));
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }

            if (dummySplit) {
                g.setStroke(new BasicStroke(7));
                g.setColor(Color.DARK_GRAY);
                if (this.dummySplitOrientation == JSplitPane.HORIZONTAL_SPLIT) {
                    g.drawLine(this.dummySplitPos, 0, this.dummySplitPos, this.getHeight());
                } else {
                    g.drawLine(0, this.dummySplitPos, this.getWidth(), this.dummySplitPos);
                }
                this.dummySplit = false;
            }

            g.setStroke(new BasicStroke(1));

            if(this.playing){
                //System.out.println("play");
                double[] time = editor.getTime();
                long t = player.getTime();
                int k=0;
                while(k<time.length && time[k]<=t){
                    k++;
                }
                if(k>=time.length){
                    this.playing = false;
                    player.stop();
                }else{
                    g.setColor(Color.RED);
                    SignalPair sp = editor.getSignal(this.getSelectedSignal());
                    double X = sp.x.signal[k];
                    double Y = sp.y.signal[k];
                    g.fillOval(tpr.getX(X)-5, tpr.getY(Y)-5, 10, 10);
                }
            }


        }
    }

    public void fixedSize() {
        /*
         * Figure f = plot.getLesfigures().get(this.selected_figure); if
         * (this.selected_figure == 0) { f.getBox().correct(640, 400); } else {
         * f.getBox().correct(3000, 4); }
         */
        this.repaint();
    }

    public void autoSize() {
        this.plotReport();
        this.repaint();

    }

    public void plotReport() {
        ap.plot();
        Figure f = ap.getSelected();
        //    System.out.println(ap.plot.getLesfigures().get(0));


        /*
         * if (fixedMode) { if (this.selected_figure == 0) {
         * f.getBox().correct(640, 400); } else { f.getBox().correct(3000,
         * 4); } }
         */

        ToPanelResizer tpr = f.getTpr();
        Box box = f.getBox();

        tpr.width = this.getWidth();
        tpr.height = this.getHeight();
    }

    public void afficheTrace() {
        //  this.selected_figure = 0;
        this.repaint();
    }

    public void afficheX() {
        //  this.selected_figure = 2;
        this.repaint();
    }

    public void afficheY() {
        //  this.selected_figure = 1;
        this.repaint();
    }
    int oldx;
    int oldy;

    public void zoomX(int r) {
        Figure f = ap.getSelected();
        f.getBox().zoomX(-r * 0.05);
    }

    public void zoomY(int r) {
        Figure f = ap.getSelected();
        f.getBox().zoomY(-r * 0.05);
    }

    public void zoom(int r) {
        Figure f = ap.getSelected();
        f.getBox().zoom(-r * 0.05);
    }

    public int getPtype() {
        return ptype;
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        //System.out.println("there");
        this.initializeAP();
    }

    public void splitPanel(int orientation, int pos) {
        Container p = this.getParent();
        p.remove(this);
        PanelEditor pe = new PanelEditor(editor, menu);
        //   pe.setAnalyzerPlotter(new SignalPlotter(editor.getGeometry()), editor);
        JSplitPane sp = new SplitPane(orientation, this, pe);
        sp.setDividerLocation(pos);

        //sp.setDividerLocation(orientation == JSplitPane.HORIZONTAL_SPLIT ? p.getWidth() / 2 : p.getHeight() / 2);


        p.add(sp);
        p.revalidate();


        p.repaint();
    }

    public void joinPanel() {
        Container splitpane = this.getParent();
        if (splitpane instanceof JSplitPane) {
            Container p = splitpane.getParent();
            p.remove(splitpane);
            p.add(this);
            p.revalidate();
            p.repaint();
        }
    }

    @Override
    public void workingTraceChanged() {
        //  System.out.print("there");
        this.initializeAP();
    }

    public int getSelectedSignal() {
        return jl.getSelectedIndex();
    }

    public void setSelectedSignal(int i) {
        jl.setSelectedIndex(i);
    }

    @Override
    public void focusGained(FocusEvent fe) {
        this.repaint();
    }

    @Override
    public void focusLost(FocusEvent fe) {
        this.repaint();
    }

    public int getMode() {
        return this.mode;
    }

    public String getModeToString() {
        String s;
        switch (this.mode) {
            case SELECTION_MODE:
                s = "Selection Mode";
                break;
            case MOVE_SELECTION_MODE:
                s = "Move Selection Mode";
                break;
            case RESIZE_SELECTION_MODE:
                s = "Resize Selection Mode";
                break;
            case HAND_EDITION_MODE:
                s = "Hand Edition Mode";
                break;
            default:
                s = "Normal Mode";
        }
        if (jl.getSelectedIndex() == Editor.GEOMETRY) {
            s = "Not Editable";
        }
        return s;
    }

    public void toggleMode(int new_mode) {
        if (jl.getSelectedIndex() != Editor.GEOMETRY) {
            if (this.mode == new_mode) {
                this.mode = DEFAULT_MODE;
            } else {
                this.mode = new_mode;
            }

            switch (this.mode) {
                case SELECTION_MODE:
                    this.setAllMouseListeners(this.sml);
                    break;
                case MOVE_SELECTION_MODE:
                case RESIZE_SELECTION_MODE:
                    this.setAllMouseListeners(this.mml);
                    this.mml.retainOri();
                    break;
                case HAND_EDITION_MODE:
                    this.setAllMouseListeners(this.heml);
                    break;
                default:
                    this.setAllMouseListeners(this.dml);
            }
            this.repaint();
        }
    }

    private void initializeKeyBindings() {
        InputMap im = this.getInputMap();
        ActionMap am = this.getActionMap();

        im.put(KeyStroke.getKeyStroke("B"), "baction");
        am.put("baction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                toggleMode(SELECTION_MODE);
            }
        });

        im.put(KeyStroke.getKeyStroke("G"), "gaction");
        am.put("gaction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                toggleMode(MOVE_SELECTION_MODE);
            }
        });

        im.put(KeyStroke.getKeyStroke("S"), "saction");
        am.put("saction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                toggleMode(RESIZE_SELECTION_MODE);
            }
        });

        im.put(KeyStroke.getKeyStroke("H"), "haction");
        am.put("haction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                toggleMode(HAND_EDITION_MODE);
            }
        });

        im.put(KeyStroke.getKeyStroke("A"), "aaction");
        am.put("aaction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                toggleAllSelection();
            }
        });

        im.put(KeyStroke.getKeyStroke("SPACE"), "spaceaction");
        am.put("spaceaction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                menu.show(me, mouse_x, mouse_y);
            }
        });

        im.put(KeyStroke.getKeyStroke("control Z"), "undoaction");
        am.put("undoaction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                editor.undo();
            }
        });

        im.put(KeyStroke.getKeyStroke("control Y"), "redoaction");
        am.put("redoaction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                editor.redo();
            }
        });

        im.put(KeyStroke.getKeyStroke("X"), "xaction");
        am.put("xaction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (mode == MOVE_SELECTION_MODE || mode == RESIZE_SELECTION_MODE) {
                    mml.toggleXconstrain();
                    repaint();
                }
            }
        });

        im.put(KeyStroke.getKeyStroke("Y"), "yaction");
        am.put("yaction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (mode == MOVE_SELECTION_MODE || mode == RESIZE_SELECTION_MODE) {
                    mml.toggleYconstrain();
                    repaint();
                }
            }
        });
        
        im.put(KeyStroke.getKeyStroke("D"), "daction");
        am.put("daction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
       
        im.put(KeyStroke.getKeyStroke("P"), "paction");
        am.put("paction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                menu.editor.play();
            }
        });


    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (this.getParent() instanceof SplitPane) {
            ((SplitPane) this.getParent()).mouseClicked(me, this, null);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        this.requestFocus();
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    public AnalyzerPlotter getAp() {
        return ap;
    }

    public void changePlotType() {
        ap.togglePtype();
        this.repaint();

    }

    public void setAllMouseListeners(PanelReportModeMouseListener l) {
        if (this.current_listener != null) {
            this.unsetAllMouseListeners(this.current_listener);
        }
        this.addMouseListener(l);
        this.addMouseMotionListener(l);
        this.addMouseWheelListener(l);
        this.current_listener = l;
    }

    public void unsetAllMouseListeners(PanelReportModeMouseListener l) {
        this.removeMouseListener(l);
        this.removeMouseMotionListener(l);
        this.removeMouseWheelListener(l);
    }

    public void setDrawSelector(boolean drawSelector) {
        this.drawSelector = drawSelector;
    }

    private void toggleAllSelection() {
        if (this.jl.getSelectedIndex() != Editor.GEOMETRY) {
            if (ap.isNothingSelected()) {
                ap.selectAll();
            } else {
                ap.selectNothing();
            }
            this.repaint();
        }
    }

    public void unSelect() {
        if (this.jl.getSelectedIndex() != Editor.GEOMETRY) {
            ap.selectNothing();
            this.repaint();
        }
    }

    public void select(double inf, double sup) {
        this.ap.select(inf, sup);
        this.repaint();
    }

    public void moveAndTimeShift(double dt, double dx) {

        double[] selection = this.ap.getSavedSelection();
        //System.out.println(Arrays.toString(selection));
        editor.moveAndTimeShift(this.jl.getSelectedIndex(), selection, dt, dx);
        try {
            selection = MatlabMethods.moveSelection(selection, dt, this.ap.signal.x.signal);
        } catch (MatlabInvocationException ex) {
            Logger.getLogger(PanelEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.ap.select(selection);
    }

    void reSizeSignal(double orit, double rt, double orix, double rx) {
        try {
            double[] selection = this.ap.getSavedSelection();
            SignalPair s = editor.getLastSignal(this.jl.getSelectedIndex());
            double Ot = MatlabMethods.mean(s.x.signal, selection);
            double Ox = MatlabMethods.mean(s.y.signal, selection);
            double ratio_t = (rt - Ot) / (orit - Ot);
            double ratio_x = (rx - Ox) / (orix - Ox);
            editor.reSize(this.jl.getSelectedIndex(), selection, Ot, ratio_t, Ox, ratio_x);


            selection = MatlabMethods.resizeSelection(this.ap.signal.x.signal, selection, Ot, ratio_t);

            this.ap.select(selection);
        } catch (MatlabInvocationException ex) {
            Logger.getLogger(PanelEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void smooth() {

        double[] selection = this.ap.getSelection();
        editor.smooth(this.jl.getSelectedIndex(), selection);
        this.ap.select(selection);
    }
    
    public void delete() {

        double[] selection = this.ap.getSelection();
        editor.delete(this.jl.getSelectedIndex(), selection);
        //this.ap.select(selection);
    }

    @Override
    public void signalsChanged() {

        double[] sel = ap.getSelection();
        double[] ssel = ap.getRealSavedSelection();
        Figure f = ap.getSelected();
        Box box = f.getBox();



        SignalPair sp = editor.getSignal(this.jl.getSelectedIndex());
        this.ap = new SignalPlotter(sp);
        this.plotReport();
        ap.getSelected().setBox(box);
        ap.select(sel);
        ap.setSavedSelection(ssel);
        this.repaint();
    }

    public void validateModifications() {
        this.ap.unsaveSelection(true);
        editor.validateModifications();
        this.toggleMode(DEFAULT_MODE);
    }

    public void cancelModifications() {
        this.ap.unsaveSelection(false);
        editor.cancelModifications();
        this.toggleMode(DEFAULT_MODE);
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        if (this.getParent() instanceof SplitPane) {
            ((SplitPane) this.getParent()).mouseMoved(me, this, null);
        }
        mouse_x = me.getX();
        mouse_y = me.getY();
    }

    @Override
    public void darken() {
        dark = true;
        this.repaint();
    }

    @Override
    public void lighten() {
        dark = false;
        this.repaint();
    }

    void drawDummySplit(int ori, int pos) {
        this.dummySplit = true;
        this.dummySplitPos = pos;
        this.dummySplitOrientation = ori;
        this.repaint();
    }

    void appendTrace(Trace T) {
        editor.appendTrace(T);
    }

    void handSignalRecord(double[] time, double[] ampli) {
        toggleMode(PanelEditor.DEFAULT_MODE);
        editor.handSignal(this.jl.getSelectedIndex(),time,ampli);
    }

    public void play(Player player) {
        this.player = player;
        this.playing = true;
        //System.out.println("play");
    }
}
