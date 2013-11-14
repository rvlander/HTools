/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.expdo.manager;

import htools.expdo.experience.Manip;
import java.awt.Component;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author rvlander
 */
public interface TraceManagerListener {

    public void manipNext(String message,int w ,int h);
    public void manipEnds();
    public void newManip(DefaultMutableTreeNode p);
    public void selectedNodeChanged(DefaultMutableTreeNode p);
    public Component getYou();


}
