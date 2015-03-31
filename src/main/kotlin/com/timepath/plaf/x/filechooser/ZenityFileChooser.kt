package com.timepath.plaf.x.filechooser

import com.timepath.plaf.linux.LinuxUtils
import com.timepath.plaf.linux.WindowToolkit
import java.awt.Toolkit
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.Arrays
import java.util.LinkedList
import java.util.logging.Level
import java.util.logging.Logger

/**
 * @author TimePath
 */
public class ZenityFileChooser : BaseFileChooser() {

    throws(javaClass<IOException>())
    override fun choose(): Array<File>? {
        val cmd = LinkedList<String>()
        cmd.add("zenity")
        cmd.add("--file-selection")
        val allDesc = StringBuilder()
        val allExt = StringBuilder()
        val allExt2 = StringBuilder()
        for (ef in filters) {
            allDesc.append(", ").append(ef.description)
            for (e in ef.getExtensions()) {
                allExt.append(", *").append(e)
                allExt2.append(" *").append(e)
            }
        }
        if (filters.size() > 1) {
            cmd.add("--file-filter=" + allDesc.toString().substring(2) + " (" + allExt.toString().substring(2) + ") | " + allExt2.toString().substring(1))
        }
        for (ef in filters) {
            val filter = StringBuilder()
            filter.append(ef.description)
            filter.append(" (*").append(ef.getExtensions().get(0))
            for (e in ef.getExtensions().subList(1, ef.getExtensions().size())) {
                filter.append(", *").append(e)
            }
            filter.append(')')
            filter.append(" |")
            for (e in ef.getExtensions()) {
                filter.append(" *").append(e)
            }
            cmd.add("--file-filter=" + filter)
        }
        if (isDirectoryMode()) {
            cmd.add("--directory")
        }
        if (isSaveDialog()) {
            cmd.add("--save")
            cmd.add("--confirm-overwrite")
        }
        if (isMultiSelectionEnabled()) {
            cmd.add("--multiple")
        }
        if ((fileName != null) || (directory != null)) {
            cmd.add("--filename=" + (if ((directory != null)) (directory!!.getPath() + '/') else "") + (if ((fileName != null)) fileName else ""))
        }
        var windowClass = WindowToolkit.getWindowClass()
        try {
            val xToolkit = Toolkit.getDefaultToolkit()
            val awtAppClassNameField = xToolkit.javaClass.getDeclaredField("awtAppClassName")
            val accessible = awtAppClassNameField.isAccessible()
            awtAppClassNameField.setAccessible(true)
            windowClass = awtAppClassNameField.get(xToolkit) as String
            awtAppClassNameField.setAccessible(accessible)
        } catch (ex: Exception) {
            LOG.log(Level.SEVERE, null, ex)
        }

        cmd.add("--class=" + windowClass)
        //        cmd.add("--name=" + Main.projectName + " ");
        if (WindowToolkit.getWindowClass() != null) {
            cmd.add("--window-icon=" + LinuxUtils.getLinuxStore() + "icons/" + WindowToolkit.getWindowClass() + ".png")
        }
        if (getTitle().trim().isNotEmpty()) {
            cmd.add("--title=" + getTitle())
        }
        if (approveButtonText != null) {
            cmd.add("--ok-label=" + approveButtonText)
        }
        //        cmd.add("--cancel-label=TEXT ");
        val exec = arrayOfNulls<String>(cmd.size())
        cmd.toArray<String>(exec)
        LOG.log(Level.INFO, "zenity: {0}", Arrays.toString(exec))
        val proc = Runtime.getRuntime().exec(exec)
        Runtime.getRuntime().addShutdownHook(Thread(object : Runnable {
            override fun run() {
                proc.destroy()
            }
        }))
        val br = BufferedReader(InputStreamReader(proc.getInputStream()))
        val selection = br.readLine()
        //        String selection = null;
        //        while((selection = br.readLine()) != null) {
        LOG.log(Level.INFO, "Zenity selection: {0}", selection)
        //        }
        if (selection == null) {
            return null
        } else {
            return selection.split("\\|").map { File(it) }.copyToArray()
        }
    }

    companion object {

        private val LOG = Logger.getLogger(javaClass<ZenityFileChooser>().getName())
    }
}
