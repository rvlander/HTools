/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.hollermap.output;

import htools.hollermap.algorithms.TraceAnalyzer;
import htools.hollermap.mat2java.plotter.Box;
import htools.hollermap.mat2java.plotter.Figure;
import htools.hollermap.mat2java.plotter.PlotType;
import htools.hollermap.mat2java.plotter.Plotter;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author rvlander
 */
abstract public class AnalyzerPlotter implements Serializable{

    public int selected;
    Plotter plot = new Plotter();
    TraceAnalyzer ta;
    public int ptype;

    public Figure getSelected() {
        Figure res;
        if (plot.getLesfigures().isEmpty()) {
            res = null;
        } else {
            res = plot.getLesfigures().get(selected);
        }
        return res;
    }

    public void setSelected(int i) {
        this.selected = i;
    }

    public Vector<Figure> getFigures() {
        return plot.getLesfigures();
    }

    abstract public void plot();

    public void togglePtype() {
        Figure f = plot.getLesfigures().get(selected);
        Box sav = f.getBox();
        this.ptype = (ptype + 1) % PlotType.NB_MODES;
        this.plot();
        plot.getLesfigures().get(selected).getBox().setBox(sav.xmin, sav.xmax, sav.ymin, sav.ymax);
    }
}
