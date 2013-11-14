/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.UI;

import editor.main.HandwritingEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.Timer;

/**
 *
 * @author rvlander
 */
public class Player implements ActionListener{
    
    private Timer timer;
    private long time=0;
    private long last_time;
    private HandwritingEditor he;
    
    public Player(HandwritingEditor h){
        timer = new Timer(40,this);
        this.he =h;
    }
    
    public void play(){
        time =0;
        last_time = new Date().getTime();
        timer.start();
        
    }
    
    public void stop(){
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long lt = new Date().getTime();
        time += (lt-last_time);
        last_time = lt;
        he.getRootPane().repaint();
    }

    public long getTime() {
        return time;
    }
    
    
    
}
