package com.timepath.plaf.x.filechooser

import com.timepath.plaf.OS
import com.timepath.plaf.mac.OSXProps

import java.awt.*
import java.io.File
import java.io.FilenameFilter
import java.util.logging.Logger

/**
 * @author TimePath
 */
public class AWTFileChooser : BaseFileChooser() {

    override fun choose(): Array<File>? {
        if (OS.isMac()) {
            OSXProps.setFileDialogDirectoryMode(isDirectoryMode())
        }
        val fd = FileDialog(parent, dialogTitle)
        fd.setFilenameFilter(object : FilenameFilter {
            override fun accept(dir: File, name: String): Boolean {
                for (ef in filters) {
                    for (e in ef.getExtensions()) {
                        if (dir.getName().endsWith(e)) {
                            return true
                        }
                    }
                }
                return false
            }
        })
        if (directory != null) {
            fd.setDirectory(directory!!.getPath())
        }
        if (fileName != null) {
            fd.setFile(fileName)
        }
        if (isDirectoryMode()) {
            if (!OS.isMac()) {
                LOG.warning("Using AWT for directory selection on non mac system - not ideal")
            }
            fd.setFilenameFilter(object : FilenameFilter {
                override fun accept(dir: File, name: String): Boolean {
                    return File(dir, name).isDirectory()
                }
            })
        }
        fd.setMode(if (isSaveDialog()) FileDialog.SAVE else FileDialog.LOAD)
        fd.setVisible(true)
        if ((fd.getDirectory() == null) || (fd.getFile() == null)) {
            // cancelled
            return null
        }
        return array(File(fd.getDirectory() + File.pathSeparator + fd.getFile()))
    }

    companion object {

        private val LOG = Logger.getLogger(javaClass<AWTFileChooser>().getName())
    }
}
