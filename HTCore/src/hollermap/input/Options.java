/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hollermap.input;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rvlander
 */
public class Options {

    static String config_file = "all.config";
    static Properties pop;

    static {
        Options.pop = new Properties();
        try {
            Options.pop.load(new FileInputStream(Options.config_file));
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void save(){
        try {
            Options.pop.store(new FileOutputStream(Options.config_file), null);
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
     public static String getSamplerType() {
        return pop.getProperty("sampler");
    }
    public static void setSamplerType(String type){
        pop.setProperty("sampler", type);
        Options.save();
    }

    public static String getExportPath() {
        return pop.getProperty("storing_path");
    }
    public static void setExportPath(String path){
        pop.setProperty("storing_path", path);
        Options.save();
    }

    public static String getPDFReportPath() {
        return pop.getProperty("pdfreport_path");
    }
    public static void setPDFReportPath(String path){
        pop.setProperty("pdfreport_path", path);
        Options.save();
    }
    
    public static String getWacomDir() {
        return pop.getProperty("wacom_dir");
    }
    public static void setWacomDir(String path){
        pop.setProperty("wacom_dir", path);
        Options.save();
    }
    
    public static int getScreenWidth(){
        return Integer.parseInt(pop.getProperty("screen_width"));
    }
    public static int getScreenHeight(){
        return Integer.parseInt(pop.getProperty("screen_height"));
    }
    
    
    //xmin,xmax,ymin,ymax;
    public static int[] getSensorBox(){
        int[] res = new int[4];
        String s = pop.getProperty("sensor_box");
        String[] ss = s.split(",");
        for(int i =0; i< ss.length;i++ ){
            res[i] = Integer.parseInt(ss[i]);
        }
        return res;
    }
    public static void setSensorBox(int xmin, int xmax, int ymin, int ymax){
        String r = xmin+","+xmax+","+ymin+","+ymax;
        pop.setProperty("sensor_box", r);
        Options.save();
    }
    
}
