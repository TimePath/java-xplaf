package com.timepath;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author timepath
 */
public class DateUtils {
    
    /**
     * 
     * @param dateStr
     * @return null if parsing failed
     */
    public static String parse(String dateStr) {
        String str = null;
        Calendar cal;
        try {
            cal = DatatypeConverter.parseDateTime(dateStr);
            str = cal.getTime().toString();
        } catch(java.lang.IllegalArgumentException ex) {
        }
        return str;
    }
    
    /**
     * 
     * @param time in seconds since the epoch
     * @return null if parsing failed
     */
    public static String parse(long time) {
        String str = null;
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
//        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        str = df.format(new Date(time * 1000));
        return str;
    }

    private DateUtils() {
    }

    private static final Logger LOG = Logger.getLogger(DateUtils.class.getName());
    
}
