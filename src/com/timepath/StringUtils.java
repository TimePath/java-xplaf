package com.timepath;

import java.util.logging.Logger;

/**
 *
 * @author timepath
 */
public class StringUtils {

    public static String capitalize(String str) {
        if(str == null) {
            return null;
        }
        if(str.length() == 0) {
            return "";
        }
        return Character.toUpperCase(str.charAt(0)) + str.length() > 1 ? str.substring(1).toLowerCase() : "";
    }

    private StringUtils() {
    }

    private static final Logger LOG = Logger.getLogger(StringUtils.class.getName());
}
