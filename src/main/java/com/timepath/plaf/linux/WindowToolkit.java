package com.timepath.plaf.linux;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TimePath
 */
public class WindowToolkit {

    private static final Logger LOG = Logger.getLogger(WindowToolkit.class.getName());
    private static String windowClass;

    private WindowToolkit() {
    }

    public static String getWindowClass() {
        return windowClass;
    }

    /**
     * Doesn't seem to work all the time
     *
     * @param windowClass
     */
    public static void setWindowClass(String windowClass) {
        WindowToolkit.windowClass = windowClass;
        System.setProperty("jayatana.startupWMClass", windowClass);
        //                boolean force = "Unity".equals(System.getenv("XDG_CURRENT_DESKTOP")); //
        // UBUNTU_MENUPROXY=libappmenu.so
        //                if(force) {
        //                    System.setProperty("jayatana.force", "true");
        //                }
        try {
            Toolkit xToolkit = Toolkit.getDefaultToolkit();
            Field awtAppClassNameField = xToolkit.getClass().getDeclaredField("awtAppClassName");
            awtAppClassNameField.setAccessible(true);
            awtAppClassNameField.set(xToolkit, windowClass);
        } catch (Exception ex) {
            LOG.log(Level.WARNING, null, ex);
        }
    }

    public static long getWindowID(Frame parent) {
        try {
            Method method = Class.forName("sun.awt.X11.XWindow").getDeclaredMethod("getParentWindowID", Component.class);
            method.setAccessible(true);
            return (Long) method.invoke(null, parent.getComponent(0));
        } catch (Exception ex) {
            LOG.log(Level.WARNING, null, ex);
        }
        return 0;
    }
}
