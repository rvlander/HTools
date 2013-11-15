package htools.hollermap.main;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import htools.core.traces.Trace;
import htools.hollermap.analyzers.AddSTraceListener;
import htools.core.input.*;
import htools.hollermap.traces.STrace;
import htools.hollermap.manager.TraceManager;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author rvlander
 */
public class InteractivePanel extends JFrame implements KeyListener, ActionListener, WindowFocusListener, SamplerListener {

    //mode pour lancer et arreter l'enregistrement
    private final int AUTO_MODE = 0;
    private final int KEY_MODE = 1;
    private int recording_mode = AUTO_MODE;
    private boolean focused;
    TraceManager tm = new TraceManager();
    private long totaltime = 0;
    private Date fin;
    private long draw_begin;
    private long know;
    private int cpt = 0;
    private BufferedImage offScreenBuffer;
    private Vector<Date> vd = new Vector<Date>();
    private Vector<Float> X = new Vector<Float>();
    private Vector<Float> Y = new Vector<Float>();
    private Vector<Float> Z = new Vector<Float>();
    private Vector<AddSTraceListener> vastl = new Vector<AddSTraceListener>();
    private boolean traceBleu = true;
    private boolean traceRouge = true;
    private boolean play = false;
    private Timer timer;
    private boolean recording = false;
    private int Xoffset;
    private int Yoffset;
    private Image background;

    public InteractivePanel(GraphicsConfiguration gConfig) {
        super(gConfig);

        this.setBackground(Color.white);
        /*
         * this.addMouseListener(this); this.addMouseMotionListener(this);
         */
        this.addWindowFocusListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        timer = new Timer(40, this);
        timer.start();





        this.setTitle("InteractivePanel");



    }

    /*  public void start(int offX, int offY) {
     this.Xoffset = offX;
     this.Yoffset = offY;
     samp.start();
     }*/
    public Trace getTrace() {
        Trace t = new Trace();

        // System.out.println(this.Y.toString());

        t.T = InteractivePanel.DateVector2doubleArray(this.vd);
        t.X = InteractivePanel.FloatVector2doubleArray(this.X);
        t.Y = InteractivePanel.FloatVector2doubleArray(this.Y);
        t.Z = InteractivePanel.FloatVector2doubleArray(this.Z);

        return t;
    }

    public void setTM(TraceManager tm) {
        this.tm = tm;
    }

    public TraceManager getTM() {
        return tm;
    }

    public void setBackground(String file) {
        try {
            this.background = ImageIO.read(new File(file));
        } catch (IOException ex) {
            Logger.getLogger(InteractivePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static double[] FloatVector2doubleArray(Vector<Float> vi) {
        double[] res = new double[vi.size()];
        int i = 0;
        for (Float I : vi) {
            res[i] = (double) I;
            i++;
        }
        return res;
    }

    public static double[] DateVector2doubleArray(Vector<Date> vd) {
        double[] res = new double[vd.size()];
        int i = 0;
        long ti = 0;
        try {
            ti = vd.firstElement().getTime();
        } catch (Exception e) {
        }
        ;
        for (Date d : vd) {
            res[i] = (double) (vd.get(i).getTime() - ti);
            i++;
        }
        return res;
    }

    private void paintCurrent(Graphics2D g) {
        g.setColor(Color.blue);
        for (int i = 1; i < X.size(); i++) {
            if (Z.get(i) == 1) {
                g.drawLine(Math.round(X.get(i - 1)), this.getHeight() - Math.round(Y.get(i - 1)), Math.round(X.get(i)), this.getHeight() - Math.round(Y.get(i)));
            }
        }
    }

    @Override
    public void paint(Graphics g1) {
        offScreenBuffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D) offScreenBuffer.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Enable anti-aliasing and sub-pixel rendering
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        /*
         * g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
         * RenderingHints.VALUE_STROKE_PURE);
         */


        if (background != null) {
            g.drawImage(this.background, 0, 0, this);
        } else {
            paintLines(g);
        }

        g.setStroke(new BasicStroke(2));


        tm.paint(g, traceBleu, traceRouge, this.getHeight());


        paintCurrent(g);

        if (recording_mode == KEY_MODE) {
            g.drawString("Mode déclanchement de l'enregistrement : clavier (touche R)", 10, 15);
        } else {
            g.drawString("Mode déclanchement de l'enregistrement : automatique (une trace par stroke)", 10, 15);
        }

        if (recording) {
            g.drawString("Recording ...", 10, 35);
        }

        g.setStroke(new BasicStroke(4));
        if (this.focused) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.GRAY);
        }
        g.drawLine(0, 0, this.getWidth(), 0);
        g.drawLine(this.getWidth(), 0, this.getWidth(), this.getHeight());
        g.drawLine(0, this.getHeight(), this.getWidth(), this.getHeight());
        g.drawLine(0, this.getHeight(), 0, 0);


        g1.drawImage(offScreenBuffer, 0, 0, this);

    }

