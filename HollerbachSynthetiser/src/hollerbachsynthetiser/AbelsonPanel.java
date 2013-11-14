/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hollerbachsynthetiser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author rvlander
 */
public class AbelsonPanel extends JPanel implements NextObserver {

    ParametersHollerbach next;

    private int H = 600;
    private int W = 600;

    BufferedImage offScreenBuffer;

    public AbelsonPanel(){
        super();
        this.setPreferredSize(new Dimension(W,H));
        this.repaint();

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

        g.setColor(Color.gray);
        g.draw(new Line2D.Double(W/2,70,W/2,H-70));
        g.draw(new Line2D.Double(70,H/2,W-70,H/2));

        g.setColor(Color.red);

        double dy=20,nx,ny,t = 0;
        double oldx,x = next.a*Math.sin(next.wx*t+next.phix);
        double oldy,y = next.b*Math.sin(next.wy*t+next.phiy);
        oldx =x;
        oldy=y;
        while(t<1|| ((oldx-x)*(oldx-x) + (oldy-y)*(oldy-y)) > 5 ){
                t+=0.01;
                nx = next.a*Math.sin(next.wx*t+next.phix);
		ny = next.b*Math.sin(next.wy*t+next.phiy);
                g.draw(new Line2D.Double(x+W/2,-y+H/2,nx+W/2,-ny+H/2));
                x = nx;
                y = ny;
                dy = Math.sin(next.wy*t+next.phiy);
        }

        g1.drawImage(offScreenBuffer, 0, 0, this);
        
    }
    public void updated(ParametersHollerbach h) {
        this.next = h;
       // System.out.println("abel : "+next);
        this.repaint();
    }

    public void next(int deactive) {
       // throw new UnsupportedOperationException("Not supported yet.");
    }

}
