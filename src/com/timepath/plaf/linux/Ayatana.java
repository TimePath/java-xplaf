package com.timepath.plaf.linux;

import org.java.ayatana.ApplicationMenu;
import org.java.ayatana.AyatanaDesktop;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TimePath
 */
public class Ayatana {

    private static final Logger LOG = Logger.getLogger(Ayatana.class.getName());

    private Ayatana() {
    }

    public static boolean tryInstallMenu(JFrame frame, JMenuBar jmb) {
        try {
            if(!AyatanaDesktop.isSupported()) {
                LOG.info("Ayatana: Unsupported");
                return false;
            }
            boolean worked = ApplicationMenu.tryInstall(frame, jmb);
            LOG.log(Level.INFO, "Ayatana: {0}", worked);
            if(worked) {
                frame.setJMenuBar(null);
                return true;
            }
        } catch(UnsupportedClassVersionError e) {
            LOG.info("Ayatana: JVM not recent enough");
        } catch(NoClassDefFoundError e) {
            LOG.info("Ayatana: Not found");
            return false;
        }
        LOG.info("Ayatana: Failed");
        return false;
    }
}
