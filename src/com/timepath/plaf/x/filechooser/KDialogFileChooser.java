package com.timepath.plaf.x.filechooser;

import com.timepath.plaf.linux.WindowToolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TimePath
 */
public class KDialogFileChooser extends BaseFileChooser {

    private static final Logger LOG = Logger.getLogger(KDialogFileChooser.class.getName());

    private boolean wasDisabled;

    private boolean wasEnabled;

    @Override
    public File[] choose() throws IOException {
        ArrayList<String> cmd = new ArrayList<String>();
        cmd.add("kdialog");
        if(this.isMultiSelectionEnabled()) {
            cmd.add("--multiple");
            cmd.add("--separate-output");
        }
        if(this.isDirectoryMode()) {
            cmd.add("--getexistingdirectory");
        } else {
            if(this.isSaveDialog()) {
                cmd.add("--getsavefilename");
            } else {
                cmd.add("--getopenfilename");
            }
        }
        if(file != null || directory != null) {
            cmd.add((directory != null ? (directory.getPath() + "/") : "") + (file != null ? file
                                                                              : ""));
        } else {
            cmd.add("~");
        }

        StringBuilder sb = new StringBuilder();

        if(filters.size() > 1) {
            sb.append("*.*|All supported files\n");
        }
        int fnum = 0;
        for(ExtensionFilter ef : filters) {
            List<String> exts = ef.getExtensions();
            StringBuilder part = new StringBuilder(exts.size() * 6 + ef.getDescription().length());
            for(String e : exts) {
                if(part.length() > 0) {
                    part.append(' ');
                }
                part.append('*').append(e);
            }
            part.append('|').append(ef.getDescription());
            sb.append(part);
            if(++fnum < filters.size()) {
                sb.append('\n');
            }
        }
        cmd.add(sb.toString());

        if(parent != null) {
            long wid = WindowToolkit.getWindowID(parent);
            if(wid != 0) {
                cmd.add("--attach");
                cmd.add("" + wid);
                wasEnabled = parent.isEnabled();
                wasDisabled = true;
                parent.setEnabled(false);
            }
        }

        if(this.getTitle() != null && this.getTitle().trim().length() > 0) {
            cmd.add("--title=" + this.getTitle());
        }
        if(this.getApproveButtonText() != null) {
            cmd.add("--yes-label=" + this.getApproveButtonText());
        }

        final String[] exec = new String[cmd.size()];
        cmd.toArray(exec);
        LOG.log(Level.INFO, "kdialog: {0}", Arrays.toString(exec));
        final Process proc = Runtime.getRuntime().exec(exec);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                proc.destroy();
            }
        });
        ArrayList<String> selected = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String selection;
        while((selection = br.readLine()) != null) {
            selected.add(selection);
        }
        LOG.log(Level.INFO, "KDialog selection: {0}", selection);
        if(wasEnabled && wasDisabled) {
            parent.setEnabled(wasDisabled);
        }
        if(selected.isEmpty()) {
            return null;
        } else {
            File[] f = new File[selected.size()];
            int i = 0;
            for(String s : selected) {
                f[i++] = new File(s);
            }
            return f;
        }
    }

}
