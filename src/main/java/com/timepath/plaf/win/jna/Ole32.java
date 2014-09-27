package com.timepath.plaf.win.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.jetbrains.annotations.NotNull;

/**
 * @author TimePath
 */
public class Ole32 {

    static {
        Native.register("ole32");
    }

    private Ole32() {
    }

    public static native void CoTaskMemFree(Pointer pv);

    @NotNull
    public static native Pointer OleInitialize(Pointer pvReserved);
}
