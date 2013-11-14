/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.editor.UI;

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
public class SelectionModeMouseListener extends PanelReportModeMouseListener {

    private int destx, orix;
    private int desty, oriy;

    public SelectionModeMouseListener(PanelEditor e) {
        super(e);
    }

    public void mouseDragged(MouseEvent me) {
        int modif = me.getModifiers();
        if ((modif & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
           // Figure f = editor.getAp().getSelected();
           // ToPanelResizer tpr = f.getTpr();
            destx = me.getX();
            desty = me.getY();
           // double orixn = tpr.getRevX(orix);
           // double destxn = tpr.getRevX(destx);
           // editor.select(Math.min(orixn, destxn), Math.max(orixn, destxn));
            editor.repaint();
        } /*else if ((modif & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
         destx = me.getX();
         desty = me.getY();
         this.repaint();
         }*/
    }

    public void mousePressed(MouseEvent me) {
        int modif = me.getModifiers();
        if ((modif & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            orix = me.getX();
            oriy = me.getY();

            editor.setDrawSelector(true);
            //editor.repaint();
        }
    }

    public void mouseReleased(MouseEvent me) {
        int modif = me.getModifiers();
        if ((modif & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            Figure f = editor.getAp().getSelected();
            ToPanelResizer tpr = f.getTpr();
            //double dx = tpr.getRevX(me.getX()) - tpr.getRevX(oldx);
            //double dy = tpr.getRevY(me.getY()) - tpr.getRevY(oldy);
            //f.getBox().setBox(tpr.getRevX(orix), tpr.getRevX(destx), tpr.getRevY(oriy), tpr.getRevY(desty));
            double orixn = tpr.getRevX(orix);
            double destxn = tpr.getRevX(destx);
            editor.select(Math.min(orixn, destxn), Math.max(orixn, destxn));
            // System.out.println(tpr.getRevX(orix) +" "+ tpr.getRevX(destx)+" "+
            //         tpr.getRevY(oriy)+" "+ tpr.getRevY(desty));
            editor.setDrawSelector(false);
            editor.toggleMode(PanelEditor.DEFAULT_MODE);

        }
    }

    public int getDestx() {
        return destx;
    }

    public int getOrix() {
        return orix;
    }

    public int getDesty() {
        return desty;
    }

    public int getOriy() {
        return oriy;
    }
}
