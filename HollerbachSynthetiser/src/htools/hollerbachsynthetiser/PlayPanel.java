/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package htools.hollerbachsynthetiser;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author rvlander
 */
public class PlayPanel extends JPanel implements ActionListener{

    private int H = 300;
    private int W = 1024;

    private double startx = 20;
    private double starty = 20;

    public double letemps=0;

    private BufferedImage offScreenBuffer;

    private Vector<ParametersHollerbach> suite;


    public PlayPanel(){
        super();
        new Timer(40,this).start();

    }

    @Override
    public void update(Graphics g)
     {
          paint(g);
     }

    @Override
    public void paint(Graphics g1) {
        offScreenBuffer = new BufferedImage(W,H,BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D) offScreenBuffer.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, W, H);


        double offx,offy,x,y,nx,ny,t=0;
        x = 20;
	y = 0;

        //on commence par tracer les anciens parametres
        g.setColor(Color.black);
        for (ParametersHollerbach ph : suite){
            offx = x  +ph.a*Math.cos(ph.wx*t+ph.phix)/ph.wx - ph.c*t;
            offy = y  +ph.b*Math.cos(ph.wy*t+ph.phiy)/ph.wy;
            while(t<ph.ftime && t<this.letemps){
                t+=0.01;
                nx = -ph.a*Math.cos(ph.wx*t+ph.phix)/ph.wx + ph.c*t + offx;
		ny = -ph.b*Math.cos(ph.wy*t+ph.phiy)/ph.wy +offy;
                if(!ph.up || ((int)(t*100))%4==0 ){
                    g.draw(new Line2D.Double(x*2,-y*2+H/2,nx*2,-ny*2+H/2));
                }
                x = nx;
                y = ny;
            }
            if(t>=this.letemps) break;
        }

   

        g1.drawImage(offScreenBuffer, 0, 0, this);

    }

    public void setSuite(Vector<ParametersHollerbach> suite) {
        this.suite = suite;
    }

    public void actionPerformed(ActionEvent e) {
        letemps+=0.05;
        if (suite != null && letemps > this.suite.lastElement().ftime) letemps = 0;
        this.repaint();

    }




}
