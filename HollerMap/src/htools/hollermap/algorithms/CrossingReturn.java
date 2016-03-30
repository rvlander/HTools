/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package htools.hollermap.algorithms;

import htools.hollermap.mat2java.Functions;
import java.io.Serializable;

/**
 *
 * @author rvlander
 */
public class CrossingReturn implements Serializable{
    static final long serialVersionUID = 1515L;
    
    
    public int ind[];
    public double dind[];
    public double t0[];
    public double s0[];

    public void filter(){
        //on commence par trouver les zero trop rapproché
        int[] indexes = new int[ind.length];
        int k=1;
        indexes[0] = 0;
        for(int i=1;i<ind.length;i++){
            //System.out.println(t0[i]-t0[indexes[k-1]]);
            if((t0[i]-t0[indexes[k-1]])>40){
                indexes[k] = i;
                k++;
                //System.out.println(i);
            }
        }
        //nouveau tableau de bonne taille
        int[] lesi = new int[k];
        
        for(int i=0;i<k;i++)
            lesi[i] = indexes[i];

        //System.out.println("les i : " + Functions.affiche(lesi));

        ind = Functions.extract(lesi,ind);
        t0 = Functions.extract(lesi,t0);
        s0 = Functions.extract(lesi,s0);

    }
}
