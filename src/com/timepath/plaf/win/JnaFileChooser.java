package com.timepath.plaf.win;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.timepath.plaf.win.jna.Comdlg32;
import com.timepath.plaf.win.jna.Ole32;
import com.timepath.plaf.win.jna.Shell32;
import com.timepath.plaf.x.filechooser.BaseFileChooser;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author andrew
 */
public class JnaFileChooser extends BaseFileChooser {

    /**
     * lpstrFile contains the selection path after the dialog returns. It must be big enough for
     * the path to fit or GetOpenFileName returns an error (FNERR_BUFFERTOOSMALL).
     * MAX_PATH is 260 so 4 bytes per char + 1 null byte should be big enough
     * http://msdn.microsoft.com/en-us/library/aa365247.aspx#maxpath
     */
    private static final int BUFFER_SIZE = 4 * 260 + 1;

    private static final Logger LOG = Logger.getLogger(JnaFileChooser.class.getName());

    private static final int MAX_PATH = 260;

    @Override
    public File[] choose() throws IOException {
        return this.isDirectoryMode() ? chooseDirectory() : chooseFile();
    }

    /**
     * Builds a filter string
     *
     * from MSDN:
     * A buffer containing pairs of null-terminated filter strings. The last
     * string in the buffer must be terminated by two NULL characters.
     *
     * The first string in each pair is a display string that describes the
     * filter (for example, "Text Files"), and the second string specifies the
     * filter pattern (for example, "*.TXT"). To specify multiple filter
     * patterns for a single display string, use a semicolon to separate the
     * patterns (for example, "*.TXT;*.DOC;*.BAK").
     *
     * http://msdn.microsoft.com/en-us/library/ms646839.aspx
     */
    private String buildFilterString() {
        StringBuilder filterStr = new StringBuilder();
        for(ExtensionFilter ef : filters) {
            filterStr.append(ef.getDescription()).append('\0');

            for(String pattern : ef.getExtensions()) {
                filterStr.append('*').append(pattern).append(';');
            }
            // Remove last superfluous ";" and add terminator
            filterStr.deleteCharAt(filterStr.length() - 1);
            filterStr.append('\0');
        }
        filterStr.append('\0');
        return filterStr.toString();
    }

    private File[] chooseDirectory() {
        Ole32.OleInitialize(null);
        Shell32.BrowseInfo params = new Shell32.BrowseInfo();
        params.hwndOwner = Native.getWindowPointer(parent);
        params.ulFlags = Shell32.BIF_RETURNONLYFSDIRS | Shell32.BIF_USENEWUI;
        if(dialogTitle != null) {
            params.lpszTitle = dialogTitle;
        }
        Pointer pidl = Shell32.SHBrowseForFolder(params);
        if(pidl != null) {
            // MAX_PATH is 260 on Windows XP x32 so 4kB should be more than big enough
            Pointer path = new Memory(1024 * 4);
            Shell32.SHGetPathFromIDListW(pidl, path);
            String filePath = path.getWideString(0);
            Ole32.CoTaskMemFree(pidl);
            return new File[] {new File(filePath)};
        }
        return null;
    }

    private File[] chooseFile() {
        Comdlg32.OpenFileName params = new Comdlg32.OpenFileName();
        params.Flags = Comdlg32.OFN_EXPLORER
                       | Comdlg32.OFN_NOCHANGEDIR
                       | Comdlg32.OFN_HIDEREADONLY
                       | Comdlg32.OFN_ENABLESIZING;

        if(parent != null) {
            params.hwndOwner = Native.getWindowPointer(parent);
        }

        params.lpstrFile = new Memory(BUFFER_SIZE);
        params.lpstrFile.clear(BUFFER_SIZE);

        params.nMaxFile = MAX_PATH;

        if(directory != null) {
            params.lpstrInitialDir = directory.getAbsolutePath();
        }

        if(!filters.isEmpty()) { // build filter string if filters were specified
            params.lpstrFilter = new WString(buildFilterString());
            params.nFilterIndex = 1; // TODO: don't hardcode here
        }

        if(this.isMultiSelectionEnabled()) {
            params.Flags |= Comdlg32.OFN_ALLOWMULTISELECT;
        }

        boolean approved = this.isSaveDialog() ? Comdlg32.GetSaveFileNameW(params) : Comdlg32
            .GetOpenFileNameW(params);

        if(approved) {
            String filePath = params.lpstrFile.getWideString(0);
            return new File[] {new File(filePath)};
        } else {
            int errCode = Comdlg32.CommDlgExtendedError();
            if(errCode != 0) {
                throw new RuntimeException("GetOpenFileName failed with error " + errCode);
            } // else user clicked cancel
        }
        return null;
    }

}
