/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hollermap.mat2java;

import Jama.Matrix;
import java.util.Vector;

/**
 *
 * @author rvlander
 */
public class Functions {
    public static double[] diff(double[] in){
        double[] out = new double[in.length-1];
        for(int i=0;i<out.length;i++){
            out[i] = in[i+1]-in[i];
        }
        return out;
    }

    public static int[] createIndex(int debut, int step,int fin){
        int n = (fin-debut)/step +1;
        int index[] = new int[n];
        for(int i=0;i<n;i++){
            index[i] = debut +i*step;
        }
        return index;
    }

    public static int[] createIndex(int debut,int fin){
        return createIndex(debut,1,fin);
    }

    public static double[] createSample(double debut, double step,double fin){
        int n = (int)((fin-debut)/step +1);
        double index[] = new double[n];
        for(int i=0;i<n;i++){
            index[i] = debut +i*step;
        }
        return index;
    }

    public static int[] trivialIndex(){
        int res[] = new int[1];
        res[0]=0;
        return res;
    }

    public static int[] findEquals(double[] vals,double val){
        int[] ores = new int[vals.length];
        int k=0;
        for(int i=0;i<vals.length;i++){
            if(vals[i]==val){
                ores[k] = i;
                k++;
            }
        }
        if(k!=0) k--;
        int[] res = new int[k+1];
        for(int i=0;i<=k;i++){
            res[i] = ores[i];
        }
        return res;
    }
    public static int[] findLessers(double[] vals,double val){
        int[] ores = new int[vals.length];
        int k=0;
        for(int i=0;i<vals.length;i++){
            if(vals[i]<val){
                ores[k] = i;
                k++;
            }
        }
        int[] res;
        if(k!=0){
            k--;
            res = new int[k+1];
            for(int i=0;i<=k;i++){
                res[i] = ores[i];
            }
        }else{
            res = new int[0];
        }
        return res;
    }

    public static double[] concat(double[] A, double[] B){
        double[] C= new double[A.length+B.length];
        System.arraycopy(A, 0, C, 0, A.length);
        System.arraycopy(B, 0, C, A.length, B.length);
        return C;
    }

    public static int[] concat(int[] A, int[] B){
        int[] C= new int[A.length+B.length];
        System.arraycopy(A, 0, C, 0, A.length);
        System.arraycopy(B, 0, C, A.length, B.length);
        return C;
    }

    public static double[] abs(double[] in){
        double[] res = new double[in.length];
        for(int i=0;i<in.length;i++){
            res[i] = Math.abs(in[i]);
        }
        return res;
    }

     public static int index_max(double[] tab){
        double max = tab[0];
        int index = 0;
        for(int i=0;i<tab.length;i++){
            if(tab[i]>max){
                max = tab[i];
                index = i;
            }
        }
        return index;
    }

     public static String affiche(int[] tab){
         String res= "";
         for(int i =0;i< tab.length;i++){
            res += tab[i]+" ";
         }
         return res;
     }

      public static String affiche(double[] tab){
         String res= "";
         for(int i =0;i< tab.length;i++){
            res += tab[i]+" ";
         }
         return res;
     }

     public static int getLesser(double[] tab,double t){
         int i=0;
         while(i<tab.length && t>tab[i]) i++;
         if (i>=tab.length) i = tab.length-1;
         else i--;
         if(i<0) i=0;
         return i;
     }

     public static double[] removeFirst(double[] tab){
         double[] res = new double[tab.length-1];
         for(int i = 1;i<tab.length;i++){
             res[i-1] = tab[i];
         }
         return res;
     }

     public static int[] borders(double[] in){
         Vector<Integer> tmp = new Vector<Integer>();
         tmp.add(0);
         for(int i=1;i<in.length;i++){
             if(in[i]!=in[i-1]){
                 tmp.add(i);
             }
         }
         int[] res = new int[tmp.size()];
         for(int i=0;i<res.length;i++){
             res[i] = tmp.get(i);
         }
         return res;
     }

     public static double[] extract(int[] inds,double[] vals){
         double[] res = new double[inds.length];
         for(int i=0;i<res.length;i++){
             res[i] = vals[inds[i]];
         }
         return res;
     }

     public static int[] extract(int[] inds,int[] vals){
         int[] res = new int[inds.length];
         for(int i=0;i<res.length;i++){
             res[i] = vals[inds[i]];
         }
         return res;
     }

     public static double[] add(double[] tab1,double[] tab2){
         double[] res = new double[tab2.length];
         for(int i=0;i<tab2.length;i++){
             res[i] = tab1[i]+tab2[i];
         }
         return res;
     }
     public static double[] oppose(double[] tab1){
         double[] res = new double[tab1.length];
         for(int i=0;i<tab1.length;i++){
             res[i] = -tab1[i];
         }
         return res;
     }

     public static double[] substract(double[] tab1,double[] tab2){
         return add(tab1,oppose(tab2));
     }

     public static double[] DoubleVector2array(Vector<Double> a){
         double[] res = new double[a.size()];
         for(int i=0;i< a.size();i++)
             res[i] = a.get(i);
         return res;
     }

     public static int[] index_sort(double[] tab){
         int[] index = new int[tab.length];
         for (int i=0;i< index.length;i++)
             index[i] = i;
         for(int i=0;i< index.length;i++)
             Functions.insert_for_sort(tab, index, i);
         return index;

     }

     public static void insert_for_sort(double[] tab,int[] indexes, int n){
         int k=n;
         while(k>0 && tab[indexes[k]]<tab[indexes[k-1]]){
             int temp = indexes[k-1];
             indexes[k-1] = indexes[k];
             indexes[k] = temp;
             k--;
         }
     }

     public static double[][] matrix2array(Matrix m){
         int taille = m.getColumnDimension();
         double[] truc = m.getColumnPackedCopy();
         double[][] res = new double[m.getColumnDimension()][m.getRowDimension()];
         int k =0;
         int l=0;
         for(int i=0;i<truc.length;i++){
             res[k][l] = truc[i];
             k++;
             if(k>=taille){
                 l++;
                 k=0;
             }
         }
         return res;
     }

     public static double[] rotateX(double ang,double[] X, double[] Y){
         int n = X.length;
         double[] res = new double[n];
         for(int i=0;i<n;i++){
             res[i] = X[i]*Math.cos(ang) + Y[i]*Math.sin(ang);
         }
         return res;
     }
      public static double[] rotateY(double ang,double[] X, double[] Y){
         int n = X.length;
         double[] res = new double[n];
         for(int i=0;i<n;i++){
             res[i] = -X[i]*Math.sin(ang) + Y[i]*Math.cos(ang);
         }
         return res;
     }

     public static double rotateX(double ang,double X, double Y){
             return X*Math.cos(ang) + Y*Math.sin(ang);
     }
     public static double rotateY(double ang,double X, double Y){
             return -X*Math.sin(ang) + Y*Math.cos(ang);
     }
     
     public static double[] SubArray(double[] s, int[] i){
         double r[] = new double[i.length];
         for(int j=0;j<i.length;j++){
             r[j] = s[i[j]];
         }
         return r;
     }
             

}
