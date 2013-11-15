/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.core.tools;

import htools.core.input.CalibrationPanel;
import htools.core.input.InteractivePanel;
import htools.core.input.Jwintab;
import htools.core.input.MouseSampler;
import htools.core.input.Options;
import htools.core.input.OSValidator;
import htools.core.input.SamplerListener;
import htools.core.input.WintabSampler;
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
public class HToolsConfigure {

    public static void determineInputMethod() {
        String[] possibilities = {"Tablet", "Mouse"};

        String s = null;
        while (s == null) {
            s = (String) JOptionPane.showInputDialog(
                    null,
                    "Choose input method",
                    "Input Method",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    // les possibilités 
                    possibilities,
                    possibilities[1]);// valeur initiale
        }
        if (s.equals("Mouse")) {
            Options.setSamplerType("mouse");
        } else {
            Options.setSamplerType("wintab");
            if (OSValidator.isUnix()) {
                String inputValue = JOptionPane.showInputDialog("Type tablet dir please ..");
                Options.setWacomDir(inputValue);
            }
        }
    }

    public static void configureSensorBox(){
        // On récupére la liste des écrans :
        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = gEnv.getScreenDevices();

        int dev = 0;

        GraphicsConfiguration gConfig = devices[0].getDefaultConfiguration();
        if (devices.length > 1) {
            dev = 1;
            gConfig = devices[1].getDefaultConfiguration();
        }

        CalibrationPanel f = new CalibrationPanel(gConfig);

        f.setUndecorated(true);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);

        Thread samp;

        if (OSValidator.isUnix()) {
            Jwintab.open(Options.getWacomDir());
        } else if (OSValidator.isWindows()) {
            Jwintab.open(f.getTitle());
        }
        System.out.println("version = " + Jwintab.getVersion());
        WintabSampler ws = new WintabSampler(f);
        f.setSampler(ws);

        DisplayMode dm = devices[dev].getDisplayMode();

        System.out.println(dm.getWidth() + " " + dm.getHeight());
        WintabSampler.screenX = dm.getWidth();
        WintabSampler.screenY = dm.getHeight();
        samp = new Thread(ws);

        samp.setPriority(Thread.MAX_PRIORITY);
        samp.start();
        try {
            samp.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(HToolsConfigure.class.getName()).log(Level.SEVERE, null, ex);
        }
        f.dispose();
        Options.setSensorBox(f.getXmin(), f.getXmax(), f.getYmin(), f.getYmax());
    }
    
    


    public static void configureExportPath(Component c) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        String s = Options.getExportPath();
        
        if(s!=null){        
            fc.setCurrentDirectory(new File(s));
        }
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
    public static void configureManipPath(Component c) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        String s = Options.getManipPath();
        
        if(s!=null){
        fc.setCurrentDirectory(new File(s));
        }

        int returnVal = fc.showDialog(c, "ManipPath");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would ope&n the file.
            System.out.println("Opening: " + file.getPath() + ".");
            Options.setManipPath(file.getPath());
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }

    public static void configurePDFReportPath(Component c) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        String s = Options.getPDFReportPath();
        
        if(s!=null){
        fc.setCurrentDirectory(new File(s));
        }

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

    public static void launchConfigSuite() {
        determineInputMethod();
        if(Options.getSamplerType().equals("wintab")){
            configureSensorBox();
        }
        configureExportPath(null);
        configureManipPath(null);
        configurePDFReportPath(null);
    }

  
}