    private void reset() {
        vd = new Vector<Date>();
        X = new Vector<Float>();
        Y = new Vector<Float>();
        Z = new Vector<Float>();
    }

    public void keyTyped(KeyEvent e) {
        //System.out.println(e.getKeyChar() +" typed " + KeyEvent.VK_C);
        switch (e.getKeyChar()) {
            case '&':
                System.exit(0);
                break;
            case 'c':
                //  this.clearTraces();
                break;
            case 'h':
                tracehide();
                break;
            case 'p':
                play();
                break;
            case 'e':
                this.exportTraces(this);
                break;
            case 's':
                this.exportSTraces(this);
                break;
            case 'u':
                this.exportSelectedAll(this);
                break;
            case 'm':
                changeMode();
                break;
            case 'r':
                toggleRecording();
                break;
            default:
                break;
        }
    }

    public void tracehide() {
        if (this.traceRouge && this.traceBleu) {
            traceBleu = false;
        } else if (this.traceRouge && !this.traceBleu) {
            traceBleu = true;
            traceRouge = false;
        } else {
            traceRouge = true;
            traceBleu = true;
        }
        // System.out.println(traceRouge + " " +traceBleu);
        this.repaint();
    }

    public void play() {
        this.play = !this.play;
        tm.setPlay(play);
        if (this.play) {
            this.timer.start();
        } else {
            this.timer.stop();
        }
        this.repaint();
    }

    public void changeMode() {
        if (this.recording) {
            this.endRecording();
        }
        if (this.recording_mode == KEY_MODE) {
            this.recording_mode = AUTO_MODE;
        } else {
            this.recording_mode = KEY_MODE;
        }
    }

