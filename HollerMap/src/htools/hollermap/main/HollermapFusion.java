package htools.hollermap.main;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import htools.core.input.InteractivePanel;
import htools.core.input.Jwintab;
import htools.core.input.MouseSampler;
import htools.core.input.OSValidator;
import htools.core.input.Options;
import htools.core.input.WintabSampler;
import htools.hollermap.manager.TraceManager;
import htools.hollermap.manager.TraceManagerUI;
import java.awt.DisplayMode;
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

        Options.init();
        
        // On récupére la liste des écrans :
        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = gEnv.getScreenDevices();

        int dev =0;
        GraphicsConfiguration gConfig = null;
        if (devices.length > 1) {
            dev=1;
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


        TraceManager tm = new TraceManager();
        ((InteractivePanel)f).setInteractivePanelListener(tm);
        TraceManagerUI tmu = new TraceManagerUI(tm, (InteractivePanel)f);
        tm.addListener(tmu);
        tmu.setVisible(true);

        tmu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
