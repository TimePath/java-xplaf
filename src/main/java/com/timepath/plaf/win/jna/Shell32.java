package com.timepath.plaf.win.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author TimePath
 */
public class Shell32 {

    // flags for the BrowseInfo structure
    public static final int BIF_BROWSEFILEJUNCTIONS = 0x00010000;
    public static final int BIF_BROWSEINCLUDEFILES = 0x00004000;
    public static final int BIF_DONTGOBELOWDOMAIN = 0x00000002;
    public static final int BIF_NONEWFOLDERBUTTON = 0x00000200;
    /**
     * Disable the OK button if the user selects a virtual PIDL
     */
    public static final int BIF_RETURNONLYFSDIRS = 0x00000001;
    public static final int BIF_SHAREABLE = 0x00008000;
    private static final int BIF_EDITBOX = 0x00000010;
    private static final int BIF_NEWDIALOGSTYLE = 0x00000040;
    /**
     * Only available as of Windows 2000/Me (Shell32.dll 5.0)
     */
    public static final int BIF_USENEWUI = BIF_EDITBOX | BIF_NEWDIALOGSTYLE;

    static {
        Native.register("shell32");
    }

    private Shell32() {
    }

    @Nullable
    public static native Pointer SHBrowseForFolder(BrowseInfo params);

    public static native boolean SHGetPathFromIDListW(Pointer pidl, Pointer path);

    public static class BrowseInfo extends Structure {

        public Pointer hwndOwner;
        public int iImage;
        public Pointer lParam;
        public Pointer lpfn;
        public String lpszTitle;
        public Pointer pidlRoot;
        public String pszDisplayName;
        public int ulFlags;

        public BrowseInfo() {
        }

        @NotNull
        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("hwndOwner", "pidlRoot", "pszDisplayName", "lpszTitle", "ulFlags", "lpfn", "lParam", "iImage");
        }
    }
}
