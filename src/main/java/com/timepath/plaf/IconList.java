package com.timepath.plaf;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author TimePath
 */
public class IconList {

    private static final Logger LOG = Logger.getLogger(IconList.class.getName());
    @NotNull
    private final List<Image> list;
    private final int[] sizes;
    private final String path;
    private final String ext;

    public IconList(String path, String ext, int... sizes) {
        this.path = path;
        this.ext = ext;
        this.sizes = sizes;
        list = new LinkedList<>();
        populate();
    }

    void populate() {
        for (int i : sizes) {
            list.add(new ImageIcon(getClass().getResource(path + i + '.' + ext)).getImage());
        }
    }

    @NotNull
    public List<Image> getIcons() {
        return Collections.unmodifiableList(list);
    }
}
