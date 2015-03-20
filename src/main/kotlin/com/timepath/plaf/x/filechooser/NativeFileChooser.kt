package com.timepath.plaf.x.filechooser

import com.timepath.plaf.OS
import com.timepath.plaf.win.JnaFileChooser
import java.io.File
import java.io.IOException
import java.util.logging.Logger

/**
 * @author TimePath
 */
public class NativeFileChooser : BaseFileChooser() {

    throws(javaClass<IOException>())
    override fun choose(): Array<File>? {
        return getChooser().choose()
    }

    private fun getChooser(): BaseFileChooser {
        val chooser = which()
        chooser.setApproveButtonText(approveButtonText)
                .setTitle(dialogTitle)
                .setDialogType(dialogType)
                .setDirectory(directory)
                .setFileName(fileName)
                .setFileMode(fileMode)
                .setMultiSelectionEnabled(multiSelectionEnabled)
                .setParent(parent)
        for (ef in filters) {
            chooser.addFilter(ef)
        }
        return chooser
    }

    companion object {

        private val LOG = Logger.getLogger(javaClass<NativeFileChooser>().getName())

        private fun which(): BaseFileChooser {
            try {
                if (OS.isWindows()) {
                    return JnaFileChooser()
                }
                //            if (OS.isMac()) {
                //                return new AWTFileChooser(); // FIXME
                //            }
                if (OS.isLinux()) {
                    val de = System.getenv("XDG_CURRENT_DESKTOP")
                    if (de != null) {
                        return if ("KDE".equalsIgnoreCase(de)) KDialogFileChooser() else ZenityFileChooser()
                    }
                }
            } catch (ignored: Throwable) {
            }

            return SwingFileChooser()
        }
    }
}
