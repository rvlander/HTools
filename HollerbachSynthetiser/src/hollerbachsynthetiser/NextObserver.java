/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hollerbachsynthetiser;

/**
 *
 * @author rvlander
 */
public interface NextObserver {
    public void updated(ParametersHollerbach h);
    public void next(int deactive);
}
