package com.timepath.plaf.x.filechooser

import com.timepath.plaf.OS
import java.io.File
import java.util.logging.Logger
import javax.swing.JFileChooser
import javax.swing.filechooser.FileFilter

/**
 * @author TimePath
 */
public class SwingFileChooser : BaseFileChooser() {

    override fun choose(): Array<File>? {
        if (OS.isLinux()) {
            //            UIManager.put("FileChooserUI", "eu.kostia.gtkjfilechooser.ui.GtkFileChooserUI");
        }
        val fd = JFileChooser()
        fd.setAcceptAllFileFilterUsed(true)
        for (ef in filters) {
            val fileFilter = object : FileFilter() {
                override fun accept(f: File): Boolean {
                    for (e in ef.getExtensions()) {
                        if (f.getName().matches(".+" + e)) {
                            return true
                        }
                    }
                    return f.isDirectory()
                }

                override fun getDescription(): String {
                    val filter = StringBuilder()
                    filter.append(" (*").append(ef.getExtensions().get(0))
                    for (e in ef.getExtensions().subList(1, ef.getExtensions().size())) {
                        filter.append(", *").append(e)
                    }
                    filter.append(')')
                    return ef.description + filter
                }
            }
            fd.addChoosableFileFilter(fileFilter)
            if (fd.isAcceptAllFileFilterUsed()) {
                fd.setAcceptAllFileFilterUsed(false)
                fd.setFileFilter(fileFilter)
            }
        }
        fd.setDialogTitle(dialogTitle)
        fd.setDialogType(if (isSaveDialog()) JFileChooser.SAVE_DIALOG else JFileChooser.OPEN_DIALOG)
        if (directory != null) {
            val fileName = fileName
            if (fileName != null) {
                fd.setSelectedFile(File(directory, fileName))
            } else {
                fd.setCurrentDirectory(directory)
            }
        }
        fd.setFileSelectionMode(if (isDirectoryMode()) JFileChooser.DIRECTORIES_ONLY else JFileChooser.FILES_AND_DIRECTORIES)
        if (!isSaveDialog()) {
            fd.setMultiSelectionEnabled(multiSelectionEnabled)
        }
        var selection: Array<File>? = null
        if (fd.showDialog(parent, approveButtonText) == JFileChooser.APPROVE_OPTION) {
            selection = if (multiSelectionEnabled) fd.getSelectedFiles() else array(fd.getSelectedFile())
        }
        return selection
    }

    companion object {

        private val LOG = Logger.getLogger(javaClass<SwingFileChooser>().getName())
    }
}
