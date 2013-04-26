package com.timepath.plaf.x.filechooser;

import com.timepath.plaf.OS;
import java.io.File;
import java.util.logging.Logger;
import net.tomahawk.ExtensionsFilter;
import net.tomahawk.XFileDialog;

/**
 *
 * @author timepath
 */
public class XFileDialogFileChooser extends BaseFileChooser {

    public static void setTraceLevel(int level) {
        if(!OS.isWindows()) {
            return;
        }
        XFileDialog.setTraceLevel(0);
    }

    public File[] choose() {
        String[] selection;
        XFileDialog fd;
        if(parent != null) {
            fd = new XFileDialog(parent);
            fd.setTitle(dialogTitle);
        } else {
            fd = new XFileDialog(dialogTitle);
        }
        for(ExtensionFilter ef : filters) {
            fd.addFilters(new ExtensionsFilter(ef.getDescription(), ef.getExtensions())); // is this broken?
        }
        if(directory != null) {
            fd.setDirectory(directory.getPath());
        }
        boolean multi = false;
        if(this.isDirectoryMode()) {
            if(multi) {
                selection = fd.getFolders();
            } else {
                selection = new String[]{fd.getFolder()};
            }
        } else {
            if(this.isSaveDialog()) {
                selection = new String[]{fd.getSaveFile()};
            } else {
                if(multi) {
                    selection = fd.getFiles();
                } else {
                    selection = new String[]{fd.getFile()};
                }
            }
        }
        fd.dispose();
        if(selection == null) {
            return null;
        } else {
//            if(this.isDirectoryMode()) { 
//                return new File(selection[0]);
//            } else {
            File[] f = new File[selection.length];
            for(int i = 0; i < f.length; i++) {
                f[i] = new File(fd.getDirectory(), selection[i]);
            }
            return f;
//            }
        }
    }

    private static final Logger LOG = Logger.getLogger(XFileDialogFileChooser.class.getName());

}
