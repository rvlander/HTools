/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package htools.hollermap.mat2java.plotter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;
import javax.swing.JPanel;

/**
 *
 * @author rvlander
 */
public class FigurePanel extends JPanel{

    private Vector<Plot> plots;
    private BufferedImage offScreenBuffer;
    private ToPanelResizer tpr;

    public FigurePanel(Vector<Plot> plots,ToPanelResizer tpr){
        super();
        this.plots = plots;
        this.tpr = tpr;
    }

    @Override
    public void update(Graphics g)
     {
          paint(g);
     }

    @Override
    public void paint(Graphics g1) {
        offScreenBuffer = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D) offScreenBuffer.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, tpr.getWidth(), tpr.getHeight());

        
        for(Plot p:plots){
            g.setColor(p.getColor1());
            switch(p.getType()){
                case PlotType.XYLINE :
                    for(int i=0;i<p.lesx.length-1;i++){
                        g.drawLine(tpr.getX(p.lesx[i]), tpr.getY(p.lesy[i]),
                                tpr.getX(p.lesx[i+1]), tpr.getY(p.lesy[i+1]));
                    }
                    break;
                case PlotType.XYPLOTS :
                    for(int i=0;i<p.lesx.length;i++){
                        g.fillOval(tpr.getX(p.lesx[i])-2, tpr.getY(p.lesy[i])-2,
                                4,4);
                    }
            }

            
        }

        g1.drawImage(offScreenBuffer, 0, 0, this);

    }

}