    public void toggleRecording() {
        if (this.recording_mode == KEY_MODE) {
            if (this.recording) {
                this.endRecording();
            } else {
                this.beginRecording();
            }
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    private void paintLines(Graphics2D g) {
        g.setColor(Color.GRAY);
        for (int i = 0; i < this.getHeight(); i += 70) {
            g.drawLine(0, i, this.getWidth(), i);
        }
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 50; i < this.getHeight(); i += 70) {
            g.drawLine(0, i, this.getWidth(), i);
        }
        g.setColor(Color.PINK);
        g.drawLine(100, 0, 100, this.getHeight());
        g.drawLine(101, 0, 101, this.getHeight());

    }

    private void updateTime() {
        this.know = new Date().getTime() - this.draw_begin;
        tm.setKnow(know);
        if (this.know > 6000) {
            this.know = 0;
            this.draw_begin = new Date().getTime();
        }
    }

    public void actionPerformed(ActionEvent e) {
        updateTime();
        this.repaint();
    }

    public void fireAddSTraceListeners(STrace st) {
        for (AddSTraceListener a : this.vastl) {
            a.STraceAdded(st);
        }
    }

    public void addAddSTraceListener(AddSTraceListener astl) {
        this.vastl.add(astl);
    }

    public void exportTraces(Component c) {
        String sf = JOptionPane.showInputDialog(c, "Le nom du fichier ?", "trace_enregistree_");
        String file = Options.getExportPath() + "/" + sf;
        tm.exportSelectedTrace(file);
    }

    public void exportTraceFromStrace(Component c) {
        String sf = JOptionPane.showInputDialog(c, "Le nom du fichier ?", "strace_reconstruite_");
        String file = Options.getExportPath() + "/" + sf;
        tm.exportSelectedTraceFromSTrace(file);
    }

    public void exportSTraces(Component c) {
        String sf = JOptionPane.showInputDialog(c, "Le nom du fichier ?", "strace_parametres_");
        String file = Options.getExportPath() + "/" + sf;
        tm.exportSelectedSTrace(file);
    }

    public void exportAllTraces(Component c) {
        String sf = JOptionPane.showInputDialog(c, "Préfixe des noms ?", "trace_enregistree_");
        String file = Options.getExportPath() + "/" + sf;
        tm.exportAllTrace(file);
    }

    public void exportAllTraceFromStrace(Component c) {
        String sf = JOptionPane.showInputDialog(c, "Préfixe des noms ?", "strace_reconstruite_");
        String file = Options.getExportPath() + "/" + sf;
        tm.exportAllTraceFromSTrace(file);
    }

    public void exportAllSTraces(Component c) {
        String sf = JOptionPane.showInputDialog(c, "Préfixe des noms ?", "strace_parametres_");
        String file = Options.getExportPath() + "/" + sf;
        tm.exportAllSTrace(file);
    }

    public void exportSelectedAll(Component c) {
        String sf = JOptionPane.showInputDialog(c, "Suffixe des noms ?", "");
        String file = Options.getExportPath() + "/trace_enregistree_" + sf;
        tm.exportSelectedTrace(file);
        file = Options.getExportPath() + "/strace_reconstruite_" + sf;
        tm.exportSelectedTraceFromSTrace(file);
        file = Options.getExportPath() + "/strace_parametres_" + sf;
        tm.exportSelectedSTrace(file);
    }

    public void cursorReleased() {

        Trace tra = this.getTrace();

        if (tra.T.length > 10) {
            double t0 = tra.T[0];

            for (int i = 0; i < tra.T.length; i++) {
                tra.T[i] -= t0;
            }

            if (tra.T.length > 2) {
                tm.addTrace(tra);
            }
        }


        this.reset();
        this.repaint();
        totaltime = 0;
    }

    private void endRecording() {
        recording = false;
        cursorReleased();
    }

    private void beginRecording() {
        recording = true;
    }

    public void windowGainedFocus(WindowEvent e) {
        this.focused = true;
    }

    public void windowLostFocus(WindowEvent e) {
        this.focused = false;
    }

    @Override
    public void newData(float[] lesX, float[] lesY, int[] lesB, long[] lesT, int res) {
        //System.out.println("les T : " + Arrays.toString(lesT));
        /*System.out.println(Arrays.toString(lesX));
         System.out.println(Arrays.toString(lesB));*/
        for (int i = 0; i < res; i++) {
            if (recording_mode == AUTO_MODE) {
                //  System.out.println("wtf ? "+i+ " "+lesB[i]);
                if (lesB[i] == 0) {
                    if (recording) {
                        System.out.println("end recording");
                        endRecording();
                    }
                } else {
                    if (!recording) {
                        System.out.println("begin recording");
                        beginRecording();
                    }
                }
            }
            if (recording) {
                if (lesT[i] > 0 && lesT[i] < 11) {
                    totaltime += lesT[i];
                    vd.add(new Date(totaltime));
                    X.add(lesX[i]);
                    Y.add(lesY[i]);
                    Z.add((float) lesB[i]);
                }

                repaint();

            }
        }
    }
}
