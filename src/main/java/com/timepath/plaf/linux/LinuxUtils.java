package com.timepath.plaf.linux;

import java.util.logging.Logger;

/**
 * @author TimePath
 */
public class LinuxUtils {

    private static final Logger LOG = Logger.getLogger(LinuxUtils.class.getName());

    private LinuxUtils() {
    }

    public static String getLinuxStore() {
        String root = System.getenv("XDG_DATA_HOME");
        if (root == null) {
            root = System.getenv("HOME") + "/.local/share/";
        }
        return root;
    }
}
