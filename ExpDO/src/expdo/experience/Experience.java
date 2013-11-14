/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expdo.experience;

/**
 *
 * @author rvlander
 */
public class Experience {
    private String consigne;
    private String name;
    private boolean autoNext;
    
    private int width;
    private int height;
    
    public Experience(String consigne,String name,boolean auto){
        this.consigne = consigne;
        this.name = name;
        this.autoNext = auto;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
            
    
    public String getConsigne() {
        return consigne;
    }

    public String getName() {
        return name;
    }
    
    public boolean getAutoNext() {
        return this.autoNext;
    }
    
    public String toString(){
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    
}
