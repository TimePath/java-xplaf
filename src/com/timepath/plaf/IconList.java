package com.timepath.plaf;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author timepath
 */
public class IconList {

    public IconList(String path, String ext, int[] sizes) {
        this.path = path;
        this.ext = ext;
        this.sizes = sizes;
        list = new ArrayList<Image>();
        populate();
    }

    protected void populate() {
        for(int i : sizes) {
            list.add(new ImageIcon(getClass().getResource(path + i + "." + ext)).getImage());
        }
    }

    public List<Image> getIcons() {
        return list;
    }

    private final ArrayList<Image> list;

    private final int[] sizes;

    private final String path;

    private final String ext;

    private static final Logger LOG = Logger.getLogger(IconList.class.getName());

}
