package com.timepath.swing;

import java.util.Enumeration;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author timepath
 */
public class TreeUtils {
    
    public static void expand(JTree tree) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
        Enumeration e = root.breadthFirstEnumeration();
        while(e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if(node.isLeaf()) {
                continue;
            }
            int row = tree.getRowForPath(new TreePath(node.getPath()));
            tree.expandRow(row);
        }
    }

    /**
     * @param source
     * @param dest 
     */
    public static void moveChildren(DefaultMutableTreeNode source, DefaultMutableTreeNode dest) {
        moveChildren(source, dest, false);
    }
    
    public static void moveChildren(DefaultMutableTreeNode source, DefaultMutableTreeNode dest, boolean safe) {
        if(safe) {
            dest.add(source);
        } else {
            Enumeration<DefaultMutableTreeNode> e = source.children();
            while(e.hasMoreElements()) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) source.getChildAt(0);
                node.removeFromParent();
                dest.add(node);
            }
        }
    }
    
}
