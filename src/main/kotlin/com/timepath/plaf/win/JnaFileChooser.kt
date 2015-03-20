package com.timepath.plaf.win

import com.sun.jna.Memory
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.WString
import com.timepath.plaf.win.jna.Comdlg32
import com.timepath.plaf.win.jna.Ole32
import com.timepath.plaf.win.jna.Shell32
import com.timepath.plaf.x.filechooser.BaseFileChooser

import java.io.File
import java.io.IOException
import java.util.logging.Logger

/**
 * @author TimePath
 */
public class JnaFileChooser : BaseFileChooser() {

    throws(javaClass<IOException>())
    override fun choose(): Array<File>? {
        return if (isDirectoryMode()) chooseDirectory() else chooseFile()
    }

    private fun chooseDirectory(): Array<File>? {
        Ole32.OleInitialize(null)
        val params = Shell32.BrowseInfo()
        params.hwndOwner = Native.getWindowPointer(parent)
        params.ulFlags = Shell32.BIF_RETURNONLYFSDIRS or Shell32.BIF_USENEWUI
        if (dialogTitle != null) {
            params.lpszTitle = dialogTitle
        }
        val pidl = Shell32.SHBrowseForFolder(params)
        if (pidl != null) {
            // MAX_PATH is 260 on Windows XP x32 so 4kB should be more than big enough
            val path = Memory((1024 * 4).toLong())
            Shell32.SHGetPathFromIDListW(pidl, path)
            val filePath = path.getWideString(0)
            Ole32.CoTaskMemFree(pidl)
            return array(File(filePath))
        }
        return null
    }

    private fun chooseFile(): Array<File>? {
        val params = Comdlg32.OpenFileName()
        params.Flags = Comdlg32.OFN_EXPLORER or Comdlg32.OFN_NOCHANGEDIR or Comdlg32.OFN_HIDEREADONLY or Comdlg32.OFN_ENABLESIZING
        if (parent != null) {
            params.hwndOwner = Native.getWindowPointer(parent)
        }
        params.lpstrFile = Memory(BUFFER_SIZE.toLong())
        params.lpstrFile.clear(BUFFER_SIZE.toLong())
        params.nMaxFile = MAX_PATH
        if (directory != null) {
            params.lpstrInitialDir = directory!!.getAbsolutePath()
        }
        if (!filters.isEmpty()) {
            // build filter string if filters were specified
            params.lpstrFilter = WString(buildFilterString())
            params.nFilterIndex = 1 // TODO: don't hardcode here
        }
        if (isMultiSelectionEnabled()) {
            params.Flags = params.Flags or Comdlg32.OFN_ALLOWMULTISELECT
        }
        val approved = if (isSaveDialog()) Comdlg32.GetSaveFileNameW(params) else Comdlg32.GetOpenFileNameW(params)
        if (approved) {
            val filePath = params.lpstrFile.getWideString(0)
            return array(File(filePath))
        }
        val errCode = Comdlg32.CommDlgExtendedError()
        if (errCode != 0) {
            throw RuntimeException("GetOpenFileName failed with error " + errCode)
        } // else user clicked cancel
        return null
    }

    /**
     * Builds a filter string
     * from MSDN:
     * A buffer containing pairs of null-terminated filter strings. The last
     * string in the buffer must be terminated by two NULL characters.
     * The first string in each pair is a display string that describes the
     * filter (for example, "Text Files"), and the second string specifies the
     * filter pattern (for example, "*.TXT"). To specify multiple filter
     * patterns for a single display string, use a semicolon to separate the
     * patterns (for example, "*.TXT;*.DOC;*.BAK").
     * http://msdn.microsoft.com/en-us/library/ms646839.aspx
     */
    private fun buildFilterString(): String {
        val filterStr = StringBuilder()
        val NUL = 0.toChar()
        for (ef in filters) {
            filterStr.append(ef.description).append(NUL)
            for (pattern in ef.getExtensions()) {
                filterStr.append('*').append(pattern).append(';')
            }
            // Remove last superfluous ";" and add terminator
            filterStr.deleteCharAt(filterStr.length() - 1)
            filterStr.append(NUL)
        }
        filterStr.append(NUL)
        return filterStr.toString()
    }

    companion object {

        /**
         * lpstrFile contains the selection path after the dialog returns. It must be big enough for
         * the path to fit or GetOpenFileName returns an error (FNERR_BUFFERTOOSMALL).
         * MAX_PATH is 260 so 4 bytes per char + 1 null byte should be big enough
         * http://msdn.microsoft.com/en-us/library/aa365247.aspx#maxpath
         */
        private val BUFFER_SIZE = (4 * 260) + 1
        private val LOG = Logger.getLogger(javaClass<JnaFileChooser>().getName())
        private val MAX_PATH = 260
    }
}
