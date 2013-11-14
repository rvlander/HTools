/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hollerbachsynthetiser;

import java.io.Serializable;

/**
 *
 * @author rvlander
 */
public class ParametersHollerbach implements Serializable{
    //parametres de hollerbach
    double a;
    double b;
    double phix;
    double phiy;
    double wx;
    double wy;
    double c;
    //up ?
    boolean up;
    //temps de fin.
    double ftime;

    public ParametersHollerbach(double a, double b, double phix,
            double phiy, double wx, double wy,double c,double ftime){
        this.a = a;
        this.b = b;
        this.phix = phix;
        this.phiy = phiy;
        this.wx = wx;
        this.wy = wy;
        this.c = c;
        this.ftime = ftime;
        this.up = false;
    }
    public ParametersHollerbach(double a, double b, double phix,
            double phiy, double wx, double wy,double c,boolean up, double ftime){
        this.a = a;
        this.b = b;
        this.phix = phix;
        this.phiy = phiy;
        this.wx = wx;
        this.wy = wy;
        this.c = c;
        this.ftime = ftime;
        this.up = up;
    }


    @Override
    public String toString(){
        String s = a + " " + b + " " +phix + " "+phiy +" "+wx +" "+wy +" "+c+" "+ up +" "+ftime ;
        return s;
    }

    public ParametersHollerbach copie(){
        return new ParametersHollerbach(a,b,phix,phiy,wx,wy,c,up,ftime);
    }

}
