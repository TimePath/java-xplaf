package com.timepath.plaf.x.filechooser


import java.io.File
import java.io.IOException
import java.util.*
import java.util.logging.Logger
import java.awt.Frame

/**
 * @author TimePath
 */
public abstract class BaseFileChooser {
    public var parent: Frame? = null
        protected set
    protected var dialogTitle: String? = null
    public var directory: File? = null
        protected set
    protected var filters: MutableCollection<ExtensionFilter> = LinkedList()
    public var fileName: String? = null
        set
    var approveButtonText: String? = null
    public var dialogType: DialogType = DialogType.OPEN_DIALOG
        set
    var fileMode = FileMode.FILES_ONLY
    var multiSelectionEnabled: Boolean = false

    fun setDirectory(directoryPath: String?): BaseFileChooser {
        if (directoryPath == null) {
            directory = null
        } else {
            setDirectory(File(directoryPath))
        }
        return this
    }

    public fun setParent(parent: Frame?): BaseFileChooser {
        this.parent = parent
        return this
    }

    fun getTitle(): String {
        if (dialogTitle == null) {
            return if (isSaveDialog()) "Save" else "Open"
        }
        return dialogTitle!!
    }

    public fun setTitle(title: String?): BaseFileChooser {
        dialogTitle = title
        return this
    }

    protected fun isSaveDialog(): Boolean {
        return dialogType == DialogType.SAVE_DIALOG
    }

    public fun setDirectory(directory: File?): BaseFileChooser {
        this.directory = directory
        return this
    }

    public fun setFileName(fileName: String?): BaseFileChooser {
        this.fileName = fileName
        return this
    }

    public fun setFile(file: File?): BaseFileChooser {
        if (file != null) {
            setDirectory(file.getParentFile())
            setFileName(file.getName())
        }
        return this
    }

    fun setApproveButtonText(approveButtonText: String?): BaseFileChooser {
        this.approveButtonText = approveButtonText
        return this
    }

    public fun setDialogType(dialogType: DialogType): BaseFileChooser {
        this.dialogType = dialogType
        return this
    }

    protected fun isDirectoryMode(): Boolean {
        return fileMode == FileMode.DIRECTORIES_ONLY
    }

    public fun setFileMode(fileMode: FileMode): BaseFileChooser {
        this.fileMode = fileMode
        return this
    }

    protected fun isMultiSelectionEnabled(): Boolean {
        return multiSelectionEnabled
    }

    public fun setMultiSelectionEnabled(multiSelectionEnabled: Boolean): BaseFileChooser {
        this.multiSelectionEnabled = multiSelectionEnabled
        return this
    }

    throws(javaClass<IOException>())
    public abstract fun choose(): Array<File>?

    public fun addFilter(ef: ExtensionFilter): BaseFileChooser {
        filters.add(ef)
        return this
    }

    public enum class DialogType {
        SAVE_DIALOG
        OPEN_DIALOG
    }

    public enum class FileMode {
        DIRECTORIES_ONLY
        FILES_ONLY
        FILES_AND_DIRECTORIES
    }

    public class ExtensionFilter(public val description: String, vararg extensions: String) {

        private val extensions: List<String>

        {
            this.extensions = Arrays.asList<String>(*extensions)
        }

        public fun getExtensions(): List<String> {
            return Collections.unmodifiableList<String>(extensions)
        }
    }

    class object {

        private val LOG = Logger.getLogger(javaClass<BaseFileChooser>().getName())
    }
}
