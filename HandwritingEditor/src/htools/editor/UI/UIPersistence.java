/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.editor.UI;

import htools.editor.editor.Editor;
import htools.editor.main.HandwritingEditor;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.synth.SynthSplitPaneUI;

/**
 *
 * @author rvlander
 */
public class UIPersistence {

    static String file = "UIsav";
    static PanelEditorPopupMenu menu;

    public static void saveUI(HandwritingEditor f,JPanel rootPane) throws IOException {
        FileWriter ow = new FileWriter(file);
        PrintWriter pw = new PrintWriter(ow);
        
        
        pw.println(f.getCurrentFile());
        
        Point p = f.getLocation();
        pw.println(p.x +"," + p.y);
        pw.println(f.getWidth() +"," + f.getHeight());

        saveChild(rootPane.getComponent(0), pw);
        
        pw.flush();
        pw.close();
    }

    private static void savePanelEditor(PanelEditor pe, PrintWriter pw) {
        pw.println("PE," + pe.getSelectedSignal());
    }

    private static void saveSplitPane(JSplitPane sp, PrintWriter pw) {
        pw.println("SP," + sp.getDividerLocation() + "," + sp.getOrientation());
        Component l = sp.getLeftComponent();
        Component r = sp.getRightComponent();
        saveChild(l, pw);
        saveChild(r, pw);
    }

    private static void saveChild(Component c, PrintWriter pw) {
        if (c instanceof JSplitPane) {
            saveSplitPane((JSplitPane) c, pw);
        } else {
            savePanelEditor((PanelEditor) c, pw);
        }
    }

    public static void loadUI(HandwritingEditor f,JPanel rootPane, Editor editor) throws FileNotFoundException, IOException {
        menu = f.getPopupMenu();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        
        
        f.setCurrentFile(br.readLine());
        
        String line =br.readLine();
        String[] ss = line.split(",");
        int x = Integer.parseInt(ss[0]);
        int y = Integer.parseInt(ss[1]);
        
        line =br.readLine();
        ss = line.split(",");
        int w = Integer.parseInt(ss[0]);
        int h = Integer.parseInt(ss[1]);
        

       
        rootPane.add(readChild(br, editor),BorderLayout.CENTER);
        
        f.setSize(w,h);
        f.setLocation(x, y);
        
        br.close();
    }

    private static Component readChild(BufferedReader br, Editor e) throws IOException {
        String line = br.readLine();
        if (line.startsWith("SP")) {
            return readSplitPane(line, br, e);
        } else {
            return readPanelEditor(line, br, e);
        }
    }

    private static Component readSplitPane(String line, BufferedReader br, Editor e) throws IOException {
        String[] ss = line.split(",");
        Component l = readChild(br, e);
        Component r = readChild(br, e);
        int orientation = Integer.parseInt(ss[2]);
        int dividerLocation = Integer.parseInt(ss[1]);
        JSplitPane sp = new SplitPane(orientation, l, r);
        sp.setDividerLocation(dividerLocation);

        
        

        
        return sp;
    }

    private static Component readPanelEditor(String line, BufferedReader br, Editor e) {
        String[] ss = line.split(",");
        PanelEditor pe = new PanelEditor(e,menu);
        int ind = Integer.parseInt(ss[1]);
        pe.setSelectedSignal(ind);
        return pe;
    }
}
