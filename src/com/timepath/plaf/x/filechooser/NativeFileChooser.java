package com.timepath.plaf.x.filechooser;

import com.timepath.plaf.OS;
import com.timepath.plaf.win.JnaFileChooser;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author TimePath
 */
public class NativeFileChooser extends BaseFileChooser {

    private static final Logger LOG = Logger.getLogger(NativeFileChooser.class.getName());

    @Override
    public File[] choose() throws IOException {
        return getChooser().choose();
    }

    private BaseFileChooser getChooser() {
        BaseFileChooser chooser = which();
        chooser.setApproveButtonText(approveButtonText)
               .setTitle(dialogTitle)
               .setDialogType(dialogType)
               .setDirectory(directory)
               .setFile(file)
               .setFileMode(fileMode)
               .setMultiSelectionEnabled(multiSelectionEnabled)
               .setParent(parent);
        for(ExtensionFilter ef : filters) {
            chooser.addFilter(ef);
        }
        return chooser;
    }

    private static BaseFileChooser which() {
        if(OS.isWindows()) {
            return new JnaFileChooser(); // XFileDialogFileChooser();
        }
        if(OS.isMac()) {
            return new AWTFileChooser();
        }
        if(OS.isLinux()) {
            String de = System.getenv("XDG_CURRENT_DESKTOP");
            if(de != null) {
                return "KDE".equalsIgnoreCase(de) ? new KDialogFileChooser() : new ZenityFileChooser();
            }
        }
        return new SwingFileChooser();
    }
}
