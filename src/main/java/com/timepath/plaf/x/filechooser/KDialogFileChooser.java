package com.timepath.plaf.x.filechooser;

import com.timepath.plaf.linux.WindowToolkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TimePath
 */
public class KDialogFileChooser extends BaseFileChooser {

    private static final Logger LOG = Logger.getLogger(KDialogFileChooser.class.getName());
    private boolean wasDisabled;
    private boolean wasEnabled;

    public KDialogFileChooser() {
    }

    @Override
    public File[] choose() throws IOException {
        Collection<String> cmd = new LinkedList<>();
        cmd.add("kdialog");
        if (isMultiSelectionEnabled()) {
            cmd.add("--multiple");
            cmd.add("--separate-output");
        }
        if (isDirectoryMode()) {
            cmd.add("--getexistingdirectory");
        } else {
            if (isSaveDialog()) {
                cmd.add("--getsavefilename");
            } else {
                cmd.add("--getopenfilename");
            }
        }
        if ((fileName != null) || (directory != null)) {
            cmd.add(((directory != null) ? (directory.getPath() + '/') : "") + ((fileName != null) ? fileName : ""));
        } else {
            cmd.add("~");
        }
        StringBuilder sb = new StringBuilder();
        if (filters.size() > 1) {
            sb.append("*.*|All supported files\n");
        }
        int fnum = 0;
        for (ExtensionFilter ef : filters) {
            List<String> exts = ef.getExtensions();
            StringBuilder part = new StringBuilder((exts.size() * 6) + ef.getDescription().length());
            for (String e : exts) {
                if (part.length() > 0) {
                    part.append(' ');
                }
                part.append('*').append(e);
            }
            part.append('|').append(ef.getDescription());
            sb.append(part);
            if (++fnum < filters.size()) {
                sb.append('\n');
            }
        }
        cmd.add(sb.toString());
        if (parent != null) {
            long wid = WindowToolkit.getWindowID(parent);
            if (wid != 0) {
                cmd.add("--attach");
                cmd.add(String.valueOf(wid));
                wasEnabled = parent.isEnabled();
                wasDisabled = true;
                parent.setEnabled(false);
            }
        }
        if ((getTitle() != null) && !getTitle().trim().isEmpty()) {
            cmd.add("--title=" + getTitle());
        }
        if (getApproveButtonText() != null) {
            cmd.add("--yes-label=" + getApproveButtonText());
        }
        String[] exec = new String[cmd.size()];
        cmd.toArray(exec);
        LOG.log(Level.INFO, "kdialog: {0}", Arrays.toString(exec));
        final Process proc = Runtime.getRuntime().exec(exec);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                proc.destroy();
            }
        }));
        Collection<String> selected = new LinkedList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String selection;
        while ((selection = br.readLine()) != null) {
            selected.add(selection);
        }
        LOG.log(Level.INFO, "KDialog selection: {0}", selected);
        if (wasEnabled && wasDisabled) {
            parent.setEnabled(wasDisabled);
        }
        if (selected.isEmpty()) {
            return null;
        } else {
            File[] files = new File[selected.size()];
            int i = 0;
            for (String s : selected) {
                files[i++] = new File(s);
            }
            return files;
        }
    }
}
