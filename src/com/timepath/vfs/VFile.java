package com.timepath.vfs;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TimePath
 */
public abstract class VFile {

    //<editor-fold defaultstate="collapsed" desc="Listener">
    public ArrayList<FileChangeListener> listeners = new ArrayList<FileChangeListener>();

    public void addFileChangeListener(FileChangeListener listener) {
        listeners.add(listener);
    }

    public static abstract class FileChangeListener {

        public abstract void fileAdded(VFile f);

        public abstract void fileModified(VFile f);

        public abstract void fileRemoved(VFile f);

    }
    //</editor-fold>

    public static VFile fromFile(final File f) {
        return new VFile() {
            @Override
            public boolean isDirectory() {
                return f.isDirectory();
            }

            @Override
            public String owner() {
                return "nobody";
            }

            @Override
            public String group() {
                return "nobody";
            }

            @Override
            public long fileSize() {
                return f.length();
            }

            @Override
            public long modified() {
                return f.lastModified();
            }

            @Override
            public String path() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String name() {
                return f.getName();
            }

            @Override
            public InputStream content() {
                try {
                    return new BufferedInputStream(new FileInputStream(f));
                } catch(FileNotFoundException ex) {
                    Logger.getLogger(VFile.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        };
    }

    public static final String sep = "/";

    public HashMap<String, VFile> files = new HashMap<String, VFile>();

    public void add(VFile f) {
        files.put(f.name(), f);
    }

    public void addAll(Collection<VFile> c) {
        for(VFile f : c) {
            add(f);
        }
    }

    public VFile get(String name) {
        String[] split = name.split(sep);
        VFile f = this;
        for(String s : split) {
            if(s.length() == 0) {
                continue;
            }
            f = f.files.get(s);
            if(f == null) {
                return null;
            }
        }
        return f;
    }

    public void remove(String name) {
        files.remove(name);
    }

    public Collection<VFile> list() {
        return files.values();
    }

    public abstract boolean isDirectory();

    public abstract String owner();

    public abstract String group();

    public abstract long fileSize();

    public abstract long modified();

    public abstract String path();

    public abstract String name();

    public abstract InputStream content();

}