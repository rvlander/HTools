/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package htools.core.mat2java;

/**
 *
 * @author rvlander
 */
public class Signal {

    public static double[] filtfilt2(double[] data){
        double[] res = new double[data.length];
        for(int i =0;i<data.length-1;i++)
            res[i] = (data[i] + data[i+1])/2;
        for(int i= data.length-1;i>0;i--)
            res[i] = (res[i] + res[i-1])/2;

        res[0] =0;
        res[res.length-1] =0;
        return res;
    }

    public static double[] filtfilt6(double[] data){
        double[] res = new double[data.length];
        res[1] = (res[0]+res[1]+res[2])/3;
        res[2] = (res[1]+res[2]+res[3])/3;
        for(int i =3;i<data.length-2;i++)
            res[i] = (data[i] + data[i+1]+data[i+2] + data[i-1]+data[i-2] + data[i-3])/6;
        res[data.length-2] = res[data.length-1]+res[data.length-2]+res[data.length-3]/3;
        res[data.length-3] = res[data.length-4]+res[data.length-3]+res[data.length-2]/3;
        for(int i= data.length-4;i>1;i--)
            res[i] = (res[i] + res[i-1]+res[i-2] + res[i+1]+res[i+2] + res[i+3])/6;

        res[1] = (res[0]+res[1]+res[2])/3;
        res[0] =0;
        res[res.length-1] =0;
        return res;
    }

}
