/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hollermap.manager;

/**
 *
 * @author rvlander
 */
public class TraceAnalyzerParams {
    public int type;
    public double angl;
    public boolean c_zero;

    public TraceAnalyzerParams(int t,double a, boolean c) {
        type =t;
        angl = a;
        c_zero =c;
        
    }
    
    
}
