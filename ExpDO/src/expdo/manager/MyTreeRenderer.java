/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expdo.manager;

import expdo.experience.Experience;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author rvlander
 */
class MyTreeRenderer extends DefaultTreeCellRenderer {

    TraceManager tm;
    ImageIcon checked;
    ImageIcon unchecked;

    public MyTreeRenderer(TraceManager tm) {
        super();
        try {
            checked = new ImageIcon(ImageIO.read(new File("checked.png")));
            unchecked = new ImageIcon(ImageIO.read(new File("unchecked.png")));
        } catch (IOException ex) {
            Logger.getLogger(MyTreeRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.tm = tm;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                row, hasFocus);

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        //Condition pour laquelle tu veux changer l'icone
        if (node.getUserObject() instanceof Integer) {
            //&& 
// !((Etat) node.getUserObject()).isDisponible()) {
//On affecte à la feuille une icone différente
            JLabel po = (JLabel) this;
            if (tm.hasBeenDone(node)) {
                po.setIcon(checked);
            } else {
                po.setIcon(unchecked);
            }


        }

        return this;
    }
}