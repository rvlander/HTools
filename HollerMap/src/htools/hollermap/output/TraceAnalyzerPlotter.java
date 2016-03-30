/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.hollermap.output;

import htools.hollermap.algorithms.CrossingReturn;
import htools.hollermap.algorithms.TraceAnalyzer;
import htools.hollermap.mat2java.plotter.PlotType;
import htools.hollermap.mat2java.plotter.Plotter;
import htools.core.traces.Trace;
import java.awt.Color;

/**
 *
 * @author rvlander
 */
public class TraceAnalyzerPlotter extends AnalyzerPlotter{
    
    
     public TraceAnalyzerPlotter(TraceAnalyzer ta){
         this.ta = ta;
     }
    
     
     public void plot(){
          // g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        //         RenderingHints.VALUE_ANTIALIAS_ON);

        // g.setStroke(new BasicStroke(2));
        plot = new Plotter();

        Trace trace0 = ta.getTrace();
        double[] sample0 = ta.getSampling();
        double[] sampledx0 = ta.getSampledx();
        double[] sampledy0 = ta.getSampledy();
        double[] samplex0 = ta.getSamplex();
        double[] sampley0 = ta.getSampley();
        double[] dYsdT0 = ta.getdYsdT();
        double[] fdYsdT0 = ta.getFdYsdT();
        double[] dXsdT0 = ta.getdXsdT();
        double[] nT0 = ta.getnT();
        CrossingReturn crx = ta.getCrx();
        CrossingReturn cry = ta.getCry();





        double[] zerosig = new double[sample0.length];




         plot.figure("Trace",10,10,"10px","10px","Bleu : trace   * enregistrée, Rouge : trace synthétisée");
        plot.plot(trace0.Y, trace0.X,ptype, Color.BLUE);
       //   System.out.print(Arrays.toString(trace0.Y));
        
        plot.hold_on();
        plot.plot(sampley0, samplex0, ptype, Color.RED);
        plot.hold_on();
        plot.plot(ta.getOx_y(), ta.getOx_x(), PlotType.XYPLOTS, Color.ORANGE);
        plot.hold_on();
        plot.plot(ta.getOy_y(), ta.getOy_x(), PlotType.XYPLOTS, Color.GREEN);


        plot.figure("Speed Y",100,0.1,"100ms","0.1px/s","Bleu : dy/dt enregistrée, Cyan : dy/dt synthétisée");
        //plot.plot(zerosig, sample0, PlotType.XYLINE, Color.GRAY);
        plot.hold_on();
        // plot.plot(dYsdT0, nT0);
        // plot.hold_on();
        plot.plot(dYsdT0, nT0, ptype, Color.BLUE);
        plot.hold_on();
        plot.plot(sampledy0, sample0, ptype, Color.CYAN);
        plot.hold_on();
        plot.plot(new double[cry.t0.length], cry.t0, PlotType.XYPLOTS, Color.GREEN);
        //plot.plot(cry.s0, cry.t0, PlotType.XYPLOTS, Color.green);
        
      


        plot.figure("Speed X",100,0.1,"100ms","0.1px/s","Rouge : dx/dt enregistrée, Magenta : dx/dt synthétisée");
        //plot.plot(zerosig, sample0, PlotType.XYLINE, Color.GRAY);
        plot.hold_on();
        plot.plot(dXsdT0, nT0, ptype, Color.RED);
        plot.hold_on();
        plot.plot(sampledx0, sample0, ptype, Color.magenta);
        plot.hold_on();
        plot.plot(new double[crx.t0.length], crx.t0, PlotType.XYPLOTS, Color.ORANGE);
        plot.hold_on();


     }
    
}
