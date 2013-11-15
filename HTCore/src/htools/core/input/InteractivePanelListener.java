/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package htools.core.input;

import htools.core.traces.Trace;
import java.awt.Graphics2D;

/**
 *
 * @author rvlander
 */
public interface InteractivePanelListener {

    public void paint(Graphics2D g,int height);

    public void addTrace(Trace tra);
    
}
