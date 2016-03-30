/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package htools.hollermap.algorithms;

import Jama.Matrix;
import htools.hollermap.mat2java.Functions;
import java.util.Arrays;

/**
 *
 * @author rvlander
 */
public class Crossing {

    //level crossing
    public static CrossingReturn crossing(double[] argS,double[] t,double level){
        CrossingReturn cr = new CrossingReturn();

        int n = argS.length;

        Matrix S = new Matrix(argS,argS.length);
        S.plusEquals(new Matrix(argS.length,1,-level));

        //first look exact zeros
        int[] ind0 = Functions.findEquals(S.getColumnPackedCopy(), 0);
        //System.out.println("ind 0 : "+Functions.affiche(ind0));

        //then look for zero crossings between data points
        Matrix S1 = S.getMatrix(Functions.createIndex(0, n-2), Functions.trivialIndex())
                .arrayTimes(S.getMatrix(Functions.createIndex(1, n-1), Functions.trivialIndex()));

        int[] ind1 = Functions.findLessers(S1.getColumnPackedCopy(), 0);
        //System.out.println("ind 1 : "+Functions.affiche(ind1));

        //bring exact zeros and "in-between" zeros together and sort
        int[] ind = Functions.concat(ind0, ind1);
        Arrays.sort(ind);

        //System.out.println(Functions.affiche(ind)+" /"+t.length+" "+n);

        //pick associated values
        double[] t0 = new Matrix(t,t.length).getMatrix(ind,Functions.trivialIndex()).getColumnPackedCopy();
        //System.out.println("Corssing t0 : "+Functions.affiche(t0));
        double[] s0 = S.getMatrix(ind,Functions.trivialIndex()).getColumnPackedCopy();

        //interpolation
        for(int i=0;i<t0.length;i++){
            //todo eps et S argS si level != 0 echec
            if( Math.abs(argS[ind[i]]) > 0.01){
                double NUM = (t[ind[i]+1] - t[ind[i]]);
                double DEN = (argS[ind[i]+1] - argS[ind[i]]);
                double DELTA =  NUM / DEN;
                t0[i] = t0[i] - argS[ind[i]] * DELTA;
                // I'm a bad person, so I simply set the value to zero
                // instead of calculating the perfect number ;)
                s0[i] = 0;
            }
        }

        //we pack results
        cr.ind = ind;
        cr.s0 = s0;
        cr.t0 = t0;
        return cr;
    }

    //zero crossing
    public static CrossingReturn crossing(double[] S,double[] t){
        return crossing(S,t,0);
    }

    public static void main(String args[]){
        double[] tab= {2.,1.,0.5,-0.2,0,-4,-7,2,0,-5,-2,-5,-8,4};
        double[] t= {0,1,2,3,4,5,6,7,8,9,10,11,12,13};
        CrossingReturn cr = Crossing.crossing(tab,t);
        System.out.println("Test de crossing : \nEntrées ...");
        System.out.println(Functions.affiche(tab)+"\n"+Functions.affiche(t));
        System.out.println("Sorties ...\nind : "+Functions.affiche(cr.ind)+"\nt0 : "+Functions.affiche(cr.t0)
                            + "\ns0 : "+Functions.affiche(cr.s0));

    }
}
