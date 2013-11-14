/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hollermap.mat2java.plotter;

import java.awt.Color;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author rvlander
 */
public class Plotter implements Serializable{

   
    
    Vector<Figure> lesfigures;
    Figure current;
    boolean hold_on;

    public Plotter(){
        lesfigures = new Vector<Figure>();
        hold_on = false;
    }

    public void figure(){
        current = new Figure();
       // System.out.println(current);
        this.lesfigures.add(current);
       // System.out.println(this.lesfigures.get(0));
        hold_on = true;
    }
    
    public void figure(String name,double d1, double d2, String s1, String s2,String comment){
        current = new Figure(name,d1,d2,s1,s2,comment);
        this.lesfigures.add(current);
        hold_on = true;
    }

    public void figure(int i){
        current = this.lesfigures.get(i);
    }

    public void hold_on(){
        hold_on = true;
    }

    public void plot(double[] lesy,double[] lesx){
        if(!hold_on) figure();
        current.addPlot(new Plot(lesy,lesx));
        hold_on = false;
    }

    public void plot(double[] lesy,double[] lesx,int type, Color c){
        if(!hold_on) figure();
        current.addPlot(new Plot(lesy,lesx,type,c));
        hold_on = false;
    }
    
     public void plot(double[] lesy,double[] lesx,int type,double[] mask, Color c1,Color c2){
        if(!hold_on) figure();
        current.addPlot(new Plot(lesy,lesx,type,mask,c1,c2));
        hold_on = false;
    }
    
    public void addValue(String str,double val){
        current.addValue(str,val);
    }

    
    
    public Vector<Figure> getLesfigures() {
        return lesfigures;
    }

   
}
