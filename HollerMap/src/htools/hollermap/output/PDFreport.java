/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.hollermap.output;

import htools.hollermap.mat2java.plotter.Figure;
import htools.hollermap.mat2java.plotter.Box;
import htools.hollermap.mat2java.plotter.Plotter;
import htools.hollermap.mat2java.plotter.PlotType;
import htools.hollermap.mat2java.plotter.ToPanelResizer;
import gnu.jpdf.PDFJob;
import htools.hollermap.algorithms.CrossingReturn;
import htools.hollermap.algorithms.TraceAnalyzer;
import htools.hollermap.mat2java.Functions;
import htools.core.traces.Trace;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author rvlander
 */
public class PDFreport {

    TraceAnalyzer ta;
    String filename;
    private int ptype = PlotType.XYLINE;
    Plotter plot = new Plotter();

    public PDFreport(TraceAnalyzer ta, int ptype, String filename) {
        this.ta = ta;
        this.filename = filename;
        //this.setVisible(true);
        this.plotReport();
        try {
            this.report();
            // 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PDFreport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PDFreport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void plotReport() {
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





        plot.plot(trace0.Y, trace0.X, ptype, Color.BLUE);
        //   System.out.print(Arrays.toString(trace0.Y));

        plot.hold_on();
        plot.plot(sampley0, samplex0, ptype, Color.RED);
        plot.hold_on();
        plot.plot(ta.getOx_y(), ta.getOx_x(), PlotType.XYPLOTS, Color.ORANGE);
        plot.hold_on();
        plot.plot(ta.getOy_y(), ta.getOy_x(), PlotType.XYPLOTS, Color.GREEN);

        plot.figure();
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



        plot.figure();
        //plot.plot(zerosig, sample0, PlotType.XYLINE, Color.GRAY);
        plot.hold_on();
        plot.plot(dXsdT0, nT0, ptype, Color.RED);
        plot.hold_on();
        plot.plot(sampledx0, sample0, ptype, Color.magenta);
        plot.hold_on();
        plot.plot(new double[crx.t0.length], crx.t0, PlotType.XYPLOTS, Color.ORANGE);
        plot.hold_on();


    }

    public void report() throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        PDFJob job = new PDFJob(fos);

        double rap = 0.71;
        int w = 550;
        double h = w / rap;

        BufferedImage bi = new BufferedImage(w, (int) h, BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D) bi.getGraphics();

        int i = 0;

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, bi.getWidth(), bi.getHeight());






        for (Figure f : plot.getLesfigures()) {

            /*
             * if (fixedMode) { if (this.selected_figure == 0) {
             * f.getBox().correct(640, 400); } else { f.getBox().correct(3000,
             * 4); } }
             */

            g.translate(0, 10);

            ToPanelResizer tpr = f.getTpr();
            Box box = f.getBox();

            tpr.width = w;
            tpr.height = h / 3.2;

            double stepx = 100;
            double stepy = 0.1;
            String divx = "100ms";
            String divy = "0.1px/s";

            if (i == 0) {
                stepx = 10;
                stepy = 10;
                divx = "10px";
                divy = "10px";
            }




            g.setColor(Color.LIGHT_GRAY);

            for (double d = 0; d <= 10000; d += stepx) {
                if (d >= box.xmin && d <= box.xmax) {
                    g.drawLine(tpr.getX(d), tpr.getY(box.ymin),
                            tpr.getX(d), tpr.getY(box.ymax));
                }
                if (-d >= box.xmin && -d <= box.xmax) {
                    g.drawLine(tpr.getX(-d), tpr.getY(box.ymin),
                            tpr.getX(-d), tpr.getY(box.ymax));
                }
            }
            for (double d = 0; d <= 10000; d += stepy) {
                if (d >= box.ymin && d <= box.ymax) {
                    g.drawLine(tpr.getX(box.xmin), tpr.getY(d),
                            tpr.getX(box.xmax), tpr.getY(d));
                }
                if (-d >= box.ymin && -d <= box.ymax) {
                    g.drawLine(tpr.getX(box.xmin), tpr.getY(-d),
                            tpr.getX(box.xmax), tpr.getY(-d));
                }
            }


            g.drawString(divx, tpr.getX(box.xmin + 0.2 * box.getWidth() / 10), tpr.getY(box.ymin - 0.1 * box.getHeight() / 10));
            g.drawString(divy, tpr.getX(box.xmin - 0.5 * box.getWidth() / 10), tpr.getY(box.ymin + 0.5 * box.getHeight() / 10));

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

            f.paint(g, w, (int) (h / 3));

            g.setColor(Color.DARK_GRAY);
            if (i == 0) {
                g.drawString("Bleu : trace enregistrée, Rouge : trace synthétisée", w / 4, 10);
            } else if (i == 1) {
                g.drawString("Bleu : dy/dt enregistrée, Cyan : dy/dt synthétisée", w / 4, 10);
            } else {
                g.drawString("Rouge : dx/dt enregistrée, Magenta : dx/dt synthétisée", w / 4, 10);
            }

            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);

            g.setStroke(new BasicStroke(1));

            i++;
            g.translate(0, h / 3.2 + 5);

        }

        Graphics2D g1 = (Graphics2D) job.getGraphics();

        // g1.translate(20,50);
        g1.drawImage(bi, 30, 10, null);



        g1.dispose();
        job.end();
        fos.close();
    }
}
