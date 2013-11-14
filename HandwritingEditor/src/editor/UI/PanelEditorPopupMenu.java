/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.UI;

import editor.main.HandwritingEditor;
import hollermap.traces.Trace;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;

/**
 *
 * @author rvlander
 */
public class PanelEditorPopupMenu extends JPopupMenu {

    HandwritingEditor editor;
    PanelEditor pe;

    public PanelEditorPopupMenu(HandwritingEditor pe) {
        super();
        this.editor = pe;
        initMenu();
    }

    private void initMenu() {
        JMenuItem splitH = new JMenuItem("append Trace ...");
        JMenuItem appendH = new JMenuItem("append Trace (holler)...");
        JMenuItem smooth = new JMenuItem("smooth all ..");
        JMenuItem smooths = new JMenuItem("smooth selection ..");
        JMenuItem play = new JMenuItem("Play");


        splitH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

                fc.setCurrentDirectory(new File(editor.getCurrentFile()).getParentFile());

                int returnVal = fc.showDialog(editor, "append");

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = fc.getSelectedFile();
                        Trace T = Trace.read(new BufferedReader(new FileReader(file)));
                        editor.getEditor().appendTrace(T);
                    } catch (IOException ex) {
                        Logger.getLogger(PanelEditorPopupMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("Open command cancelled by user.");
                }
            }
        });
        
        appendH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

                fc.setCurrentDirectory(new File(editor.getCurrentFile()).getParentFile());

                int returnVal = fc.showDialog(editor, "append");

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = fc.getSelectedFile();
                        Trace T = Trace.read(new BufferedReader(new FileReader(file)));
                        editor.getEditor().appendHollerTrace(T);
                    } catch (IOException ex) {
                        Logger.getLogger(PanelEditorPopupMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("Open command cancelled by user.");
                }
            }
        });

        smooth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                editor.getEditor().smooth();
            }
        });


        smooths.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                pe.smooth();
            }
        });
        
         play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                editor.play();
            }
        });



        this.add(splitH);
        this.add(appendH);
        this.add(smooth);
        this.add(smooths);
        this.add(new JSeparator());
        this.add(play);


    }

    @Override
    public void show(Component c, int x, int y) {
        super.show(c, x, y);
        this.pe = (PanelEditor) c;
    }
}
