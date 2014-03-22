package com.timepath.plaf.win.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 *
 * @author TimePath
 */
public class Ole32 {

    static {
        Native.register("ole32");
    }

    public static native void CoTaskMemFree(Pointer pv);

    public static native Pointer OleInitialize(Pointer pvReserved);

}
