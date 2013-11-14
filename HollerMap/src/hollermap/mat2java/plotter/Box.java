/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hollermap.mat2java.plotter;

import java.io.Serializable;

/**
 *
 * @author rvlander
 */
public class Box implements Serializable{

    public double xmin;
    public double xmax;
    public double ymin;
    public double ymax;

    public void merge(Box box) {
        xmax = Math.max(box.xmax,xmax);
        ymax = Math.max(box.ymax,ymax);
        xmin = Math.min(box.xmin,xmin);
        ymin = Math.min(box.ymin,ymin);
    }
    
    public void correct(double width,double height) {
        double dx = width - (xmax-xmin);
        double dy = height - (ymax-ymin);
        xmin -= dx/2;
        xmax += dx/2;
        ymin -= dy/2;
        ymax += dy/2;
        
    }
    
    public double getWidth(){
        return xmax-xmin;
    }
    
    public double getHeight(){
        return ymax-ymin;
    }

    public void translate(double d, double d0) {
        xmin += d;
        xmax += d;
        ymin += d0;
        ymax += d0;
    }
    
    public void zoomX(double fac){
        double hx = fac*(xmax-xmin);
        xmin += hx/2;
        xmax -= hx/2;
    }
    
    public void zoomY(double fac){
        double hy = fac*(ymax-ymin);
        ymin += hy/2;
        ymax -= hy/2;
    }
    
    public void zoom(double fac){
        zoomX(fac);
        zoomY(fac);        
    }

    public boolean inbox(double x,double y){
        return xmin <= x && xmax >= x && ymin <= y &&ymax >= y;
    }
    
    public void setBox(double x1,double x2,double y1,double y2){
        xmin = Math.min(x1, x2);
        xmax = Math.max(x1, x2);
        ymin = Math.min(y1, y2);
        ymax = Math.max(y1, y2);
    }
}
