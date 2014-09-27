package com.timepath.plaf.x.filechooser;

import com.timepath.plaf.linux.LinuxUtils;
import com.timepath.plaf.linux.WindowToolkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TimePath
 */
public class ZenityFileChooser extends BaseFileChooser {

    private static final Logger LOG = Logger.getLogger(ZenityFileChooser.class.getName());

    public ZenityFileChooser() {
    }

    @Nullable
    @Override
    public File[] choose() throws IOException {
        @NotNull Collection<String> cmd = new LinkedList<>();
        cmd.add("zenity");
        cmd.add("--file-selection");
        @NotNull StringBuilder allDesc = new StringBuilder();
        @NotNull StringBuilder allExt = new StringBuilder();
        @NotNull StringBuilder allExt2 = new StringBuilder();
        for (@NotNull ExtensionFilter ef : filters) {
            allDesc.append(", ").append(ef.getDescription());
            for (String e : ef.getExtensions()) {
                allExt.append(", *").append(e);
                allExt2.append(" *").append(e);
            }
        }
        if (filters.size() > 1) {
            cmd.add("--file-filter=" + allDesc.toString().substring(2) + " (" + allExt.toString().substring(2) +
                    ") | " + allExt2.toString().substring(1));
        }
        for (@NotNull ExtensionFilter ef : filters) {
            @NotNull StringBuilder filter = new StringBuilder();
            filter.append(ef.getDescription());
            filter.append(" (*").append(ef.getExtensions().get(0));
            for (String e : ef.getExtensions().subList(1, ef.getExtensions().size())) {
                filter.append(", *").append(e);
            }
            filter.append(')');
            filter.append(" |");
            for (String e : ef.getExtensions()) {
                filter.append(" *").append(e);
            }
            cmd.add("--file-filter=" + filter);
        }
        if (isDirectoryMode()) {
            cmd.add("--directory");
        }
        if (isSaveDialog()) {
            cmd.add("--save");
            cmd.add("--confirm-overwrite");
        }
        if (isMultiSelectionEnabled()) {
            cmd.add("--multiple");
        }
        if ((fileName != null) || (directory != null)) {
            cmd.add("--filename=" + ((directory != null) ? (directory.getPath() + '/') : "") +
                    ((fileName != null) ? fileName : ""));
        }
        String windowClass = WindowToolkit.getWindowClass();
        try {
            Toolkit xToolkit = Toolkit.getDefaultToolkit();
            Field awtAppClassNameField = xToolkit.getClass().getDeclaredField("awtAppClassName");
            boolean accessible = awtAppClassNameField.isAccessible();
            awtAppClassNameField.setAccessible(true);
            windowClass = (String) awtAppClassNameField.get(xToolkit);
            awtAppClassNameField.setAccessible(accessible);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        cmd.add("--class=" + windowClass);
        //        cmd.add("--name=" + Main.projectName + " ");
        if (WindowToolkit.getWindowClass() != null) {
            cmd.add("--window-icon=" + LinuxUtils.getLinuxStore() + "icons/" + WindowToolkit.getWindowClass() + ".png");
        }
        if ((getTitle() != null) && !getTitle().trim().isEmpty()) {
            cmd.add("--title=" + getTitle());
        }
        if (getApproveButtonText() != null) {
            cmd.add("--ok-label=" + getApproveButtonText());
        }
        //        cmd.add("--cancel-label=TEXT ");
        @NotNull String[] exec = new String[cmd.size()];
        cmd.toArray(exec);
        LOG.log(Level.INFO, "zenity: {0}", Arrays.toString(exec));
        final Process proc = Runtime.getRuntime().exec(exec);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                proc.destroy();
            }
        }));
        @NotNull BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String selection = br.readLine();
        //        String selection = null;
        //        while((selection = br.readLine()) != null) {
        LOG.log(Level.INFO, "Zenity selection: {0}", selection);
        //        }
        if (selection == null) {
            return null;
        } else {
            @NotNull String[] selected = selection.split("\\|");
            @NotNull File[] files = new File[selected.length];
            for (int i = 0; i < files.length; i++) {
                files[i] = new File(selected[i]);
            }
            return files;
        }
    }
}
