/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hollermap.output;

import hollermap.mat2java.plotter.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.JPanel;

/**
 *
 * @author rvlander
 */
public class PanelReport extends JPanel implements MouseMotionListener, MouseWheelListener, MouseListener {

    AnalyzerPlotter ap;
    // private int selected_figure = 1;
    private int orix, oriy, destx, desty;
    private boolean drawSelector = false;
    private int ptype = PlotType.XYLINE;

    public PanelReport() {


        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addMouseListener(this);

        if (ap != null) {
            this.plotReport();
        }


    }

    public void setAnalyzerPlotter(AnalyzerPlotter ap) {
        this.ap = ap;
    }

    public void paint(Graphics g1) {

        Graphics2D g = (Graphics2D) g1;
        int i = 0;

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());







        if (ap != null && ap.getSelected() != null) {
            Figure f = ap.getSelected();

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
            
            for(int j=0;j<ps.size();j++){
                g.drawString(ps.get(i) +" : "+ vs.get(i), tpr.getX(box.xmax - 0.3 * box.getWidth())
                        , tpr.getY(box.ymax - (0.5 * box.getHeight()*(i+2))/ 10));
            }


            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);


            g.setStroke(new BasicStroke(1));



            if (drawSelector) {
                g.setColor(Color.DARK_GRAY);
                int xmin = Math.min(orix, destx);
                int xmax = Math.max(orix, destx);
                int ymin = Math.min(oriy, desty);
                int ymax = Math.max(oriy, desty);
                g.drawRect(xmin, ymin, xmax - xmin, ymax - ymin);
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

    public void mouseDragged(MouseEvent me) {
        int modif = me.getModifiers();
        if ((modif & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
            Figure f = ap.getSelected();
            ToPanelResizer tpr = f.getTpr();
            double dx = tpr.getRevX(me.getX()) - tpr.getRevX(oldx);
            double dy = tpr.getRevY(me.getY()) - tpr.getRevY(oldy);

            oldx = me.getX();
            oldy = me.getY();
            f.getBox().translate(-dx, -dy);
            this.repaint();
        } else if ((modif & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            destx = me.getX();
            desty = me.getY();
            this.repaint();
        }
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

    public void mouseWheelMoved(MouseWheelEvent mwe) {
        int r = mwe.getWheelRotation();
        if (mwe.getX() >= 0.9 * this.getWidth()) {
            zoomY(r);
        } else if (mwe.getY() >= 0.9 * this.getHeight()) {
            zoomX(r);
        } else {
            zoom(r);
        }
        this.repaint();
    }

    public void mouseClicked(MouseEvent me) {
    }

    public void mousePressed(MouseEvent me) {
        int modif = me.getModifiers();
        if ((modif & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            // System.out.println("Salut les zozos");
            orix = me.getX();
            oriy = me.getY();
            this.drawSelector = true;
        }
    }

    public void mouseReleased(MouseEvent me) {
        int modif = me.getModifiers();
        if ((modif & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            Figure f = ap.getSelected();
            ToPanelResizer tpr = f.getTpr();
            //double dx = tpr.getRevX(me.getX()) - tpr.getRevX(oldx);
            //double dy = tpr.getRevY(me.getY()) - tpr.getRevY(oldy);
            f.getBox().setBox(tpr.getRevX(orix), tpr.getRevX(destx), tpr.getRevY(oriy), tpr.getRevY(desty));
           // System.out.println(tpr.getRevX(orix) +" "+ tpr.getRevX(destx)+" "+
           //         tpr.getRevY(oriy)+" "+ tpr.getRevY(desty));
            drawSelector = false;
        }
        this.repaint();
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public void changePlotType() {
        ap.togglePtype();
        this.repaint();

    }

    public int getPtype() {
        return ptype;
    }
}
