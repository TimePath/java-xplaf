package com.timepath.plaf.x.filechooser;

import com.timepath.plaf.OS;
import com.timepath.plaf.win.JnaFileChooser;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author TimePath
 */
public class NativeFileChooser extends BaseFileChooser {

    private static final Logger LOG = Logger.getLogger(NativeFileChooser.class.getName());

    public NativeFileChooser() {
    }

    @NotNull
    private static BaseFileChooser which() {
        try {
            if (OS.OBJECT$.isWindows()) {
                return new JnaFileChooser();
            }
//            if (OS.isMac()) {
//                return new AWTFileChooser(); // FIXME
//            }
            if (OS.OBJECT$.isLinux()) {
                String de = System.getenv("XDG_CURRENT_DESKTOP");
                if (de != null) {
                    return "KDE".equalsIgnoreCase(de) ? new KDialogFileChooser() : new ZenityFileChooser();
                }
            }
        } catch (@NotNull final Throwable ignored) {
        }
        return new SwingFileChooser();
    }

    @Nullable
    @Override
    public File[] choose() throws IOException {
        return getChooser().choose();
    }

    @NotNull
    private BaseFileChooser getChooser() {
        @NotNull BaseFileChooser chooser = which();
        chooser.setApproveButtonText(approveButtonText)
                .setTitle(dialogTitle)
                .setDialogType(dialogType)
                .setDirectory(directory)
                .setFileName(fileName)
                .setFileMode(fileMode)
                .setMultiSelectionEnabled(multiSelectionEnabled)
                .setParent(parent);
        for (ExtensionFilter ef : filters) {
            chooser.addFilter(ef);
        }
        return chooser;
    }
}
