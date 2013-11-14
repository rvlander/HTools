package htools.expdo.fusion;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import htools.core.input.Jwintab;
import htools.expdo.input.Options;
import htools.core.traces.Trace;
import htools.expdo.manager.TraceManager;
import htools.core.input.SamplerListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.Calendar;
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
    private final int SEMI_KEY_MODE = 2;
    private final int NUM_RECORDING_MODE = 3;
    private int recording_mode = AUTO_MODE;
    private boolean focused;
    TraceManager tm = new TraceManager();
    private long totaltime = 0;
    private Date debut;
    private Date fin;
    private long draw_begin;
    private long know;
    private int cpt = 0;
    private BufferedImage offScreenBuffer;
    private Vector<Date> vd = new Vector<Date>();
    private Vector<Float> X = new Vector<Float>();
    private Vector<Float> Y = new Vector<Float>();
    private Vector<Float> Z = new Vector<Float>();
    private boolean traceBleu = true;
    private boolean traceRouge = true;
    private boolean play = false;
    private Timer timer;
    private boolean recording = false;
    private int delay = 50;
    private Thread samp;
    private int Xoffset;
    private int Yoffset;
    private Image background;
    private int constrained_width;
    private int constrained_height;
    private Point cursor;
    private boolean cursorCond = true;

    public InteractivePanel(GraphicsConfiguration gConfig) {
        super(gConfig);

        cursor = new Point(0, 0);

        this.setBackground(Color.white);
        /*
         * this.addMouseListener(this); this.addMouseMotionListener(this);
         */
        this.addWindowFocusListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        timer = new Timer(40, this);
        timer.start();

        //     System.out.println("version = " + Jwintab.getVersion());



        this.setTitle("InteractivePanel");



        //       samp = new Thread(new Sampler());
        //      samp.setPriority(Thread.MAX_PRIORITY);



    }

    public void start(int offX, int offY) {
        this.Xoffset = offX;
        this.Yoffset = offY;
        samp.start();
    }

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
                // System.out.println(X.get(i)+" "+Y.get(i));
                g.drawLine(Math.round(X.get(i - 1)), this.getHeight() - Math.round(Y.get(i - 1)), Math.round(X.get(i)), this.getHeight() - Math.round(Y.get(i)));
            }
        }
    }

    public void paintTrace(Graphics2D g, Trace tra) {
        g.setColor(Color.blue);
        for (int i = 1; i < tra.T.length; i++) {
            if (!this.play || tra.T[i] < this.know) {
                if (tra.Z[i] == 1) {
                    g.setColor(Color.blue);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.drawLine((int) tra.X[i - 1], this.getHeight() - (int) tra.Y[i - 1], (int) tra.X[i], this.getHeight() - (int) tra.Y[i]);
            } else {
                break;
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


        updateTime();

        if (background != null) {
            g.drawImage(this.background, 0, 0, this);
        } else {
            //   paintLines(g);
        }

        g.setStroke(new BasicStroke(2));


        for (Trace t : tm.getVts()) {
            paintTrace(g, t);
        }


        paintCurrent(g);

        if (recording_mode == KEY_MODE) {
            g.drawString("Mode déclanchement de l'enregistrement : clavier (touche R)", 10, 15);
        } else if (recording_mode == SEMI_KEY_MODE) {
            g.drawString("Mode déclanchement de l'enregistrement : semi-auto", 10, 15);
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

        paintConstrains(g);

        if (cursorCond) {
            //paint crusor
            g.setColor(Color.DARK_GRAY);
            int cursx = cursor.x;
            int cursy = this.getHeight() - cursor.y;
            // System.out.println(cursor.x +" "+ cursor.y);
            g.drawLine(cursx + 5, cursy, cursx + 15, cursy);
            g.drawLine(cursx - 5, cursy, cursx - 15, cursy);
            g.drawLine(cursx, cursy + 5, cursx, cursy + 15);
            g.drawLine(cursx, cursy - 5, cursx, cursy - 15);
        }
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
            //  case 'e':
            //    this.exportTraces(this);
            //  break;
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
        //tm.setPlay(play);
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
        this.recording_mode = (this.recording_mode + 1) % NUM_RECORDING_MODE;
        /* if (this.recording_mode == KEY_MODE) {
         this.recording_mode = AUTO_MODE;
         } else {
         this.recording_mode = KEY_MODE;
         }*/
    }

    public void toggleRecording() {
        if (this.recording_mode == KEY_MODE) {
            if (this.recording) {
                this.endRecording();
            } else {
                this.beginRecording();
            }
        } else if (this.recording_mode == SEMI_KEY_MODE) {
            if (this.recording) {
                this.endRecording();
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
        this.repaint();
    }

    /* public void exportTraces(Component c) {
     String sf = JOptionPane.showInputDialog(c, "Le nom du fichier ?", "trace_enregistree_");
     String file = Options.getExportPath() + "/" + sf;
     tm.exportSelectedTrace(file);
     }

 
     public void exportAllTraces(Component c) {
     String sf = JOptionPane.showInputDialog(c, "Préfixe des noms ?", "trace_enregistree_");
     String file = Options.getExportPath() + "/" + sf;
     tm.exportAllTrace(file);
     }*/
    public void cursorReleased() {
        Trace tra = this.getTrace();
        double t0 = tra.T[0];
        /*
         * for (int i = 0; i < tra.T.length; i++) { tra.T[i] -= t0; }
         */
        if (constrained_width != 0) {
            int tr = (this.getWidth() - constrained_width) / 2;
            for (int i = 0; i < tra.X.length; i++) {
                tra.X[i] -= tr;
            }
        }

        if (constrained_height != 0) {
            int tr = (this.getHeight() - constrained_height) / 2;
            for (int i = 0; i < tra.Y.length; i++) {
                tra.Y[i] = tra.Y[i] - tr;
            }
        }
        if (tra.T.length > 2) {
            tm.addTrace(tra);
        }

        this.repaint();
        this.reset();
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

    public void constrain(int w, int h) {
        this.constrained_width = w;
        this.constrained_height = h;
    }

    private void paintConstrains(Graphics2D g) {
        int w, h;
        w = this.getWidth();
        h = this.getHeight();
        g.setColor(new Color(20, 20, 20, 200));
        if (constrained_width != 0) {
            int delta = (w - constrained_width) / 2;
            g.fillRect(0, 0, delta, h);
            g.fillRect(w - delta, 0, delta, h);

        }

        if (constrained_height != 0) {
            int delta = (h - constrained_height) / 2;
            g.fillRect(0, 0, w, delta);
            g.fillRect(0, h - delta, w, delta);
        }
    }
    private long time;

    @Override
    public void newData(float[] lesX, float[] lesY, int[] lesB, long[] lesT, int res) {
        //System.out.println("les T : " + Arrays.toString(lesT));
        /*System.out.println(Arrays.toString(lesX));
         System.out.println(Arrays.toString(lesB));*/
        Point locationOnScreen = getLocationOnScreen();


        if (res > 0) {
            cursor.x = (int) lesX[res - 1];// - locationOnScreen.x;
            cursor.y = (int) lesY[res - 1];// - locationOnScreen.y;
            if (!recording & new Date().getTime() - time > 510) {
                cursorCond = true;
            }
        }

        for (int i = 0; i < res; i++) {
            if (recording_mode == AUTO_MODE) {
                //  System.out.println("wtf ? "+i+ " "+lesB[i]);
                if (lesB[i] == 0) {
                    if (recording) {

                        System.out.println("end recording");
                        endRecording();
                        time = new Date().getTime();
                    }
                } else {
                    if (!recording) {
                        cursorCond = false;
                        System.out.println("begin recording");
                        beginRecording();
                    }
                }
            } else if (recording_mode == SEMI_KEY_MODE) {
                //  System.out.println("wtf ? "+i+ " "+lesB[i]);
                if (lesB[i] != 0) {
                    if (!recording) {
                        cursorCond = false;
                        System.out.println("begin recording");
                        beginRecording();
                    }
                }
            }
            if (recording) {
                if (lesT[i] > 0 && lesT[i] < 11) {
                    totaltime += lesT[i];
                    vd.add(new Date(totaltime));
                    X.add(lesX[i]);// - locationOnScreen.x);
                    Y.add(lesY[i]);// - locationOnScreen.y);
                    Z.add((float) lesB[i]);
                }



            }
        }
        repaint();
    }

    public RenderedImage getImage() {
        BufferedImage myImage =
                new BufferedImage(this.getWidth(), this.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = myImage.createGraphics();
        this.paint(g2);
        return myImage.getSubimage((this.getWidth() - this.constrained_width) / 2, (this.getHeight() - this.constrained_height) / 2, this.constrained_width, this.constrained_height);
    }
}
