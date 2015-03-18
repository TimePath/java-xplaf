package com.timepath.plaf.x.filechooser

import com.timepath.plaf.linux.WindowToolkit

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
public class KDialogFileChooser : BaseFileChooser() {
    private var wasDisabled: Boolean = false
    private var wasEnabled: Boolean = false

    throws(javaClass<IOException>())
    override fun choose(): Array<File>? {
        val cmd = LinkedList<String>()
        cmd.add("kdialog")
        if (isMultiSelectionEnabled()) {
            cmd.add("--multiple")
            cmd.add("--separate-output")
        }
        if (isDirectoryMode()) {
            cmd.add("--getexistingdirectory")
        } else {
            if (isSaveDialog()) {
                cmd.add("--getsavefilename")
            } else {
                cmd.add("--getopenfilename")
            }
        }
        if ((fileName != null) || (directory != null)) {
            cmd.add((if ((directory != null)) (directory!!.getPath() + '/') else "") + (if ((fileName != null)) fileName else ""))
        } else {
            cmd.add("~")
        }
        val sb = StringBuilder()
        if (filters.size() > 1) {
            sb.append("*.*|All supported files\n")
        }
        var fnum = 0
        for (ef in filters) {
            val exts = ef.getExtensions()
            val part = StringBuilder((exts.size() * 6) + ef.description.length())
            for (e in exts) {
                if (part.length() > 0) {
                    part.append(' ')
                }
                part.append('*').append(e)
            }
            part.append('|').append(ef.description)
            sb.append(part)
            if (++fnum < filters.size()) {
                sb.append('\n')
            }
        }
        cmd.add(sb.toString())
        if (parent != null) {
            val wid = WindowToolkit.getWindowID(parent)
            if (wid != 0L) {
                cmd.add("--attach")
                cmd.add(wid.toString())
                wasEnabled = parent!!.isEnabled()
                wasDisabled = true
                parent!!.setEnabled(false)
            }
        }
        if ((getTitle() != null) && !getTitle().trim().isEmpty()) {
            cmd.add("--title=" + getTitle())
        }
        if (approveButtonText != null) {
            cmd.add("--yes-label=" + approveButtonText)
        }
        val exec = arrayOfNulls<String>(cmd.size())
        cmd.toArray<String>(exec)
        LOG.log(Level.INFO, "kdialog: {0}", Arrays.toString(exec))
        val proc = Runtime.getRuntime().exec(exec)
        Runtime.getRuntime().addShutdownHook(Thread(object : Runnable {
            override fun run() {
                proc.destroy()
            }
        }))
        val selected = LinkedList<String>()
        val br = BufferedReader(InputStreamReader(proc.getInputStream()))
        br.forEachLine {
            selected.add(it)
        }
        LOG.log(Level.INFO, "KDialog selection: {0}", selected)
        if (wasEnabled && wasDisabled) {
            parent!!.setEnabled(wasDisabled)
        }
        if (selected.isEmpty()) {
            return null
        } else {
            return selected.map { File(it) }.copyToArray()
        }
    }

    class object {

        private val LOG = Logger.getLogger(javaClass<KDialogFileChooser>().getName())
    }
}
