/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.core.input;

//import hollermap.fusion.InteractivePanel;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class WintabSampler implements Runnable {

    int resxmax = 53019;
    int resxmin = 0;
    int resymax = 33439;
    int resymin = 0;
    public static int screenX = 1280;
    public static int screenY = 800;
    long tini = 0;
    private int delay = 30;
    private Date debut;
    boolean goon = true;

    private SamplerListener sl;

    public WintabSampler(SamplerListener sl) {
        this.sl = sl;
        screenX = sl.getWidth();
        screenY = sl.getHeight();
        int[] temp = Options.getSensorBox();
        if (temp != null) {
            resxmin = temp[0];
            resxmax = temp[1];
            resymin = temp[2];
            resymax = temp[3];
        }
    }

    public void terminate(){
        goon = false;
    }
    
    public void run() {

        float lesX[] = new float[128];
        float lesY[] = new float[128];
        int lesB[] = new int[128];
        long lesT[] = new long[128];

        while (goon) {
            long debut = System.currentTimeMillis();
            int res = Jwintab.getPacket(lesX, lesY, lesB, lesT);

            float clesX[] = (float[]) lesX.clone();
            float clesY[] = (float[]) lesY.clone();
            int clesB[] = (int[]) lesB.clone();
            long clesT[] = (long[]) lesT.clone();

            for (int i = 0; i < res; i++) {
                clesX[i] = screenX * ((float) clesX[i] - resxmin) / (float) (resxmax - resxmin);
                clesY[i] = screenY * ((float) clesY[i] - resymin) / (float) (resymax - resymin);
            }
            //  System.out.println(res);
            sl.newSizedData(clesX, clesY, clesB, clesT, res);
            sl.newRawData(lesX, lesY, lesB, lesT, res);

            long d = delay - (System.currentTimeMillis() - debut);
            if (d < 0) {
                d = 0;
            }
            //  System.out.println(delay);
            try {
                Thread.sleep(d);
            } catch (InterruptedException ex) {
                // Logger.getLogger(InteractivePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
