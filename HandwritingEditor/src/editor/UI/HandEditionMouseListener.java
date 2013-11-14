/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.UI;

import hollermap.mat2java.plotter.Figure;
import hollermap.mat2java.plotter.ToPanelResizer;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 *
 * @author rvlander
 */
public class HandEditionMouseListener extends PanelReportModeMouseListener {

    private ArrayList<Double> time;
    private ArrayList<Double> ampli;

    HandEditionMouseListener(PanelEditor pe) {
        super(pe);
        time = new ArrayList<Double>();
        ampli = new ArrayList<Double>();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Figure f = editor.getAp().getSelected();
        ToPanelResizer tpr = f.getTpr();
        time.add(tpr.getRevX(e.getX()));
        ampli.add(tpr.getRevY(e.getY()));
        editor.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        editor.handSignalRecord(getTime(), getAmpli());
        time = new ArrayList<Double>();
        ampli = new ArrayList<Double>();
    }

    public double[] getTime() {
        double[] res = new double[time.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = time.get(i);
        }
        return res;
    }

    public double[] getAmpli() {
        double[] res = new double[ampli.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = ampli.get(i);
        }
        return res;
    }
}
