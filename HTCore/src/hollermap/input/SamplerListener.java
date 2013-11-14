/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hollermap.input;

/**
 *
 * @author rvlander
 */
public interface SamplerListener {
    public void newData( float lesX[],float lesY[],int lesB[],long lesT[],int res);
}
