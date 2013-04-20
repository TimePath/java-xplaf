package com.timepath.nio;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

/**
 *
 * @author timepath
 */
public class BitBuffer {

    private ByteBuffer source;

    private byte b;

    private int left = 0;

    public BitBuffer(ByteBuffer bytes) {
        this.source = bytes;
    }

    public int position() {
        return source.position();
    }

    public boolean ReadBool() {
        return true;
    }

    public float ReadFloat() {
        return 0f;
    }

    private static final int[] masks = new int[]{0, 1, 2, 4, 8, 16, 32, 64, 128};

    public int ReadBits(int bits) {
        int data = 0;
        for(int i = 0; i < bits; i++) {
            if(left == 0) {
                nextByte();
            }
            boolean bool = (b & masks[left]) != 0 ? true : false;
//            System.out.print(bool ? 1 : 0);
            if(bool) {
                data |= masks[left];
            }
            left--;
        }
//        System.out.println();
        return data;
    }

    private void nextByte() {
        int end = source.limit();
        source.limit(source.position() + 1);
        b = source.slice().get();
        source.limit(end);
        left = 8;
    }

    public boolean getBoolean() {
        return ReadBits(1) != 0;
    }

    private static final Logger LOG = Logger.getLogger(BitBuffer.class.getName());

}
