/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hollermap.output;

import hollermap.algorithms.TTLAnalyzer;
import hollermap.algorithms.TraceAnalyzer;
import hollermap.mat2java.plotter.Plotter;
import java.awt.Color;

/**
 *
 * @author rvlander
 */
public class TTLPlotter extends AnalyzerPlotter {

    public TTLPlotter(TraceAnalyzer ta) {
        this.ta = ta;
    }

    @Override
    public void plot() {
        plot = new Plotter();
        TTLAnalyzer tat = (TTLAnalyzer) ta;
        plot.figure("2/3", 1, 1, "1", "1", "Log de la courbure en fonction de log de la vitesse tangentielle");
        plot.addValue("a",tat.getA());
        plot.plot(tat.getLogv(),tat.getLogcurve() , ptype, Color.BLUE);
        plot.hold_on();
        plot.plot(tat.getY(),tat.getT() , ptype, Color.RED);
    }
}
