package com.timepath.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author timepath
 */
public class AggregateOutputStream extends OutputStream {

    public AggregateOutputStream() {
    }

    private final ArrayList<OutputStream> out = new ArrayList<OutputStream>();

    public synchronized void register(OutputStream outputStream) {
        out.add(outputStream);
    }

    public synchronized void deregister(OutputStream outputStream) {
        out.remove(outputStream);
    }

    @Override
    public void write(int b) throws IOException {
        ArrayList<OutputStream> dereg = new ArrayList<OutputStream>();
        synchronized(out) {
            for(OutputStream os : out) {
                try {
                    os.write(b);
                } catch(Exception e) {
                    dereg.add(os);
                }
            }
            out.removeAll(dereg);
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        ArrayList<OutputStream> dereg = new ArrayList<OutputStream>();
        synchronized(out) {
            for(OutputStream os : out) {
                try {
                    os.write(b, off, len);
                } catch(Exception e) {
                    dereg.add(os);
                }
            }
            out.removeAll(dereg);
        }
    }
    
    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

}
