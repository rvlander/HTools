/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package htools.hollermap.mat2java.plotter;

import htools.hollermap.mat2java.StdStats;
import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author rvlander
 */
public class Plot implements Serializable{


    public double[] lesx;
    public double[] lesy;
    public double[] mask;

    private int type;
    

    private Color color1;
    private Color color2;
    
    
    public Plot(double[] lesy,double[] lesx,int type,double[] mask,Color c1,Color c2){
        this.lesx = lesx;
        this.lesy = lesy;
        this.type = type;
        this.mask = mask;
        this.color1 = c1;
        this.color2 = c2;
    }
    

    public Plot(double[] lesy,double[] lesx,int type,Color c){
        this(lesy,lesx,type,new double[lesx.length],c,null);
    }

    public Plot(double[] lesy,double[] lesx){
        this(lesy,lesx,PlotType.XYLINE,Color.BLUE);
    }

    public Box getBox(){
        Box res = new Box();
        res.xmax = StdStats.max(lesx);
        res.xmin = StdStats.min(lesx);
        res.ymax = StdStats.max(lesy);
        res.ymin = StdStats.min(lesy);
        return res;
    }

    public int getType() {
        return type;
    }

    public Color getColor1() {
        return color1;
    }
    
    public Color getColor2() {
        return color2;
    }

    public double[] getMask() {
        return mask;
    }

    
}
