/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.expdo.fusion;

import htools.core.input.Jwintab;
import htools.expdo.input.Options;
import htools.core.input.OSValidator;
import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author rvlander
 */
public class HollerMapConfigure implements KeyListener {

    private long delay = 50;
    boolean con = true;

    public void configureExportPath(Component c) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        fc.setCurrentDirectory(new File(Options.getExportPath()));

        int returnVal = fc.showDialog(c, "ExportPath");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would ope&n the file.
            System.out.println("Opening: " + file.getPath() + ".");
            Options.setExportPath(file.getPath());
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }

    public void configurePDFReportPath(Component c) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        fc.setCurrentDirectory(new File(Options.getPDFReportPath()));

        int returnVal = fc.showDialog(c, "ReportPath");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would ope&n the file.
            System.out.println("Opening: " + file.getPath() + ".");
            Options.setPDFReportPath(file.getPath() + "\\report.pdf");
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }

    public void calibrate() {
        float lesX[] = new float[128];
        float lesY[] = new float[128];
        int lesB[] = new int[128];
        long lesT[] = new long[128];

        int xmin = Integer.MAX_VALUE;
        int xmax = Integer.MIN_VALUE;
        int ymin = Integer.MAX_VALUE;
        int ymax = Integer.MIN_VALUE;



        while (con) {
            long debut = System.currentTimeMillis();
            int res = Jwintab.getPacket(lesX, lesY, lesB, lesT);

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



            try {
                long d = delay - (System.currentTimeMillis() - debut);
                if (d < 0) {
                    d = 0;
                }
                Thread.sleep(d);
            } catch (InterruptedException ex) {
                Logger.getLogger(InteractivePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("while : " + con);
        }

        Options.setSensorBox(xmin, xmax, ymin, ymax);

    }

    public void keyTyped(KeyEvent e) {
        System.out.println("typed");
        con = false;
        System.out.println("keyTyped :" + con);
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public static void main(String args[]) {
        // On récupére la liste des écrans :
        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = gEnv.getScreenDevices();

        // On récupère la configuration du second écran (s'il existe) :r
        GraphicsConfiguration gConfig = null;
        if (devices.length > 1) {
            gConfig = devices[1].getDefaultConfiguration();
        }

        JFrame f = new JFrame(gConfig);
        f.setUndecorated(true);

        f.setExtendedState(JFrame.MAXIMIZED_BOTH);

        f.setVisible(true);

        HollerMapConfigure hmc = new HollerMapConfigure();
        f.addKeyListener(hmc);
        f.setFocusable(true);
        Jwintab.open("/dev/input/event5");
        /*if (OSValidator.isUnix()) {
            Jwintab.open(Options.getWacomDir());
        } else if (OSValidator.isWindows()) {
            Jwintab.open(f.getTitle());
        }*/

        hmc.calibrate();
        //Jwintab.close();

        System.exit(0);
    }
}