package htools.hollermap.main;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import htools.core.input.Jwintab;
import htools.core.input.MouseSampler;
import htools.core.input.OSValidator;
import htools.core.input.Options;
import htools.core.input.WintabSampler;
import htools.hollermap.manager.TraceManager;
import htools.hollermap.manager.TraceManagerUI;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author rvlander
 */
public class HollermapFusion {

    public static void main(String argsr[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HollermapFusion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(HollermapFusion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(HollermapFusion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(HollermapFusion.class.getName()).log(Level.SEVERE, null, ex);
        }

        // On récupére la liste des écrans :
        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = gEnv.getScreenDevices();

        GraphicsConfiguration gConfig = null;
        if (devices.length > 1) {
            gConfig = devices[1].getDefaultConfiguration();
        }
        JFrame f = new InteractivePanel(gConfig);
        InteractivePanel ip = (InteractivePanel) f;

        f.setUndecorated(true);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);

        Thread samp;
        if (Options.getSamplerType().equals("wintab")) {
            
            if(OSValidator.isUnix()){
                Jwintab.open(Options.getWacomDir());
            } else if (OSValidator.isWindows()){
                Jwintab.open(f.getTitle());
            }
            
            
            System.out.println("version = " + Jwintab.getVersion());
            WintabSampler ws = new WintabSampler(ip);
            samp = new Thread(ws);
        } else {
            MouseSampler ms = new MouseSampler(ip);
            samp = new Thread(ms);
        }
        samp.setPriority(Thread.MAX_PRIORITY);
        samp.start();


        TraceManager tm = ip.getTM();
        TraceManagerUI tmu = new TraceManagerUI(tm, ip);
        tm.addListener(tmu);
        tmu.setVisible(true);

        tmu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
