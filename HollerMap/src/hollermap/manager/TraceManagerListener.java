/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hollermap.manager;

/**
 *
 * @author rvlander
 */
public interface TraceManagerListener {

    public void addedTraceStudy();

    public void addedTraceAnalyzer();

    public void removedTraceStudy();

    public void removedTraceAnalyzer();
    
    public void addedDefaultAnalyzer();
    
    public void removedDefaultAnalyzer();
}
