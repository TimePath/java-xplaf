package com.timepath.plaf.x.filechooser;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author timepath
 */
public abstract class BaseFileChooser {

    private static final Logger LOG = Logger.getLogger(BaseFileChooser.class.getName());

    protected Frame parent;

    public Frame getParent() {
        return parent;
    }

    public BaseFileChooser setParent(Frame parent) {
        this.parent = parent;
        return this;
    }

    protected String dialogTitle;

    public String getTitle() {
        if(dialogTitle == null) {
            return this.isSaveDialog() ? "Save" : "Open";
        }
        return dialogTitle;
    }

    public BaseFileChooser setTitle(String title) {
        this.dialogTitle = title;
        return this;
    }

    protected File directory;

    public File getDirectory() {
        return directory;
    }

    public BaseFileChooser setDirectory(File directory) {
        this.directory = directory;
        return this;
    }
    
    public BaseFileChooser setDirectory(String directoryPath) {
        if(directoryPath == null) {
            this.directory = null;
        } else {
            setDirectory(new File(directoryPath));
        }
        return this;
    }
    
    protected String file;

    public BaseFileChooser setFile(String file) {
        this.file = file;
        return this;
    }

    public String getFile() {
        return file;
    }

    public BaseFileChooser setFile(File file) {
        if(file != null) {
            setDirectory(file.getParentFile());
            setFile(file.getName());
        }
        return this;
    }
    
    protected String approveButtonText;

    public String getApproveButtonText() {
        return approveButtonText;
    }

    public BaseFileChooser setApproveButtonText(String approveButtonText) {
        this.approveButtonText = approveButtonText;
        return this;
    }
    
    public static enum DialogType {
        SAVE_DIALOG, OPEN_DIALOG
    }
    
    protected DialogType dialogType = DialogType.OPEN_DIALOG;

    public DialogType getDialogType() {
        return dialogType;
    }

    public BaseFileChooser setDialogType(DialogType dialogType) {
        this.dialogType = dialogType;
        return this;
    }
    
    public boolean isSaveDialog() {
        return dialogType == DialogType.SAVE_DIALOG;
    }
    
    public static enum FileMode {
        DIRECTORIES_ONLY, FILES_ONLY, FILES_AND_DIRECTORIES
    }
    
    protected FileMode fileMode = FileMode.FILES_ONLY;

    public FileMode getFileMode() {
        return fileMode;
    }

    public BaseFileChooser setFileMode(FileMode fileMode) {
        this.fileMode = fileMode;
        return this;
    }
    
    public boolean isDirectoryMode() {
        return this.getFileMode() == FileMode.DIRECTORIES_ONLY;
    }
    
    protected boolean multiSelectionEnabled;
    
    public boolean isMultiSelectionEnabled() {
        return multiSelectionEnabled;
    }
    
    public BaseFileChooser setMultiSelectionEnabled(boolean multiSelectionEnabled) {
        this.multiSelectionEnabled = multiSelectionEnabled;
        return this;
    }
    
    public BaseFileChooser() {
        
    }
    
    public BaseFileChooser(File currentDirectory) {
        setDirectory(currentDirectory);
    }
    
    public BaseFileChooser(String currentDirectoryPath) {
        setDirectory(currentDirectoryPath);
    }

    public abstract File[] choose() throws IOException;
    
    protected ArrayList<ExtensionFilter> filters = new ArrayList<ExtensionFilter>();
    
    public BaseFileChooser addFilter(ExtensionFilter ef) {
        filters.add(ef);
        return this;
    }
    
    public static class ExtensionFilter {
        
        public ExtensionFilter(String shortDescription, String... extensions) {
            this.description = shortDescription;
            this.extensions = Arrays.asList(extensions);
        }

        private final List<String> extensions;

        public List<String> getExtensions() {
            return extensions;
        }

        private final String description;

        public String getDescription() {
            return description;
        }
        
    }
    
}
