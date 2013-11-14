/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.editor.UI;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 *
 * @author rvlander
 */
public class SplitPane extends JSplitPane implements Darkenable {

    public static final int NORMAL = 0;
    public static final int VSPLIT = 1;
    public static final int HSPLIT = 2;
    public static final int JOIN = 3;
    private int state;//o normal, 1  hsplit, 2 vsplit, 3 join
    SplitPanePopupMenu menu;

    public SplitPane(int orientation, Component left, Component right) {
        super(orientation, left, right);

        menu = new SplitPanePopupMenu(this);
        // this.addMouseListener(this);
        // this.addMouseMotionListener(this);

        this.setResizeWeight(0.5);
        BasicSplitPaneDivider bsd = ((BasicSplitPaneUI) this.getUI()).getDivider();


        MouseListener ml = bsd.getMouseListeners()[0];

        bsd.removeMouseMotionListener((MouseMotionListener) ml);
        bsd.removeMouseListener(ml);

        SplitPaneDividerMouseListener sml = new SplitPaneDividerMouseListener(ml, (MouseMotionListener) ml);
        bsd.addMouseListener(sml);
        bsd.addMouseMotionListener(sml);

    }

    public void mouseClicked(MouseEvent e, PanelEditor pe, SplitPane sp) {
        if (this.getParent() instanceof SplitPane) {
            ((SplitPane) this.getParent()).mouseClicked(e, pe, this);
        }
        if (state != SplitPane.NORMAL) {
            int modif = e.getModifiers();
            if ((modif & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                switch (state) {
                    case VSPLIT:
                        this.vsplit(pe,e.getY());
                        break;
                    case HSPLIT:
                        this.hsplit(pe,e.getX());
                        break;
                    case JOIN:
                        this.join(pe, sp);
                        break;
                }
            }
            if ((modif & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
            }
            setState(NORMAL);
            this.lighten();
        }
    }

    private void vsplit(PanelEditor pe,int pos) {
        pe.splitPanel(JSplitPane.VERTICAL_SPLIT,pos);
    }

    private void hsplit(PanelEditor pe,int pos) {
        pe.splitPanel(JSplitPane.HORIZONTAL_SPLIT,pos);
    }

    private void join(PanelEditor pe, SplitPane sp) {
        if (sp == null) {
            pe.joinPanel();
        } else {
            sp.joinPanel();
        }

    }

    public void setState(int state) {
        //   System.out.println("Setting state to : " + state);
        this.state = state;
    }

    private void joinPanel() {
        Container splitpane = this.getParent();
        if (splitpane instanceof JSplitPane) {
            Container p = splitpane.getParent();
            p.remove(splitpane);
            p.add(this);
            p.revalidate();
            p.repaint();
        }
    }

    void mouseMoved(MouseEvent me, PanelEditor pe, SplitPane sp) {
        if (this.getParent() instanceof SplitPane) {
            ((SplitPane) this.getParent()).mouseMoved(me, pe, this);
        }
        if (state == JOIN) {
            if (pe == this.getLeftComponent() || sp == this.getLeftComponent()) {
                ((Darkenable) this.getRightComponent()).darken();
                ((Darkenable) this.getLeftComponent()).lighten();
            } else {
                ((Darkenable) this.getLeftComponent()).darken();
                ((Darkenable) this.getRightComponent()).lighten();
            }
        }
        if (state == VSPLIT || state==HSPLIT){
            pe.drawDummySplit(state==VSPLIT?JSplitPane.VERTICAL_SPLIT:JSplitPane.HORIZONTAL_SPLIT,state==VSPLIT?me.getY():me.getX());
        }
    }

    @Override
    public void darken() {
        try {
            ((Darkenable) this.getLeftComponent()).darken();
            ((Darkenable) this.getRightComponent()).darken();
        } catch (NullPointerException e) {
        }
    }

    @Override
    public void lighten() {
        try {
            ((Darkenable) this.getLeftComponent()).lighten();
            ((Darkenable) this.getRightComponent()).lighten();
        } catch (NullPointerException e) {
        }
    }

    public void play(Player player) {
           Component c = this.getLeftComponent();
        if(c instanceof SplitPane){
            ((SplitPane)c).play(player);
        }else{
            ((PanelEditor)c).play(player);
        }
        c = this.getRightComponent();
        if(c instanceof SplitPane){
            ((SplitPane)c).play(player);
        }else{
            ((PanelEditor)c).play(player);
        }

    }

    public class SplitPaneDividerMouseListener implements MouseListener, MouseMotionListener {

        MouseListener ml;
        MouseMotionListener mml;

        public SplitPaneDividerMouseListener(MouseListener ml, MouseMotionListener mml) {
            this.ml = ml;
            this.mml = mml;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int modif = e.getModifiers();
            if ((modif & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                ml.mouseClicked(e);
            }
            if ((modif & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
                menu.show((Component) e.getSource(), e.getX(), e.getY());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int modif = e.getModifiers();
            if ((modif & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                ml.mousePressed(e);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int modif = e.getModifiers();
            if ((modif & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                ml.mouseReleased(e);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //ml.mouseEntered(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //ml.mouseExited(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int modif = e.getModifiers();
            if ((modif & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                mml.mouseDragged(e);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // mml.mouseMoved(e);
        }
    }
}
