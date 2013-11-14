/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.core.tools;


import htools.core.input.Jwintab;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class TestSampler {

    public static void main(String args[]) {

        Jwintab.open(null);
        float[] lesX = new float[128];
        float[] lesY = new float[128];
        long[] lesT = new long[128];
        int[] lesB = new int[128];

        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestSampler.class.getName()).log(Level.SEVERE, null, ex);
            }
            int res = Jwintab.getPacket(lesX, lesY, lesB, lesT);
            System.out.println("N="+res);
            int i = 0;
            for (i = 0; i < res; i++) {
                System.out.println(lesX[i]+"," +lesY[i] +"," +lesB[i] +"," +lesT[i]);
            }
        }
    }

}
