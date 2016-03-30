package hollermap.input;

/**
 *  Java interface to Wintab
 *
 *  @version $Id: Jwintab.java,v 1.1 2000/01/12 12:31:34 rekimoto Exp rekimoto 
 *  @author rekimoto
 *  Log:
 *    1.29.2000 rekimoto: add pressure parameter
 *    1.8.2000  rekimoto:  initial creation
 *
 **/   
public class Jwintab {
    static {System.loadLibrary("jwintab");}

    Jwintab(){}

    /** returns current version number 
     */
    public static native int getVersion();
    
    public static native int open(String title);

	/** call this before closing the application, otherwise tablet does not
	    work.
	**/    
    public static native int close();
    
    /** val[0..5]  = {x, y, button, orientation, angle, pressure}
     */
    public static native int getPacket(int lesX[],int lesY[],int lesButton[],long lesTimes[]);

}

