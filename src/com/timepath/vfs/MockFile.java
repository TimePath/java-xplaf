package com.timepath.vfs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 *
 * @author TimePath
 */
public class MockFile extends VFile {

    private String name, cont;

    public MockFile(String name, String cont) {
        this.name = name;
        this.cont = cont;
    }

    @Override
    public boolean isDirectory() {
        return cont == null;
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
        return cont != null ? cont.getBytes().length : this.files.size();
    }

    @Override
    public long modified() {
        return System.currentTimeMillis();
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public InputStream content() {
        return new ByteArrayInputStream(cont.getBytes());
    }

    @Override
    public String path() {
        return "";
    }

}
