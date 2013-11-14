/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;

/**
 *
 * @author rvlander
 */
public class SplitPanePopupMenu extends JPopupMenu {

    private SplitPane splitPane;

    public SplitPanePopupMenu(SplitPane pe) {
        super();
        splitPane = pe;
        initMenu();
    }

    private void initMenu() {
        JMenuItem splitH = new JMenuItem("Horizontal Split");
        JMenuItem splitV = new JMenuItem("Vertical Split");
        JMenuItem join = new JMenuItem("Join");

        splitH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                splitPane.setState(SplitPane.HSPLIT);
                //editor.splitPanel(JSplitPane.HORIZONTAL_SPLIT);
            }
        });

        splitV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                splitPane.setState(SplitPane.VSPLIT);
               // editor.splitPanel(JSplitPane.VERTICAL_SPLIT);
            }
        });

        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                splitPane.setState(SplitPane.JOIN);
               // editor.joinPanel();
            }
        });
        
        
        this.add(splitH);
        this.add(splitV);
        this.add(join);

    }
}
