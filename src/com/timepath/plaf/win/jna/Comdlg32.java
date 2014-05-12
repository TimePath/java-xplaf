package com.timepath.plaf.win.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;

import java.util.Arrays;
import java.util.List;

/**
 * @author TimePath
 */
public class Comdlg32 {

    // error codes from cderr.h which may be returned by
    // CommDlgExtendedError for the GetOpenFileName and
    public static final int CDERR_DIALOGFAILURE      = 0xFFFF;
    public static final int CDERR_FINDRESFAILURE     = 0x0006;
    public static final int CDERR_INITIALIZATION     = 0x0002;
    public static final int CDERR_LOADRESFAILURE     = 0x0007;
    public static final int CDERR_LOADSTRFAILURE     = 0x0005;
    public static final int CDERR_LOCKRESFAILURE     = 0x0008;
    public static final int CDERR_MEMALLOCFAILURE    = 0x0009;
    public static final int CDERR_MEMLOCKFAILURE     = 0x000A;
    public static final int CDERR_NOHINSTANCE        = 0x0004;
    public static final int CDERR_NOHOOK             = 0x000B;
    public static final int CDERR_NOTEMPLATE         = 0x0003;
    public static final int CDERR_STRUCTSIZE         = 0x0001;
    public static final int FNERR_BUFFERTOOSMALL     = 0x3003;
    public static final int FNERR_INVALIDFILENAME    = 0x3002;
    public static final int FNERR_SUBCLASSFAILURE    = 0x3001;
    // flags for the OpenFileName structure
    public static final int OFN_ALLOWMULTISELECT     = 0x00000200;
    public static final int OFN_CREATEPROMPT         = 0x00002000;
    public static final int OFN_DONTADDTORECENT      = 0x02000000;
    public static final int OFN_ENABLEHOOK           = 0x00000020;
    public static final int OFN_ENABLEINCLUDENOTIFY  = 0x00400000;
    /**
     * Enable resizing of the dialog
     */
    public static final int OFN_ENABLESIZING         = 0x00800000;
    public static final int OFN_ENABLETEMPLATE       = 0x00000040;
    public static final int OFN_ENABLETEMPLATEHANDLE = 0x00000080;
    /**
     * Use explorer-style interface
     */
    public static final int OFN_EXPLORER             = 0x00080000;
    public static final int OFN_EXTENSIONDIFFERENT   = 0x00000400;
    public static final int OFN_FILEMUSTEXIST        = 0x00001000;
    public static final int OFN_FORCESHOWHIDDEN      = 0x10000000;
    /**
     * Disable "open as read-only" feature
     */
    public static final int OFN_HIDEREADONLY         = 0x00000004;
    public static final int OFN_LONGNAMES            = 0x00200000;
    /**
     * The dialog changes the current directory when browsing,
     * this flag causes the original value to be restored after the dialog returns
     */
    public static final int OFN_NOCHANGEDIR          = 0x00000008;
    public static final int OFN_NODEREFERENCELINKS   = 0x00100000;
    public static final int OFN_NOLONGNAMES          = 0x00040000;
    public static final int OFN_NONETWORKBUTTON      = 0x00020000;
    public static final int OFN_NOREADONLYRETURN     = 0x00008000;
    public static final int OFN_NOTESTFILECREATE     = 0x00010000;
    public static final int OFN_NOVALIDATE           = 0x00000100;
    public static final int OFN_OVERWRITEPROMPT      = 0x00000002;
    public static final int OFN_PATHMUSTEXIST        = 0x00000800;
    public static final int OFN_READONLY             = 0x00000001;
    public static final int OFN_SHAREAWARE           = 0x00004000;
    public static final int OFN_SHOWHELP             = 0x00000010;

    static {
        Native.register("comdlg32");
    }

    public static native int CommDlgExtendedError();

    public static native boolean GetOpenFileNameW(OpenFileName params);

    public static native boolean GetSaveFileNameW(OpenFileName params);

    public static class OpenFileName extends Structure {

        public int     Flags;
        public Pointer hInstance;
        public Pointer hwndOwner;
        public Pointer lCustData;
        public int     lStructSize;
        public Pointer lpTemplateName;
        public Pointer lpfnHook;
        public WString lpstrCustomFilter;
        public String  lpstrDefExt;
        public Pointer lpstrFile;
        public String  lpstrFileTitle;
        public WString lpstrFilter;
        public String  lpstrInitialDir;
        public String  lpstrTitle;
        public short   nFileExtension;
        public short   nFileOffset;
        public int     nFilterIndex;
        public int     nMaxCustFilter;
        /**
         * http://msdn.microsoft.com/en-us/library/ms646839.aspx:
         * "The size, in characters, of the buffer pointed to by lpstrFile.
         * The buffer must be large enough to store the path and file name string or strings,
         * including the terminating NULL character."
         * Therefore because we're using the unicode version of the API
         * the nMaxFile value must be 1/4 of the lpstrFile buffer size plus one
         * for the terminating null byte.
         */
        public int     nMaxFile;
        public int     nMaxFileTitle;

        public OpenFileName() {
            lStructSize = size();
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("lStructSize",
                                 "hwndOwner",
                                 "hInstance",
                                 "lpstrFilter",
                                 "lpstrCustomFilter",
                                 "nMaxCustFilter",
                                 "nFilterIndex",
                                 "lpstrFile",
                                 "nMaxFile",
                                 "lpstrFileTitle",
                                 "nMaxFileTitle",
                                 "lpstrInitialDir",
                                 "lpstrTitle",
                                 "Flags",
                                 "nFileOffset",
                                 "nFileExtension",
                                 "lpstrDefExt",
                                 "lCustData",
                                 "lpfnHook",
                                 "lpTemplateName");
        }
    }
}
