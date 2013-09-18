package com.timepath.vfs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author timepath
 */
public abstract class VFile implements Comparable<VFile> {

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
    
    public static final String sep = "/";

    public HashMap<String, VFile> files = new HashMap<String, VFile>();

    public void add(VFile f) {
        files.put(f.name(), f);
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

    public abstract int itemSize();

    public abstract String owner();

    public abstract String group();

    public abstract long fileSize();

    public abstract long modified();

    public abstract String path();

    public abstract String name();

    public abstract InputStream content();

    public int compareTo(VFile o) {
        return name().compareTo(o.name());
    }

}