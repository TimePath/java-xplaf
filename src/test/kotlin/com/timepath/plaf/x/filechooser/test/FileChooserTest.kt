package com.timepath.plaf.x.filechooser.test

import com.timepath.plaf.win.JnaFileChooser
import com.timepath.plaf.x.filechooser.*
import com.timepath.plaf.x.filechooser.BaseFileChooser.ExtensionFilter
import com.timepath.plaf.x.filechooser.BaseFileChooser.FileMode
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.IOException
import java.util.Arrays
import java.util.logging.Level
import java.util.logging.Logger
import javax.swing.*
import javax.swing.GroupLayout.Alignment
import javax.swing.LayoutStyle.ComponentPlacement
import javax.swing.table.DefaultTableModel
import kotlin.platform.platformStatic

/**
 * @author TimePath
 */
public class FileChooserTest : JFrame() {
    private val checkDirectories = JCheckBox()
    private val checkFiles = JCheckBox()
    private val checkMulti = JCheckBox()
    private val checkSave = JCheckBox()
    private val jButton1 = JButton()
    private val jLabel1 = JLabel()
    private val jList1 = JList<Any>()
    private val jPanel1 = JPanel()
    private val jPanel2 = JPanel()
    private val jPanel3 = JPanel()
    private val jPanel4 = JPanel()
    private val jScrollPane1 = JScrollPane()
    private val jScrollPane2 = JScrollPane()
    private val jTable1 = JTable()
    private val textTitle = JTextField()

    init {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
        setTitle("File dialog tester")
        jList1.setModel(getFileChoosers())
        jScrollPane1.setViewportView(jList1)
        jTable1.setAutoCreateRowSorter(true)
        jTable1.setModel(object : DefaultTableModel(array("Path", "Name"), 0) {
            var types = array<Class<*>>(javaClass<String>(), javaClass<String>())
            var canEdit = booleanArray(true, false)

            override fun getColumnClass(columnIndex: Int): Class<out Any?> {
                return types[columnIndex]
            }

            override fun isCellEditable(row: Int, column: Int): Boolean {
                return canEdit[column]
            }
        })
        jScrollPane2.setViewportView(jTable1)
        jButton1.setText("Test")
        jButton1.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent) = jButton1ActionPerformed()
        })
        jLabel1.setText("Title:")
        textTitle.setMinimumSize(Dimension(78, 27))
        textTitle.setPreferredSize(Dimension(78, 27))
        val jPanel2Layout = GroupLayout(jPanel2)
        jPanel2.setLayout(jPanel2Layout)
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(ComponentPlacement.RELATED).addComponent(textTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap()))
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE).addComponent(textTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(jLabel1)).addContainerGap()))
        checkFiles.setSelected(true)
        checkFiles.setText("Files")
        checkDirectories.setText("Directories")
        val jPanel3Layout = GroupLayout(jPanel3)
        jPanel3.setLayout(jPanel3Layout)
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addComponent(checkFiles).addComponent(checkDirectories)).addContainerGap()))
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(checkFiles).addPreferredGap(ComponentPlacement.RELATED).addComponent(checkDirectories).addContainerGap()))
        checkSave.setText("Save")
        checkMulti.setText("Multi")
        val jPanel4Layout = GroupLayout(jPanel4)
        jPanel4.setLayout(jPanel4Layout)
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING).addComponent(checkSave).addComponent(checkMulti)).addContainerGap()))
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(checkSave).addPreferredGap(ComponentPlacement.RELATED).addComponent(checkMulti).addContainerGap()))
        val jPanel1Layout = GroupLayout(jPanel1)
        jPanel1.setLayout(jPanel1Layout)
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt()).addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))).addContainerGap(43, java.lang.Short.MAX_VALUE.toInt())))
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt()).addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt()).addPreferredGap(ComponentPlacement.RELATED).addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt()))).addContainerGap()))
        val layout = GroupLayout(getContentPane())
        getContentPane().setLayout(layout)
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt()).addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 0, java.lang.Short.MAX_VALUE.toInt()).addComponent(jScrollPane1)).addContainerGap()))
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt()).addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE).addContainerGap()))
        pack()
        this.jList1.setSelectedIndex(0)
    }

    private fun jButton1ActionPerformed() {
        try {
            val clazz = this.jList1.getSelectedValue() as Class<*>
            val bfc = clazz.newInstance()
            if (bfc !is BaseFileChooser) {
                return
            }
            bfc.setParent(this)
            bfc.setFileMode(if (this.checkDirectories.isSelected())
                FileMode.DIRECTORIES_ONLY
            else
                (if (this.checkFiles.isSelected())
                    FileMode.FILES_ONLY
                else
                    FileMode.FILES_AND_DIRECTORIES))
            bfc.setDialogType(if (this.checkSave.isSelected())
                BaseFileChooser.DialogType.SAVE_DIALOG
            else
                BaseFileChooser.DialogType.OPEN_DIALOG).setMultiSelectionEnabled(this.checkMulti.isSelected()).setTitle(this.textTitle.getText())
            bfc.addFilter(ExtensionFilter("All files", ".*"))
            val f = bfc.choose()
            if (f == null) {
                return
            }
            val table = jTable1.getModel() as DefaultTableModel
            table.setRowCount(0)
            for (aF in f) {
                val row = array<Any>(aF.getParentFile().getPath(), aF.getName())
                table.addRow(row)
                LOG.log(Level.INFO, "{0}: {1}", array<Any>(aF, Arrays.toString(row)))
            }
        } catch (ex: IOException) {
            LOG.log(Level.SEVERE, null, ex)
        } catch (ex: InstantiationException) {
            LOG.log(Level.SEVERE, null, ex)
        } catch (ex: IllegalAccessException) {
            LOG.log(Level.SEVERE, null, ex)
        }

    }

    companion object {

        private val serialVersionUID = 1
        private val LOG = Logger.getLogger(javaClass<FileChooserTest>().getName())

        private fun getFileChoosers() = object : AbstractListModel<Any>() {
            SuppressWarnings("unchecked")
            private val strings = array(
                    javaClass<NativeFileChooser>(),
                    javaClass<AWTFileChooser>(),
                    javaClass<SwingFileChooser>(),
                    javaClass<ZenityFileChooser>(),
                    javaClass<KDialogFileChooser>(),
                    javaClass<JnaFileChooser>()
            )

            override fun getElementAt(index: Int): Any? {
                return strings[index]
            }

            override fun getSize(): Int {
                return strings.size()
            }
        }

        /**
         * @param args the command line arguments
         */
        public platformStatic fun main(args: Array<String>) {
            EventQueue.invokeLater {
                FileChooserTest().setVisible(true)
            }
        }
    }
}
