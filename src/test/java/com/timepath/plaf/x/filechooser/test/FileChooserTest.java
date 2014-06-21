package com.timepath.plaf.x.filechooser.test;

import com.timepath.plaf.win.JnaFileChooser;
import com.timepath.plaf.x.filechooser.*;
import com.timepath.plaf.x.filechooser.BaseFileChooser.ExtensionFilter;
import com.timepath.plaf.x.filechooser.BaseFileChooser.FileMode;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TimePath
 */
public class FileChooserTest extends JFrame {

    private static final long   serialVersionUID = 1L;
    private static final Logger LOG              = Logger.getLogger(FileChooserTest.class.getName());
    private JCheckBox   checkDirectories;
    private JCheckBox   checkFiles;
    private JCheckBox   checkMulti;
    private JCheckBox   checkSave;
    private JButton     jButton1;
    private JLabel      jLabel1;
    private JList       jList1;
    private JPanel      jPanel1;
    private JPanel      jPanel2;
    private JPanel      jPanel3;
    private JPanel      jPanel4;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTable      jTable1;
    private JTextField  textTitle;

    /**
     * Creates new form FileChooserTest
     */
    public FileChooserTest() {
        initComponents(); this.jList1.setSelectedIndex(0);
    }

    private void initComponents() {
        jScrollPane1 = new JScrollPane(); jList1 = new JList(); jScrollPane2 = new JScrollPane(); jTable1 = new JTable();
        jPanel1 = new JPanel(); jButton1 = new JButton(); jPanel2 = new JPanel(); jLabel1 = new JLabel();
        textTitle = new JTextField(); jPanel3 = new JPanel(); checkFiles = new JCheckBox(); checkDirectories = new JCheckBox();
        jPanel4 = new JPanel(); checkSave = new JCheckBox(); checkMulti = new JCheckBox();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); setTitle("File dialog tester");
        jList1.setModel(getFileChoosers()); jScrollPane1.setViewportView(jList1); jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new DefaultTableModel(new Object[][] {
        }, new String[] {
                "Path", "Name"
        }
        )
        {
            Class[] types = new Class[] {
                    String.class, String.class
            };
            boolean[] canEdit = new boolean[] {
                    true, false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return canEdit[column];
            }
        }); jScrollPane2.setViewportView(jTable1); jButton1.setText("Test"); jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1ActionPerformed(e);
            }
        }); jLabel1.setText("Title:"); textTitle.setMinimumSize(new Dimension(78, 27));
        textTitle.setPreferredSize(new Dimension(78, 27)); GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout); jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
                                                                                        .addGroup(jPanel2Layout
                                                                                                          .createSequentialGroup()
                                                                                                               .addContainerGap()
                                                                                                               .addComponent(
                                                                                                                       jLabel1)
                                                                                                               .addPreferredGap(
                                                                                                                       ComponentPlacement.RELATED)
                                                                                                               .addComponent(
                                                                                                                       textTitle,
                                                                                                                       GroupLayout.PREFERRED_SIZE,
                                                                                                                       GroupLayout.DEFAULT_SIZE,
                                                                                                                       GroupLayout.PREFERRED_SIZE)
                                                                                                               .addContainerGap())
                                                                          );
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                                           .addContainerGap()
                                                                           .addGroup(jPanel2Layout.createParallelGroup
                                                                                   (Alignment.BASELINE)
                                                                                                  .addComponent(textTitle,
                                                                                                                GroupLayout
                                                                                                                        .PREFERRED_SIZE,
                                                                                                                GroupLayout
                                                                                                                        .DEFAULT_SIZE,
                                                                                                                GroupLayout
                                                                                                                        .PREFERRED_SIZE
                                                                                                               )
                                                                                                  .addComponent(jLabel1))
                                                                           .addContainerGap())
                                      ); checkFiles.setSelected(true); checkFiles.setText("Files");
        checkDirectories.setText("Directories"); GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout); jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
                                                                                        .addGroup(jPanel3Layout
                                                                                                          .createSequentialGroup()
                                                                                                               .addContainerGap()
                                                                                                               .addGroup(
                                                                                                                       jPanel3Layout
                                                                                                                               .createParallelGroup(
                                                                                                                                       Alignment.LEADING)
                                                                                                                               .addComponent(
                                                                                                                                       checkFiles)
                                                                                                                               .addComponent(
                                                                                                                                       checkDirectories)
                                                                                                                        )
                                                                                                               .addContainerGap())
                                                                          );
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
                                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                                                           .addContainerGap()
                                                                           .addComponent(checkFiles)
                                                                           .addPreferredGap(ComponentPlacement.RELATED)
                                                                           .addComponent(checkDirectories)
                                                                           .addContainerGap())
                                      ); checkSave.setText("Save"); checkMulti.setText("Multi");
        GroupLayout jPanel4Layout = new GroupLayout(jPanel4); jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
                                                      .addGroup(jPanel4Layout.createSequentialGroup()
                                                                             .addContainerGap()
                                                                             .addGroup(jPanel4Layout.createParallelGroup
                                                                                     (Alignment.LEADING)
                                                                                                    .addComponent(checkSave)
                                                                                                    .addComponent(checkMulti))
                                                                             .addContainerGap())
                                        ); jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
                                                                                       .addGroup(jPanel4Layout
                                                                                                         .createSequentialGroup()
                                                                                                              .addContainerGap()
                                                                                                              .addComponent(
                                                                                                                      checkSave)
                                                                                                              .addPreferredGap(
                                                                                                                      ComponentPlacement.RELATED)
                                                                                                              .addComponent(
                                                                                                                      checkMulti)
                                                                                                              .addContainerGap())
                                                                         ); GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout); jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
                                                                                        .addGroup(jPanel1Layout
                                                                                                          .createSequentialGroup()
                                                                                                               .addContainerGap()
                                                                                                               .addGroup(
                                                                                                                       jPanel1Layout
                                                                                                                               .createParallelGroup(
                                                                                                                                       Alignment.LEADING,
                                                                                                                                       false)
                                                                                                                               .addGroup(
                                                                                                                                       jPanel1Layout
                                                                                                                                               .createSequentialGroup()
                                                                                                                                               .addComponent(
                                                                                                                                                       jPanel3,
                                                                                                                                                       GroupLayout.PREFERRED_SIZE,
                                                                                                                                                       GroupLayout.DEFAULT_SIZE,
                                                                                                                                                       GroupLayout.PREFERRED_SIZE)
                                                                                                                                               .addPreferredGap(
                                                                                                                                                       ComponentPlacement.RELATED)
                                                                                                                                               .addComponent(
                                                                                                                                                       jPanel2,
                                                                                                                                                       GroupLayout.PREFERRED_SIZE,
                                                                                                                                                       GroupLayout.DEFAULT_SIZE,
                                                                                                                                                       GroupLayout.PREFERRED_SIZE)
                                                                                                                                        )
                                                                                                                               .addGroup(
                                                                                                                                       jPanel1Layout
                                                                                                                                               .createSequentialGroup()
                                                                                                                                               .addComponent(
                                                                                                                                                       jPanel4,
                                                                                                                                                       GroupLayout.PREFERRED_SIZE,
                                                                                                                                                       GroupLayout.DEFAULT_SIZE,
                                                                                                                                                       GroupLayout.PREFERRED_SIZE)
                                                                                                                                               .addPreferredGap(
                                                                                                                                                       ComponentPlacement.RELATED,
                                                                                                                                                       GroupLayout.DEFAULT_SIZE,
                                                                                                                                                       Short.MAX_VALUE)
                                                                                                                                               .addComponent(
                                                                                                                                                       jButton1,
                                                                                                                                                       GroupLayout.PREFERRED_SIZE,
                                                                                                                                                       105,
                                                                                                                                                       GroupLayout.PREFERRED_SIZE)
                                                                                                                                        )
                                                                                                                        )
                                                                                                               .addContainerGap
                                                                                                                       (43,
                                                                                                                                Short.MAX_VALUE))
                                                                          );
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                                           .addContainerGap()
                                                                           .addGroup(jPanel1Layout.createParallelGroup
                                                                                   (Alignment.LEADING)
                                                                                                  .addGroup(jPanel1Layout
                                                                                                                    .createSequentialGroup()
                                                                                                                         .addComponent(
                                                                                                                                 jPanel2,
                                                                                                                                 GroupLayout.PREFERRED_SIZE,
                                                                                                                                 GroupLayout.DEFAULT_SIZE,
                                                                                                                                 GroupLayout.PREFERRED_SIZE)
                                                                                                                         .addPreferredGap(
                                                                                                                                 ComponentPlacement.RELATED,
                                                                                                                                 GroupLayout.DEFAULT_SIZE,
                                                                                                                                 Short.MAX_VALUE)
                                                                                                                         .addComponent(
                                                                                                                                 jButton1,
                                                                                                                                 GroupLayout.PREFERRED_SIZE,
                                                                                                                                 42,
                                                                                                                                 GroupLayout.PREFERRED_SIZE)
                                                                                                           )
                                                                                                  .addGroup(jPanel1Layout
                                                                                                                    .createSequentialGroup()
                                                                                                                         .addComponent(
                                                                                                                                 jPanel3,
                                                                                                                                 GroupLayout.DEFAULT_SIZE,
                                                                                                                                 GroupLayout.DEFAULT_SIZE,
                                                                                                                                 Short.MAX_VALUE)
                                                                                                                         .addPreferredGap(
                                                                                                                                 ComponentPlacement.RELATED)
                                                                                                                         .addComponent(
                                                                                                                                 jPanel4,
                                                                                                                                 GroupLayout.DEFAULT_SIZE,
                                                                                                                                 GroupLayout.DEFAULT_SIZE,
                                                                                                                                 Short.MAX_VALUE)
                                                                                                           ))
                                                                           .addContainerGap())
                                      ); GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout); layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
                                                                            .addGroup(layout.createSequentialGroup()
                                                                                            .addContainerGap()
                                                                                            .addGroup(layout.createParallelGroup(
                                                                                                    Alignment.LEADING)
                                                                                                            .addComponent(jPanel1,
                                                                                                                          GroupLayout.DEFAULT_SIZE,
                                                                                                                          GroupLayout.DEFAULT_SIZE,
                                                                                                                          Short.MAX_VALUE)
                                                                                                            .addComponent(
                                                                                                                    jScrollPane2,
                                                                                                                    GroupLayout
                                                                                                                            .PREFERRED_SIZE,
                                                                                                                    0,
                                                                                                                    Short.MAX_VALUE)
                                                                                                            .addComponent(
                                                                                                                    jScrollPane1))
                                                                                            .addContainerGap())
                                                                     );
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
                                      .addGroup(layout.createSequentialGroup()
                                                      .addContainerGap()
                                                      .addComponent(jScrollPane1,
                                                                    GroupLayout.PREFERRED_SIZE,
                                                                    GroupLayout.DEFAULT_SIZE,
                                                                    GroupLayout.PREFERRED_SIZE)
                                                      .addPreferredGap(ComponentPlacement.RELATED)
                                                      .addComponent(jPanel1,
                                                                    GroupLayout.PREFERRED_SIZE,
                                                                    GroupLayout.DEFAULT_SIZE,
                                                                    GroupLayout.PREFERRED_SIZE)
                                                      .addPreferredGap(ComponentPlacement.RELATED,
                                                                       GroupLayout.DEFAULT_SIZE,
                                                                       Short.MAX_VALUE)
                                                      .addComponent(jScrollPane2,
                                                                    GroupLayout.PREFERRED_SIZE,
                                                                    197,
                                                                    GroupLayout.PREFERRED_SIZE)
                                                      .addContainerGap())
                               ); pack();
    }

    private static ListModel getFileChoosers() {
        return new AbstractListModel() {
            private static final long serialVersionUID = 1L;
            @SuppressWarnings("unchecked")
            private final Class<? extends BaseFileChooser>[] strings = new Class[] {
                    NativeFileChooser.class,
                    AWTFileChooser.class,
                    SwingFileChooser.class,
                    ZenityFileChooser.class,
                    KDialogFileChooser.class,
                    JnaFileChooser.class
            };

            @Override
            public Object getElementAt(int index) {
                return strings[index];
            }

            @Override
            public int getSize() {
                return strings.length;
            }
        };
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        try {
            Class<?> clazz = (Class) this.jList1.getSelectedValue(); Object o = clazz.newInstance();
            if(!( o instanceof BaseFileChooser )) {
                return;
            } BaseFileChooser c = (BaseFileChooser) o; c.setParent(this)
                                                        .setFileMode(this.checkDirectories.isSelected()
                                                                     ? FileMode.DIRECTORIES_ONLY
                                                                     : ( this.checkFiles.isSelected()
                                                                         ? FileMode.FILES_ONLY
                                                                         : FileMode.FILES_AND_DIRECTORIES )
                                                                    )
                                                        .setDialogType(this.checkSave.isSelected()
                                                                       ? BaseFileChooser.DialogType.SAVE_DIALOG
                                                                       : BaseFileChooser.DialogType.OPEN_DIALOG
                                                                      )
                                                        .setMultiSelectionEnabled(this.checkMulti.isSelected())
                                                        .setTitle(this.textTitle.getText());
            c.addFilter(new ExtensionFilter("All files", ".*")); File[] f = c.choose(); if(f == null) {
                return;
            } DefaultTableModel table = (DefaultTableModel) jTable1.getModel(); table.setRowCount(0); for(File aF : f) {
                Object[] row = new Object[] { aF.getParentFile().getPath(), aF.getName() }; table.addRow(row);
                LOG.log(Level.INFO, "{0}: {1}", new Object[] { aF, Arrays.toString(row) });
            }
        } catch(IOException | InstantiationException | IllegalAccessException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args
     *         the command line arguments
     */
    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FileChooserTest().setVisible(true);
            }
        });
    }
}
