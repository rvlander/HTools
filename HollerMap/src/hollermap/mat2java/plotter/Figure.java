/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hollermap.mat2java.plotter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author rvlander
 */
public class Figure implements Serializable{

    /**
     *
     * @author rvlander
     */
    
    Vector<String> params;
    Vector<Double> values;
    
    
    Vector<Plot> plots;
    Box box;
    int width;
    int height;
    ToPanelResizer tpr;
    
    double stepx;
    double stepy;
    String divx;
    String divy;
    String name;
    String comment;

    public String getComment() {
        return comment;
    }
    
    
    
    
    public String getName() {
        return name;
    }

    public String getDivX() {
        return divx;
    }

    public String getDivY() {
        return divy;
    }

    public double getStepX() {
        return stepx;
    }

    public double getStepY() {
        return stepy;
    }

    public Figure() {
        tpr = new ToPanelResizer();
        box = new Box();
        box.ymin = Double.MAX_VALUE;
        box.xmin = Double.MAX_VALUE;
        box.ymax = Double.MIN_VALUE;
        box.xmax = Double.MIN_VALUE;
        tpr.box = box;
        plots = new Vector<Plot>();
        params = new Vector<String>();
        values = new Vector<Double>();
    }
    
    public Figure(String name,double sx,double sy, String dx, String dy,String com){
        this();
        stepx = sx;
        stepy = sy;
        divx = dx;
        divy = dy;
        this.name = name;
        this.comment = com;
    }

    public void addPlot(Plot p) {
        plots.add(p);
        box.merge(p.getBox());
    }

    public void paint(Graphics2D g, int height, int width) {
        tpr.width = height;
        tpr.height = width;









        for (Plot p : plots) {
            switch (p.getType()) {
                case PlotType.XYLINE:
                    for (int i = 0; i < p.lesx.length - 1; i++) {
                        Color c = p.getMask()[i]==0?p.getColor1():p.getColor2();
                        g.setColor(c);
                        g.drawLine(tpr.getX(p.lesx[i]), tpr.getY(p.lesy[i]),
                                tpr.getX(p.lesx[i + 1]), tpr.getY(p.lesy[i + 1]));
                        //}
                    }
                    break;
                case PlotType.XYPLOTS:
                    for (int i = 0; i < p.lesx.length; i++) {
                        Color c = p.getMask()[i]==0?p.getColor1():p.getColor2();
                        g.setColor(c);
                        // if (box.inbox(p.lesx[i], p.lesy[i])) {
                        g.fillOval(tpr.getX(p.lesx[i]) - 5, tpr.getY(p.lesy[i]) - 5,
                                10, 10);
                        // }
                    }
                    break;
                case PlotType.XYPLOTS_LINED:
                    g.setStroke(new BasicStroke(1));
                    for (int i = 0; i < p.lesx.length - 1; i++) {
                        Color c = p.getMask()[i]==0?p.getColor1():p.getColor2();
                        g.setColor(c);
                        
                        g.fillOval(tpr.getX(p.lesx[i]) - 5, tpr.getY(p.lesy[i]) - 5,
                                10, 10);
                        g.drawLine(tpr.getX(p.lesx[i]), tpr.getY(p.lesy[i]),
                                tpr.getX(p.lesx[i + 1]), tpr.getY(p.lesy[i + 1]));
                    }
                    Color c = p.getMask()[p.lesx.length - 1]==0?p.getColor1():p.getColor2();
                        g.setColor(c);
                    g.fillOval(tpr.getX(p.lesx[p.lesx.length - 1]) - 5, tpr.getY(p.lesy[p.lesx.length - 1]) - 5,
                            10, 10);
            }


        }

        //g1.drawImage(offScreenBuffer, 0, 0, null);

    }

    public ToPanelResizer getTpr() {
        return tpr;
    }

    public Box getBox() {
        return box;
    }
    
     public void setBox(Box box) {
        this.box =box;
        this.tpr.box = box;
    }
    
    @Override
    public String toString(){
        return name;
    }

    void addValue(String str, double val) {
        this.params.add(str);
        this.values.add(val);
    }

    public Vector<String> getParams() {
        return params;
    }

    public Vector<Double> getValues() {
        return values;
    }
    
    
}
