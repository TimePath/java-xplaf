package com.timepath.io.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 *
 * @author TimePath
 */
public interface Savable {

    public void readExternal(InputStream in);

    public void readExternal(ByteBuffer buf);

    public void writeExternal(OutputStream out);

}
