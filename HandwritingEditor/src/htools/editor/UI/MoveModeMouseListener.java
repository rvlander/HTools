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
public class MoveModeMouseListener extends PanelReportModeMouseListener {

    private int desty, oriy, destx, orix;
    private boolean XContrained, YContrained;

    public MoveModeMouseListener(PanelEditor e) {
        super(e);
    }

    public void mouseMoved(MouseEvent me) {

        if (oriy == 0) {
            oriy = me.getY();
        }

        if (orix == 0) {
            orix = me.getX();
        }


        Figure f = editor.getAp().getSelected();
        ToPanelResizer tpr = f.getTpr();
        desty = me.getY();
        destx = me.getX();
        double oriyn = tpr.getRevY(oriy);
        double destyn = tpr.getRevY(desty);
        double orixn = tpr.getRevX(orix);
        double destxn = tpr.getRevX(destx);
        //      System.err.println(tpr.getRevY(me.getY()));
        // editor.select(Math.min(orixn, destxn), Math.max(orixn, destxn));
        //   editor.move();
        if (editor.getMode() == PanelEditor.MOVE_SELECTION_MODE) {
            editor.moveAndTimeShift(YContrained ? 0 : destxn - orixn, XContrained ? 0 : destyn - oriyn);
        }
        if(editor.getMode() == PanelEditor.RESIZE_SELECTION_MODE) {
            editor.reSizeSignal(orixn,YContrained ? orixn : destxn,oriyn, XContrained ? oriyn : destyn);
        }



        //    oriy=desty;
        //      orix=destx;



        /*else if ((modif & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
         destx = me.getX();
         desty = me.getY();
         this.repaint();
         }*/
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        int modif = me.getModifiers();
        if ((modif & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            oriy = 0;
            orix = 0;

            editor.validateModifications();
        }
        if ((modif & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
            oriy = 0;
            orix = 0;

            editor.cancelModifications();
        }
        clearConstrains();
    }

    public void retainOri() {
        orix = 0;
        oriy = 0;
    }

    public void toggleXconstrain() {
        YContrained = false;
        XContrained = !XContrained;
    }

    public void toggleYconstrain() {
        XContrained = false;
        YContrained = !YContrained;
    }

    public void clearConstrains() {
        YContrained = false;
        XContrained = false;
    }

    public boolean isXContrained() {
        return XContrained;
    }

    public boolean isYContrained() {
        return YContrained;
    }
}
