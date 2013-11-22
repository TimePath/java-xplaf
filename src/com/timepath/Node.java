package com.timepath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

/**
 *
 * @author TimePath
 * @param <X>
 */
public class Node<X> {

    private X value;

    private static final Logger LOG = Logger.getLogger(Node.class.getName());

    private Node<X> parent;

    private ArrayList<Node<X>> children = new ArrayList<Node<X>>();

    private ArrayList<X> properties = new ArrayList<X>();

    public Node(Object a) {

    }

    public void add(Node<X> e) {
        e.parent = this;
        getChildren().add(e);
    }

    public void add(Node<X>... a) {
        for(Node<X> n : a) {
            add(n);
        }
    }

    public void add(Collection<Node<X>> c) {
        for(Node<X> n : c) {
            add(n);
        }
    }

    public void add(X... properties) {
        this.getProperties().addAll(Arrays.asList(properties));
    }

    /**
     * @return the children
     */
    public ArrayList<Node<X>> getChildren() {
        return children;
    }

    /**
     * @return the parent
     */
    public Node<X> getParent() {
        return parent;
    }

    /**
     * @return the properties
     */
    public ArrayList<X> getProperties() {
        return properties;
    }

    /**
     * @return the value
     */
    public X getValue() {
        return value;
    }

}
