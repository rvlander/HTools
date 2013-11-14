/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expdo.experience;

import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author rvlander
 */
public class Manip {
    private Vector<Experience> vectorExperiences;
    
    private String name;
    private int iter;
    private boolean saveImage;
    
    
    public Manip(String name,int iter,boolean save_im){
        this.name = name;
        this.iter=iter;
        this.saveImage = save_im;
        this.vectorExperiences = new Vector<Experience>();
      
    }

    public int getIter() {
        return iter;
    }
    
    /*public void addExperience(Experience exp){
        this.vectorExperiences.add(exp);
    }

    public Vector<Experience> getVectorExperiences() {
        return vectorExperiences;
    }*/

   /* public DefaultMutableTreeNode getTree(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(this);
        
        for(Experience e:vectorExperiences){
            DefaultMutableTreeNode l = new DefaultMutableTreeNode(e);
            root.add(l);
        }
        
        
        return root;
    }*/

    public String getName() {
        return name;
    }
    
    
    
    
    @Override
    public String toString(){
        return name;
    }

    public boolean isSaveImage() {
        return saveImage;
    }
    
    
    
}
