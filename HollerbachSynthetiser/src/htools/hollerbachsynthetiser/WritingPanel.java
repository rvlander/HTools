/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package htools.hollerbachsynthetiser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author rvlander
 */
public class WritingPanel extends JPanel implements ChangeListener,
        ActionListener, MouseListener{

    private BufferedImage offScreenBuffer;

    private Vector<NextObserver> no;

    private int H = 300;
    private int W = 1024;

    private double startx = 20;
    private double starty = 20;

    private boolean active = false;

    private int the_active = 0; //1 x , 2y

    private Vector<ParametersHollerbach> suite;
    private ParametersHollerbach next;

    private Timer cligno;

    public WritingPanel(Vector<ParametersHollerbach> suite){
        super();
        this.suite = suite;
        cligno = new Timer(300,this);
        no = new Vector<NextObserver>();
        cligno.start();
        try{
            this.next = this.suite.lastElement().copie();
        }catch(Exception e){
            next = new ParametersHollerbach(100.,100.,Math.PI,Math.PI,10,10.,20.,10.);
        }
        this.setPreferredSize(new Dimension(W,H));
        this.addMouseListener(this);
        this.repaint();

    }

  /*  @Override
    public void update(Graphics g) {
        Graphics gr;
	offScreenBuffer = new BufferedImage(W,H,BufferedImage.TYPE_INT_BGR);
	gr = offScreenBuffer.getGraphics();
	g.drawImage(offScreenBuffer, 0, 0, this);
    }*/

    @Override
    public void update(Graphics g)
     {
          paint(g);
     }

    @Override
    public void paint(Graphics g1) {
        offScreenBuffer = new BufferedImage(W,H,BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D) offScreenBuffer.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, W, H);
        

        double offx,offy,x,y,nx,ny,t=0;
        x = 20;
	y = 0;

        //on commence par tracer les anciens parametres
        g.setColor(Color.black);
        for (ParametersHollerbach ph : suite){
            offx = x  +ph.a*Math.cos(ph.wx*t+ph.phix)/ph.wx - ph.c*t;
            offy = y  +ph.b*Math.cos(ph.wy*t+ph.phiy)/ph.wy;
            while(t<ph.ftime){
                t+=0.01;
                nx = -ph.a*Math.cos(ph.wx*t+ph.phix)/ph.wx + ph.c*t + offx;
		ny = -ph.b*Math.cos(ph.wy*t+ph.phiy)/ph.wy +offy;
                if(!ph.up || ((int)(t*100))%4==0 ){
                    g.draw(new Line2D.Double(x*2,-y*2+H/2,nx*2,-ny*2+H/2));
                }
                x = nx;
                y = ny;
            }
        }

        //puis les nouveaux en clignotant
        double dy = 1000;
        double dx = 1000;
        if(active) {
            g.setColor(Color.red);
            double oldt = t;
            offx = x  +next.a*Math.cos(next.wx*t+next.phix)/next.wx - next.c*t;
            offy = y  +next.b*Math.cos(next.wy*t+next.phiy)/next.wy;
            //while((Math.abs(dx)>0.1 && Math.abs(dy)>0.1) || Math.abs(oldt-t)<0.1){
            while(( Math.abs(dy)>0.1) || Math.abs(oldt-t)<0.1){
                t+=0.01;
                nx = -next.a*Math.cos(next.wx*t+next.phix)/next.wx + next.c*t+offx;
		ny = -next.b*Math.cos(next.wy*t+next.phiy)/next.wy+offy;
                if(!next.up || ((int)(t*100))%4==0 ){
                    g.draw(new Line2D.Double(x*2,-y*2+H/2,nx*2,-ny*2+H/2));
                }
                x = nx;
                y = ny;
                dy = Math.sin(next.wy*t+next.phiy);
                dx = Math.sin(next.wx*t+next.phix);
            }
            //if(Math.abs(dx)<=0.1 || Math.abs(dy)<=0.1){
            if(Math.abs(dy)<=0.1){
                System.out.println(Math.abs(dx)+" "+Math.abs(dy));
                if (Math.abs(dx)<Math.abs(dy))
                    this.the_active =1;
                else if (Math.abs(dx)==Math.abs(dy))
                    this.the_active =0;
                else
                    this.the_active =2;
            }
            next.ftime = t;
        }

        g1.drawImage(offScreenBuffer, 0, 0, this);

    }

    public void stateChanged(ChangeEvent ce) {
        JSlider js = (JSlider)ce.getSource();
       // System.out.println(js.getName() + "fired.");
        if(js.getName().equals("a")) next.a = WritingPanel.scale(js.getValue(),0.,200.);
        else if(js.getName().equals("b")) next.b = WritingPanel.scale(js.getValue(),0.,200.);
        else if(js.getName().equals("phix")) next.phix = WritingPanel.scale(js.getValue(),0,2*Math.PI);
        else if(js.getName().equals("phiy")) next.phiy = WritingPanel.scale(js.getValue(),0,2*Math.PI);
        else if(js.getName().equals("wx")) next.wx = WritingPanel.scale(js.getValue(),-20,20);
        else if(js.getName().equals("wy")) next.wy = WritingPanel.scale(js.getValue(),-20,20);
        for(NextObserver to:no){
            to.updated(next);
        }
        this.repaint();
    }

    public static double scale(int val, double min, double max){
        return ((double)val)*(max-min)/100 + min;
    }

    public static double uscale(double val, double min, double max){
        return ((double)val-min)*100/(max-min);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() instanceof JButton){
            JButton jb = (JButton)ae.getSource();
            if(jb.getName().equals("saveButton")){
                try {
                    String filedir = JOptionPane.showInputDialog("Entrez le nom du fichier à sauver");
                    // System.out.println(filedir);
                    File userDir = new File(filedir);
                    ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(userDir));
                    ois.writeObject(this.suite);
                    ois.flush();
                    ois.close();
                } catch (IOException ex) {
                    System.err.println("Erreur lors de la suvegarde du fichier.");
                }
            }else if(jb.getName().equals("clearButton")){
                this.suite = new Vector<ParametersHollerbach>();
            }else{
                try{
                    String filedir = JOptionPane.showInputDialog("Entrez le nom du fichier à sauver");
                    // System.out.println(filedir);
                    File userDir = new File(filedir);
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userDir));
                    this.suite = (Vector<ParametersHollerbach>)(ois.readObject());
                    this.next = this.suite.lastElement().copie();
                }catch (Exception ex) {
                    System.err.println("Erreur lors de la lecture du fichier.");
                }
            }

        }else{
            active = !active;
            this.repaint();
        }
    }

    public void mouseClicked(MouseEvent me) {
        if(me.getButton() == MouseEvent.BUTTON1){
            suite.add(next.copie());
            System.out.println(next + " added.");
            for(NextObserver to:no){
              to.next(the_active);
             }
        }else if(me.getButton() == MouseEvent.BUTTON2){
            next.up = !next.up;
        }else{
            next = suite.get(suite.size()-1).copie();
            suite.removeElementAt(suite.size()-1);
            System.out.println(next + " removed.");
        }
        this.repaint();
    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    

    public void addNextObserver(NextObserver o){
        no.add(o);
        for(NextObserver to:no){
            to.updated(next);
        }
    }

    public Vector<ParametersHollerbach> getSuite() {
        return suite;
    }

    

}
