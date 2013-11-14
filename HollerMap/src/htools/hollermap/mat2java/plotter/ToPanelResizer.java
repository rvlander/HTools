/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package htools.hollermap.mat2java.plotter;

import java.io.Serializable;

/**
 *
 * @author rvlander
 */
public class ToPanelResizer implements Serializable{

    public Box box;
    public double width;
    public double height;

    public int getX(double x){
        double res = x - box.xmin;
        res = res*0.9*width/(box.xmax-box.xmin);
        res+=0.05*width;
        return (int)res;
    }

    public int getY(double y){
        double res = y-box.ymin;
        res = res*0.9*height;
        res /= (box.ymax-box.ymin);
        res += 0.05*height;
        return (int)(height-res);
        //return (int)(res);
    }
    
     public double getRevX(int x){
        double res = x - 0.05*width;
        res = res*(box.xmax-box.xmin)/(0.9*width);
        res+=box.xmin;
        return res;
    }
     
    public double getRevY(int y){
       // System.out.println(height);
        double res = height-y; 
        res = res-0.05*height;
        res = res/(0.9*height);
        res *= (box.ymax-box.ymin);
        res += box.ymin;
        return res;
        //return (res);
    }

    public int getHeight() {
        return (int)height;
    }

    public int getWidth() {
        return (int)width;
    }

}
