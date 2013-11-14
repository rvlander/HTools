/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htools.expdo.manager;

import htools.expdo.experience.ExpIO;
import htools.expdo.experience.Manip;
//import htools.core.tools.HToolsConfigure;
import htools.expdo.main.InteractivePanel;
import htools.expdo.input.Options;

import java.awt.Component;
import java.io.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.jdom2.JDOMException;

/**
 *
 * @author rvlander
 */
public class TraceManagerUI extends javax.swing.JFrame implements TraceManagerListener, ListSelectionListener {

    //HToolsConfigure hmc = new HToolsConfigure();
    TraceManager tm;
    InteractivePanel ip;

    public TraceManagerUI(TraceManager tm, InteractivePanel ip) {
        this.tm = tm;

        this.ip = ip;
        tm.setInteractivePanel(ip);
        initComponents();
        this.setTitle("HollerMap");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jButtonNext = new javax.swing.JButton();
        jLabelMessage = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jStatusSelected = new javax.swing.JLabel();
        jStatusDrawn = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuNew = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuLoad = new javax.swing.JMenuItem();
        jMenuSave = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuPlay = new javax.swing.JMenuItem();
        jMenuMode = new javax.swing.JMenuItem();
        jMenuRecording = new javax.swing.JMenuItem();
        jMenuSetBackground = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuConfigureExportPath = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(150, 611));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Traces"));

        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jTree1);
        jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree1.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("No Manip...")));
        jTree1.setCellRenderer(new MyTreeRenderer(tm));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 387, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE))
        );

        jButtonNext.setText("Next");
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 270, Short.MAX_VALUE)
                .addComponent(jButtonNext))
        );

        jSplitPane1.setLeftComponent(jPanel1);
        jSplitPane1.setRightComponent(jLabelMessage);

        jStatusSelected.setText("   ");

        jStatusDrawn.setText("    ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jStatusSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addComponent(jStatusDrawn, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jStatusSelected)
                    .addComponent(jStatusDrawn))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu1.setText("File");

        jMenuNew.setText("New Manip ...");
        jMenuNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuNewActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuNew);
        jMenu1.add(jSeparator1);

        jMenuLoad.setText("Load");
        jMenuLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuLoadActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuLoad);

        jMenuSave.setText("Save");
        jMenuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSaveActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuSave);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Shortcuts");

        jMenuPlay.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, 0));
        jMenuPlay.setText("Play");
        jMenuPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuPlayActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuPlay);

        jMenuMode.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, 0));
        jMenuMode.setText("Change Mode");
        jMenuMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuModeActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuMode);

        jMenuRecording.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, 0));
        jMenuRecording.setText("Toggle Recording");
        jMenuRecording.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuRecordingActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuRecording);

        jMenuSetBackground.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, 0));
        jMenuSetBackground.setText("Set Background");
        jMenuSetBackground.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSetBackgroundActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuSetBackground);

        jMenuBar1.add(jMenu2);

        jMenu5.setText("Configure");

        jMenuConfigureExportPath.setText("Export Path");
        jMenuConfigureExportPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuConfigureExportPathActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuConfigureExportPath);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuLoadActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        fc.setCurrentDirectory(new File(Options.getExportPath()));

        int returnVal = fc.showDialog(this, "Load");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            this.load(file.getPath());
            this.repaint();

        } else {
            System.out.println("Open command cancelled by user.");
        }
    }//GEN-LAST:event_jMenuLoadActionPerformed

    private void jMenuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSaveActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        fc.setCurrentDirectory(new File(Options.getExportPath()));

        int returnVal = fc.showDialog(this, "Save");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            this.save(file.getPath());

        } else {
            System.out.println("Open command cancelled by user.");
        }
    }//GEN-LAST:event_jMenuSaveActionPerformed

    private void jMenuPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPlayActionPerformed
        ip.play();
    }//GEN-LAST:event_jMenuPlayActionPerformed

    private void jMenuModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuModeActionPerformed
        ip.changeMode();
    }//GEN-LAST:event_jMenuModeActionPerformed

    private void jMenuRecordingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuRecordingActionPerformed
        ip.toggleRecording();
    }//GEN-LAST:event_jMenuRecordingActionPerformed

    private void jMenuConfigureExportPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuConfigureExportPathActionPerformed
      //  hmc.configureExportPath(this);
    }//GEN-LAST:event_jMenuConfigureExportPathActionPerformed

    private void jMenuSetBackgroundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSetBackgroundActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        

        int returnVal = fc.showDialog(this, "Load Image");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            ip.setBackground(file.getPath());

        } else {
            System.out.println("Open command cancelled by user.");
        }
    }//GEN-LAST:event_jMenuSetBackgroundActionPerformed

    private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
        tm.newStepReturn();
    }//GEN-LAST:event_jButtonNextActionPerformed

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) ((JTree)evt.getSource()).getLastSelectedPathComponent();

       // System.out.println("here");
        
        if (node == null ||  node.getUserObject() instanceof String) //Nothing is selected.     
        {
            return;
        }

        tm.setSelectedNode(node);
    }//GEN-LAST:event_jTree1ValueChanged

    private void jMenuNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuNewActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        fc.setCurrentDirectory(new File(Options.getManipPath()));
        fc.setSelectedFile(new File(Options.getManipPath()));

        int returnVal = fc.showDialog(this, "Manip File");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String name = "";
            while(name == null || name.equals("")){
                name = JOptionPane.showInputDialog(this,"Name of individual.");
            }
            try {
                tm.setManip(ExpIO.readXML(file.getPath()), name);
            } catch (JDOMException ex) {
                Logger.getLogger(TraceManagerUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TraceManagerUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("Open command cancelled by user.");
        }
    }//GEN-LAST:event_jMenuNewActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonNext;
    private javax.swing.JLabel jLabelMessage;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuConfigureExportPath;
    private javax.swing.JMenuItem jMenuLoad;
    private javax.swing.JMenuItem jMenuMode;
    private javax.swing.JMenuItem jMenuNew;
    private javax.swing.JMenuItem jMenuPlay;
    private javax.swing.JMenuItem jMenuRecording;
    private javax.swing.JMenuItem jMenuSave;
    private javax.swing.JMenuItem jMenuSetBackground;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel jStatusDrawn;
    private javax.swing.JLabel jStatusSelected;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

    public void valueChanged(ListSelectionEvent e) {
        /*
         * if (e.getSource() == this.jList1) { if
         * (this.jList1.getSelectedIndex() > -1) {
         * this.tm.setSelectedStudy(this.jList1.getSelectedIndex()); TraceStudy
         * ts = tm.getVTS().get(tm.getSelectedStudy());
         *
         * }
         * }
         */
    }

    public void addedTraceStudy() {
        this.updateTraceList();
    }

    public void removedTraceStudy() {
        this.updateTraceList();
    }

    private void updateTraceList() {
        /*
         * this.jList1.setListData(tm.getVTS());
         * this.jList1.setSelectedIndex(tm.getSelectedStudy()); TraceStudy ts = tm.getVTS().get(tm.getSelectedStudy());
         */
    }

    public void save(String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);

            // création d'un "flux objet" avec le flux fichier
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.tm);
            // on vide le tampon
            oos.flush();


            oos.close();

            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(String path) {
        try {
            FileInputStream fos = new FileInputStream(path);

            // création d'un "flux objet" avec le flux fichier
            ObjectInputStream oos = new ObjectInputStream(fos);

            this.tm = (TraceManager) oos.readObject();
            this.ip.setTM(tm);
            this.addedTraceStudy();
            //     this.tm.loadListener(this);


            oos.close();

            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void manipNext(String message,int w, int h) {
        jLabelMessage.setText(message);
        this.ip.constrain(w,h);
        //jButtonNext.setText("Next");
        this.repaint();
    }

    @Override
    public void manipEnds() {
        jLabelMessage.setText("Finished");
        this.repaint();
    }


    public void newManip(DefaultMutableTreeNode n) {
        jTree1.setModel(new DefaultTreeModel(n));
       // this.ip.setSaveImage(((Manip)n.getUserObject()).isSaveImage());
    }

    @Override
    public void selectedNodeChanged(DefaultMutableTreeNode p) {
       // System.out.print(p);
        this.jTree1.setSelectionPath(new TreePath(p.getPath()));
    }

    @Override
    public Component getYou() {
        return this;
    }


}
