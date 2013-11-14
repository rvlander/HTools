/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hollermap.input;

import hollermap.fusion.InteractivePanel;
import java.awt.Container;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class MouseSampler implements Runnable, MouseListener {


    long tini = 0;
    private int delay = 30;
    int B = 0;
    int kloop = 0;
    private SamplerListener sl;
    int offx;
    int offy;
    HighSpeedSampler samp;

    public MouseSampler(SamplerListener sl) {
        this.sl = sl;
        ((Container) sl).addMouseListener(this);
        Point p = ((Container) sl).getLocationOnScreen();
        offx = p.x;
        offy = p.y;

        samp = new HighSpeedSampler();
        new Thread(samp).start();

    }

    public void run() {


        float lesX[] = new float[128];
        float lesY[] = new float[128];
        int lesB[] = new int[128];
        long lesT[] = new long[128];



        while (true) {
            long debut = System.currentTimeMillis();
            int res = samp.getPackets(lesX, lesY, lesB, lesT);

            float clesX[] = (float[]) lesX.clone();
            float clesY[] = (float[]) lesY.clone();
            int clesB[] = (int[]) lesB.clone();
            long clesT[] = (long[]) lesT.clone();



            //  System.out.println(res);
            sl.newData(clesX, clesY, clesB, clesT, res);



            long d = delay - (System.currentTimeMillis() - debut);
            if (d < 0) {
                d = 0;
            }
            //  System.out.println(delay);
            try {
                Thread.sleep(d);
            } catch (InterruptedException ex) {
                Logger.getLogger(InteractivePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        // System.out.println("pressed");
        B = 1;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        // System.out.println("released");
        B = 0;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    class HighSpeedSampler implements Runnable {

        float lesX[] = new float[128];
        float lesY[] = new float[128];
        int lesB[] = new int[128];
        long lesT[] = new long[128];
        int kloop = 0;
        private int delay = 10;

        int getPackets(float[] lX, float[] lY, int[] lB, long[] lT) {
            for (int i = 0; i < kloop; i++) {
                lX[i] = lesX[i];
                lY[i] = lesY[i];
                lB[i] = lesB[i];
                lT[i] = lesT[i];
            }
            int res = kloop;
            kloop = 0;
            return res;
        }

        public void run() {
            long last = System.currentTimeMillis();
            while (true) {


                Point mloc = MouseInfo.getPointerInfo().getLocation();

                if (mloc != null) {
                    lesX[kloop] = mloc.x - offx;
                    lesY[kloop] = ((Container) sl).getHeight() - (mloc.y - offy);
                    lesB[kloop] = B;
                    //System.out.println(B);
                    long now = System.currentTimeMillis();
                    lesT[kloop] = now - last;
                    last = now;

                    kloop++;
                    long d = Math.max(delay - (System.currentTimeMillis() - last), 0);

                    try {
                        Thread.sleep(d);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(InteractivePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }
}
