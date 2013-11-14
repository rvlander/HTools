/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.UI;

import hollermap.mat2java.plotter.Figure;
import hollermap.mat2java.plotter.ToPanelResizer;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author rvlander
 */
public class DefaultModeMouseListener extends PanelReportModeMouseListener {

    private int oldx;
    private int oldy;

    public DefaultModeMouseListener(PanelEditor e) {
        super(e);
    }

    public void mouseDragged(MouseEvent me) {
        int modif = me.getModifiers();
        if ((modif & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            Figure f = editor.getAp().getSelected();
            ToPanelResizer tpr = f.getTpr();
            double dx = tpr.getRevX(me.getX()) - tpr.getRevX(oldx);
            double dy = tpr.getRevY(me.getY()) - tpr.getRevY(oldy);

            oldx = me.getX();
            oldy = me.getY();
            f.getBox().translate(-dx, -dy);
            editor.repaint();
        } /*else if ((modif & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
         destx = me.getX();
         desty = me.getY();
         this.repaint();
         }*/
    }

    public void mouseMoved(MouseEvent me) {
        /*
         * if(!plot.getLesfigures().isEmpty()){ Figure f =
         * plot.getLesfigures().get(this.selected_figure); ToPanelResizer tpr =
         * f.getTpr();
         */

        oldx = me.getX();
        oldy = me.getY();

        // System.out.println(tpr.getRevX(oldx) +" " +tpr.getRevY(oldy));
        // }
    }

    public void mouseWheelMoved(MouseWheelEvent mwe) {
        int r = mwe.getWheelRotation();
        if (mwe.getX() >= 0.9 * editor.getWidth()) {
            editor.zoomY(r);
        } else if (mwe.getY() >= 0.9 * editor.getHeight()) {
            editor.zoomX(r);
        } else {
            editor.zoom(r);
        }
        editor.repaint();
    }

    public void mouseClicked(MouseEvent me) {
        int modif = me.getModifiers();
        if ((modif & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
            editor.unSelect();
        } 
    }

 /*   public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
        //this.repaint();
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }*/
}
