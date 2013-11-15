/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.core.tools;

import htools.core.input.Jwintab;
import htools.core.input.MouseSampler;
import htools.core.input.Options;
import htools.core.input.OSValidator;
import htools.core.input.WintabSampler;
import htools.expdo.fusion.InteractivePanel;
import java.awt.Component;
import java.awt.DisplayMode;
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
import javax.swing.JOptionPane;

/**
 *
 * @author rvlander
 */
public class HToolsConfigure implements KeyListener {

    public static void determineInputMethod() {
        String[] possibilities = {"Tablet", "Mouse"};

        String s = null;
        while (s != null) {
            s = (String) JOptionPane.showInputDialog(
                    null,
                    "Choose input method",
                    "Input Method",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    // les possibilités 
                    possibilities,
                    possibilities[1]);// valeur initiale
        }
        if(s.equals("Mouse")){
            Options.setSamplerType("mouse");
        }else{
            Options.setSamplerType("wintab");
            if(OSValidator.isUnix()){
                String inputValue = JOptionPane.showInputDialog("Type tablet dir please ..");
                Options.setWacomDir(inputValue);
            }
        }
    }

    public static void configureSensorBox(){
        // On récupére la liste des écrans :
        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = gEnv.getScreenDevices();

        int dev =0;
        
        GraphicsConfiguration gConfig = null;
        if (devices.length > 1) {
            dev =1;
            gConfig = devices[1].getDefaultConfiguration();
        }
        
        JFrame f = new InteractivePanel(gConfig);

        f.setUndecorated(true);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
      /*  Jwintab.open(f.getTitle());
        ((InteractivePanel) f).start(0, 0);*/
        
        
          Thread samp;
        if (Options.getSamplerType().equals("wintab")) {
            if(OSValidator.isUnix()){
                Jwintab.open(Options.getWacomDir());
            } else if (OSValidator.isWindows()){
                Jwintab.open(f.getTitle());
            }
            System.out.println("version = " + Jwintab.getVersion());
            WintabSampler ws = new WintabSampler((InteractivePanel)f);
           // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            
            DisplayMode dm = devices[dev].getDisplayMode();
            //WintabSampler.screenX = screenSize.width;
            //WintabSampler.screenY = screenSize.height;
            
            System.out.println(dm.getWidth() +" " + dm.getHeight());
            WintabSampler.screenX =dm.getWidth();
            WintabSampler.screenY = dm.getHeight();
            samp = new Thread(ws);
        } else {
            MouseSampler ms = new MouseSampler((InteractivePanel)f);
            samp = new Thread(ms);
        }
        samp.setPriority(Thread.MAX_PRIORITY);
        samp.start();
        
        
    }
    
    public static void configureExportPath(Component c) {
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

    public static void configurePDFReportPath(Component c) {
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
    
    public static void launchConfigSuite() {
        determineInputMethod();
        
        
        
        configureExportPath(null);
        configurePDFReportPath(null);
    }
}
