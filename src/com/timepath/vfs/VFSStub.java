package com.timepath.vfs;

import java.io.InputStream;

/**
 *
 * @author timepath
 */
public class VFSStub extends VFile {
    
    public VFSStub() {
        this("");
    }
    
    private String name;
    
    public VFSStub(String name) {
        this.name = name;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public String owner() {
        return "ftp";
    }

    @Override
    public String group() {
        return "ftp";
    }

    @Override
    public long fileSize() {
        return 0;
    }

    @Override
    public long modified() {
        return System.currentTimeMillis();
    }

    @Override
    public String path() {
        return "";
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public InputStream content() {
        return null;
    }
    
}
