package com.timepath.plaf.x.filechooser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author TimePath
 */
public abstract class BaseFileChooser {

    private static final Logger LOG = Logger.getLogger(BaseFileChooser.class.getName());
    protected Frame parent;
    protected String dialogTitle;
    @Nullable
    protected File directory;
    @NotNull
    protected Collection<ExtensionFilter> filters = new LinkedList<>();
    String fileName;
    String approveButtonText;
    DialogType dialogType = DialogType.OPEN_DIALOG;
    FileMode fileMode = FileMode.FILES_ONLY;
    boolean multiSelectionEnabled;

    protected BaseFileChooser() {
    }

    private BaseFileChooser(File currentDirectory) {
        setDirectory(currentDirectory);
    }

    private BaseFileChooser(String currentDirectoryPath) {
        setDirectory(currentDirectoryPath);
    }

    @NotNull
    BaseFileChooser setDirectory(@Nullable String directoryPath) {
        if (directoryPath == null) {
            directory = null;
        } else {
            setDirectory(new File(directoryPath));
        }
        return this;
    }

    public Frame getParent() {
        return parent;
    }

    @NotNull
    public BaseFileChooser setParent(Frame parent) {
        this.parent = parent;
        return this;
    }

    String getTitle() {
        if (dialogTitle == null) {
            return isSaveDialog() ? "Save" : "Open";
        }
        return dialogTitle;
    }

    @NotNull
    public BaseFileChooser setTitle(String title) {
        dialogTitle = title;
        return this;
    }

    protected boolean isSaveDialog() {
        return dialogType == DialogType.SAVE_DIALOG;
    }

    @Nullable
    public File getDirectory() {
        return directory;
    }

    @NotNull
    public BaseFileChooser setDirectory(File directory) {
        this.directory = directory;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    @NotNull
    public BaseFileChooser setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @NotNull
    public BaseFileChooser setFile(@Nullable File file) {
        if (file != null) {
            setDirectory(file.getParentFile());
            setFileName(file.getName());
        }
        return this;
    }

    String getApproveButtonText() {
        return approveButtonText;
    }

    @NotNull
    BaseFileChooser setApproveButtonText(String approveButtonText) {
        this.approveButtonText = approveButtonText;
        return this;
    }

    public DialogType getDialogType() {
        return dialogType;
    }

    @NotNull
    public BaseFileChooser setDialogType(DialogType dialogType) {
        this.dialogType = dialogType;
        return this;
    }

    protected boolean isDirectoryMode() {
        return fileMode == FileMode.DIRECTORIES_ONLY;
    }

    FileMode getFileMode() {
        return fileMode;
    }

    @NotNull
    public BaseFileChooser setFileMode(FileMode fileMode) {
        this.fileMode = fileMode;
        return this;
    }

    protected boolean isMultiSelectionEnabled() {
        return multiSelectionEnabled;
    }

    @NotNull
    public BaseFileChooser setMultiSelectionEnabled(boolean multiSelectionEnabled) {
        this.multiSelectionEnabled = multiSelectionEnabled;
        return this;
    }

    @Nullable
    public abstract File[] choose() throws IOException;

    @NotNull
    public BaseFileChooser addFilter(ExtensionFilter ef) {
        filters.add(ef);
        return this;
    }

    public enum DialogType {
        SAVE_DIALOG,
        OPEN_DIALOG
    }

    public enum FileMode {
        DIRECTORIES_ONLY,
        FILES_ONLY,
        FILES_AND_DIRECTORIES
    }

    public static class ExtensionFilter {

        @NotNull
        private final List<String> extensions;
        private final String description;

        public ExtensionFilter(String shortDescription, String... extensions) {
            description = shortDescription;
            this.extensions = Arrays.asList(extensions);
        }

        public List<String> getExtensions() {
            return Collections.unmodifiableList(extensions);
        }

        public String getDescription() {
            return description;
        }
    }
}
