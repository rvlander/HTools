/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package htools.hollermap.mat2java.plotter;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Vector;
import javax.swing.JFrame;

/**
 *
 * @author rvlander
 */
public class JFrameFigure implements ComponentListener{

    Vector<Plot> plots;
    Box box;
    JFrame jf;
    int width;
    int height;
    FigurePanel fp;
    ToPanelResizer tpr;

    public JFrameFigure(){
        tpr = new ToPanelResizer();
        box = new Box();
        box.ymin = Double.MAX_VALUE;
        box.xmin = Double.MAX_VALUE;
        box.ymax = Double.MIN_VALUE;
        box.xmax = Double.MIN_VALUE;
        tpr.box = box;
        plots = new Vector<Plot>();
        jf = new JFrame("Figure");
        jf.setSize(600, 400);
        fp = new FigurePanel(plots,tpr);
        fp.addComponentListener(this);
        jf.getContentPane().add(fp);
        //jf.getContentPane().addComponentListener(this);
        
        
        jf.setVisible(true);
    }

    public void addPlot(Plot p){
        plots.add(p);
        box.merge(p.getBox());
        tpr.width = this.fp.getWidth();
        tpr.height = this.fp.getHeight();
        fp.repaint();
        //System.out.println(box.xmin + " "+box.xmax);
    }

    public void componentResized(ComponentEvent e) {
        //System.out.println(e.paramString());
       // String regex = "x| ";
        //String[] ss = e.paramString().split(regex);
        //tpr.width = Double.parseDouble(ss[2]);
        //tpr.height = Double.parseDouble(ss[3].replace(')', ' ').split(" ")[0]);
        //System.out.println(width +" "+ height);
        tpr.width = this.fp.getWidth();
        tpr.height = this.fp.getHeight();
       // fp.repaint();
    }



    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void setExitOnClose(){
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
