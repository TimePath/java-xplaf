package com.timepath.plaf.x.filechooser;

import com.timepath.plaf.OS;
import com.timepath.plaf.mac.OSXProps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.logging.Logger;

/**
 * @author TimePath
 */
public class AWTFileChooser extends BaseFileChooser {

    private static final Logger LOG = Logger.getLogger(AWTFileChooser.class.getName());

    public AWTFileChooser() {
    }

    @Nullable
    @Override
    public File[] choose() {
        if (OS.OBJECT$.isMac()) {
            OSXProps.setFileDialogDirectoryMode(isDirectoryMode());
        }
        @NotNull FileDialog fd = new FileDialog(parent, dialogTitle);
        fd.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(@NotNull File dir, String name) {
                for (@NotNull ExtensionFilter ef : filters) {
                    for (@NotNull String e : ef.getExtensions()) {
                        if (dir.getName().endsWith(e)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        if (directory != null) {
            fd.setDirectory(directory.getPath());
        }
        if (fileName != null) {
            fd.setFile(fileName);
        }
        if (isDirectoryMode()) {
            if (!OS.OBJECT$.isMac()) {
                LOG.warning("Using AWT for directory selection on non mac system - not ideal");
            }
            fd.setFilenameFilter(new FilenameFilter() {
                @Override
                public boolean accept(File dir, @NotNull String name) {
                    return new File(dir, name).isDirectory();
                }
            });
        }
        fd.setMode(isSaveDialog() ? FileDialog.SAVE : FileDialog.LOAD);
        fd.setVisible(true);
        if ((fd.getDirectory() == null) || (fd.getFile() == null)) { // cancelled
            return null;
        }
        return new File[]{new File(fd.getDirectory() + File.pathSeparator + fd.getFile())};
    }
}
