/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timepath.io.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 *
 * @author timepath
 */
public interface Savable {

    public void readExternal(InputStream in);

    public void readExternal(ByteBuffer buf);

    public void writeExternal(OutputStream out);

}
