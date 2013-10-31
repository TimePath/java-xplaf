package com.timepath.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author TimePath
 */
public class AggregateOutputStream extends OutputStream {

    public AggregateOutputStream() {
    }

    private final ArrayList<OutputStream> out = new ArrayList<OutputStream>();

    public void register(OutputStream outputStream) {
        synchronized(out) {
            out.add(outputStream);
        }
    }

    public void deregister(OutputStream outputStream) {
        synchronized(out) {
            out.remove(outputStream);
        }
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
        ArrayList<OutputStream> streams = new ArrayList<OutputStream>();
        ArrayList<OutputStream> dereg = new ArrayList<OutputStream>();
        synchronized(out) {
            streams.addAll(out);
        }
        for(OutputStream os : streams) {
            try {
                os.write(b, off, len);
            } catch(Exception e) {
                dereg.add(os);
            }
        }
        if(!dereg.isEmpty()) {
            synchronized(out) {
                out.removeAll(dereg);
            }
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

}
