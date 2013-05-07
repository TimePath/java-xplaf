package com.timepath.plaf.x.filechooser;

import com.timepath.plaf.OS;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author timepath
 */
public class SwingFileChooser extends BaseFileChooser {

    @Override
    public File[] choose() {
        if(OS.isLinux()) {
//            UIManager.put("FileChooserUI", "eu.kostia.gtkjfilechooser.ui.GtkFileChooserUI");
        }
        JFileChooser fd = new JFileChooser(directory);
        fd.setAcceptAllFileFilterUsed(true);
        for(final ExtensionFilter ef : filters) {
            FileFilter ff = new FileFilter() {
                public boolean accept(File file) {
                    for(String e : ef.getExtensions()) {
                        if(file.getName().matches(".+" + e)) {
                            return true;
                        }
                    }
                    return file.isDirectory();
                }

                public String getDescription() {
                    StringBuilder filter = new StringBuilder();
                    filter.append(" (*").append(ef.getExtensions().get(0));
                    for(String e : ef.getExtensions().subList(1, ef.getExtensions().size())) {
                        filter.append(", *").append(e);
                    }
                    filter.append(")");
                    return ef.getDescription() + filter.toString();
                }
            };
            fd.addChoosableFileFilter(ff);
            if(fd.isAcceptAllFileFilterUsed()) {
                fd.setAcceptAllFileFilterUsed(false);
                fd.setFileFilter(ff);
            }
        }
        fd.setDialogTitle(dialogTitle);
        fd.setDialogType(this.isSaveDialog() ? JFileChooser.SAVE_DIALOG : JFileChooser.OPEN_DIALOG);
        if(directory != null) {
            if(file != null) {
                fd.setSelectedFile(new File(directory, file));
            } else {
                fd.setSelectedFile(directory);
            }
        }
        fd.setFileSelectionMode(this.isDirectoryMode() ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_AND_DIRECTORIES);
        if(!this.isSaveDialog()) {
            fd.setMultiSelectionEnabled(multiSelectionEnabled);
        }
        File[] selection = null;
        if(fd.showDialog(parent, approveButtonText) == JFileChooser.APPROVE_OPTION) {
            if(this.multiSelectionEnabled) {
                selection = fd.getSelectedFiles();
            } else {
                selection = new File[]{fd.getSelectedFile()};
            }
        }
        return selection;
    }

    private static final Logger LOG = Logger.getLogger(SwingFileChooser.class.getName());

}
