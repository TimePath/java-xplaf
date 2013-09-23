package com.timepath.plaf.x.filechooser;

import com.timepath.plaf.OS;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author TimePath
 */
public class NativeFileChooser extends BaseFileChooser {

    private static final Logger LOG = Logger.getLogger(NativeFileChooser.class.getName());

    private BaseFileChooser getChooser() {
        BaseFileChooser chooser;
        if(OS.isWindows()) {
            chooser = new XFileDialogFileChooser();
        } else if(OS.isMac()) {
            chooser = new AWTFileChooser();
        } else if(OS.isLinux()) {
//            if(System.getProperties().getProperty("java.class.path").contains("NetBeansProjects")) {
//                LOG.warning("Running from NetBeans, ZenityFileChooser may not work");
//                chooser = new SwingFileChooser();
//            } else {
//            try {
                chooser = new ZenityFileChooser();
//            } catch(IOException ex) {
//                chooser = new SwingFileChooser();  
//            }
//            }
        } else {
            chooser = new SwingFileChooser();
        }
        chooser
                .setApproveButtonText(approveButtonText)
                .setTitle(dialogTitle)
                .setDialogType(dialogType)
                .setDirectory(directory)
                .setFile(file)
                .setFileMode(fileMode)
                .setMultiSelectionEnabled(multiSelectionEnabled)
                .setParent(parent);
        for(ExtensionFilter ef : filters) {
            chooser.addFilter(ef);
        }

        return chooser;
    }

    @Override
    public File[] choose() throws IOException {
        return getChooser().choose();
    }

}
