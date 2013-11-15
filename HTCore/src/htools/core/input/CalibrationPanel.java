/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package htools.core.input;

import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author rvlander
 */
public class CalibrationPanel extends JFrame implements SamplerListener,KeyListener{
    
     private int xmin = Integer.MAX_VALUE;
     private int xmax = Integer.MIN_VALUE;
     private int ymin = Integer.MAX_VALUE;
     private int ymax = Integer.MIN_VALUE;
     
     private WintabSampler ws;

      public CalibrationPanel(GraphicsConfiguration gConfig) {
        super(gConfig);
        this.addKeyListener(this);
      }

    @Override
    public void newSizedData(float[] lesX, float[] lesY, int[] lesB, long[] lesT, int res) {
    }

    @Override
    public void newRawData(float[] lesX, float[] lesY, int[] lesB, long[] lesT, int res) {
                 for (int i = 0; i < res; i++) {
                int x = Math.round(lesX[i]);
                int y = Math.round(lesY[i]);

                if (x < xmin) {
                    xmin = x;
                }
                if (x > xmax) {
                    xmax = x;
                }
                if (y < ymin) {
                    ymin = y;
                }
                if (y > ymax) {
                    ymax = y;
                }

            }
    }

    public int getXmin() {
        return xmin;
    }

    public int getXmax() {
        return xmax;
    }

    public int getYmin() {
        return ymin;
    }

    public int getYmax() {
        return ymax;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        ws.terminate();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void setSampler(WintabSampler ws) {
        this.ws =ws;
    }
    
}
