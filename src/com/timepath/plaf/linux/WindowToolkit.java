package com.timepath.plaf.linux;

import java.awt.Toolkit;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TimePath
 */
public class WindowToolkit {

    private static String windowClass;

    public static String getWindowClass() {
        return WindowToolkit.windowClass;
    }

    /**
     * Doesn't seem to work all the time
     *
     * @param windowClass
     */
    public static void setWindowClass(String windowClass) {
        WindowToolkit.windowClass = windowClass;

        System.setProperty("jayatana.startupWMClass", windowClass);
//                boolean force = "Unity".equals(System.getenv("XDG_CURRENT_DESKTOP")); // UBUNTU_MENUPROXY=libappmenu.so
//                if(force) {
//                    System.setProperty("jayatana.force", "true");
//                }
        try {
            Toolkit xToolkit = Toolkit.getDefaultToolkit();
            Field awtAppClassNameField = xToolkit.getClass().getDeclaredField("awtAppClassName");
            awtAppClassNameField.setAccessible(true);
            awtAppClassNameField.set(xToolkit, windowClass);
        } catch(Exception ex) {
            LOG.log(Level.WARNING, null, ex);
        }
    }

    private WindowToolkit() {
    }

    private static final Logger LOG = Logger.getLogger(WindowToolkit.class.getName());

}