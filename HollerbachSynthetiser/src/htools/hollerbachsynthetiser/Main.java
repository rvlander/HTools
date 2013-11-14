/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package htools.hollerbachsynthetiser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;
import javax.swing.JFrame;

/**
 *
 * @author rvlander
 */
public class Main implements ActionListener{

    private WritingPanel wp;
    private ParametersFrame pf;
    private JFrame af;
    private JFrame playf;
    private PlayPanel play;

    public Main(String args[]){

        //creating Writing Panel
        try {
            File userDir = new File(args[0]);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userDir));
            wp = new WritingPanel((Vector<ParametersHollerbach>)(ois.readObject()));
        }catch(Exception e){
            wp = new WritingPanel(new Vector<ParametersHollerbach>());
        }

        //creating parameter frame and adding as observer
        pf= new ParametersFrame();
        wp.addNextObserver(pf);
        pf.addStateChangedListenerToSliders(wp);
        pf.addSaveLoadButtonListener(wp);
        pf.addPlayListener(this);

        //creating abel panel
        af = new JFrame("Abelson");
        af.setSize(600,600);
        AbelsonPanel abel = new AbelsonPanel();
        af.getContentPane().add(abel);
        af.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wp.addNextObserver(abel);


        //playPanel
        playf = new JFrame("Play !");
        playf.setSize(1024,300);
        play = new PlayPanel();
        playf.getContentPane().add(play);
        playf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


        //correcting locations and visible things
        pf.setLocation(0, 500);
        af.setLocation(512, 500);
        playf.setLocation(200, 300);

        pf.setVisible(true);
        af.setVisible(true);

        
    }

    public WritingPanel getWp() {
        return wp;
    }



    public static void main(String args[]){
        JFrame f = new JFrame("HollerbachSynthetizer");
        f.setSize(1024,300);

        Main mn = new Main(args);


        f.getContentPane().add(mn.getWp());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setVisible(true);
        

    }

    public void actionPerformed(ActionEvent e) {
        this.play.setSuite(this.wp.getSuite());
        this.playf.setVisible(true);
    }

}
