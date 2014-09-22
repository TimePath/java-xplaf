package com.timepath.plaf.x.filechooser;

import com.timepath.plaf.OS;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.logging.Logger;

/**
 * @author TimePath
 */
public class SwingFileChooser extends BaseFileChooser {

    private static final Logger LOG = Logger.getLogger(SwingFileChooser.class.getName());

    public SwingFileChooser() {
    }

    @Override
    public File[] choose() {
        if (OS.isLinux()) {
            //            UIManager.put("FileChooserUI", "eu.kostia.gtkjfilechooser.ui.GtkFileChooserUI");
        }
        JFileChooser fd = new JFileChooser();
        fd.setAcceptAllFileFilterUsed(true);
        for (final ExtensionFilter ef : filters) {
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    for (String e : ef.getExtensions()) {
                        if (f.getName().matches(".+" + e)) {
                            return true;
                        }
                    }
                    return f.isDirectory();
                }

                @Override
                public String getDescription() {
                    StringBuilder filter = new StringBuilder();
                    filter.append(" (*").append(ef.getExtensions().get(0));
                    for (String e : ef.getExtensions().subList(1, ef.getExtensions().size())) {
                        filter.append(", *").append(e);
                    }
                    filter.append(')');
                    return ef.getDescription() + filter;
                }
            };
            fd.addChoosableFileFilter(fileFilter);
            if (fd.isAcceptAllFileFilterUsed()) {
                fd.setAcceptAllFileFilterUsed(false);
                fd.setFileFilter(fileFilter);
            }
        }
        fd.setDialogTitle(dialogTitle);
        fd.setDialogType(isSaveDialog() ? JFileChooser.SAVE_DIALOG : JFileChooser.OPEN_DIALOG);
        if (directory != null) {
            if (fileName != null) {
                fd.setSelectedFile(new File(directory, fileName));
            } else {
                fd.setCurrentDirectory(directory);
            }
        }
        fd.setFileSelectionMode(isDirectoryMode() ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_AND_DIRECTORIES);
        if (!isSaveDialog()) {
            fd.setMultiSelectionEnabled(multiSelectionEnabled);
        }
        File[] selection = null;
        if (fd.showDialog(parent, approveButtonText) == JFileChooser.APPROVE_OPTION) {
            selection = multiSelectionEnabled ? fd.getSelectedFiles() : new File[]{fd.getSelectedFile()};
        }
        return selection;
    }
}
