package com.timepath.plaf.mac;

import java.util.logging.Logger;

/**
 * @author TimePath
 */
public class OSXProps {

    private static final Logger LOG = Logger.getLogger(OSXProps.class.getName());

    private OSXProps() {
    }

    public static void setMetal(boolean flag) {
        System.setProperty("apple.awt.brushMetalLook", Boolean.toString(flag));
    }

    public static void setName(String str) {
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", str);
    }

    public static void setShowGrowBox(boolean flag) {
        System.setProperty("apple.awt.showGrowBox", Boolean.toString(flag));
    }

    public static void setGrowBoxIntrudes(boolean flag) {
        System.setProperty("com.apple.mrj.application.growbox.intrudes", Boolean.toString(flag));
    }

    public static void setQuartz(boolean flag) {
        System.setProperty("apple.awt.graphics.EnableQ2DX", Boolean.toString(flag));
    }

    public static void useGlobalMenu(boolean flag) {
        System.setProperty("apple.laf.useScreenMenuBar", Boolean.toString(flag));
    }

    public static void setSmallTabs(boolean flag) {
        System.setProperty("com.apple.macos.smallTabs", Boolean.toString(flag));
    }

    public static void useFileDialogPackages(boolean flag) {
        System.setProperty("com.apple.macos.use-file-dialog-packages", Boolean.toString(flag));
    }

    public static void setLiveResize(boolean flag) {
        System.setProperty("com.apple.mrj.application.live-resize", Boolean.toString(flag));
    }

    public static void setFileDialogDirectoryMode(boolean flag) {
        System.setProperty("apple.awt.fileDialogForDirectories", Boolean.toString(flag));
    }
}
