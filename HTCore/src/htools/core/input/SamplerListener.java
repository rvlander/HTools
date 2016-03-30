/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.core.input;

/**
 *
 * @author rvlander
 */
public interface SamplerListener {
    public void newSizedData( float lesX[],float lesY[],int lesB[],long lesT[],int res);
    public void newRawData( float lesX[],float lesY[],int lesB[],long lesT[],int res);
    public int getWidth();
    public int getHeight();
}
