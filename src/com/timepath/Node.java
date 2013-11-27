package com.timepath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

/**
 *
 * @author TimePath
 * @param <A> Property type
 * @param <B> Your subclass
 */
public class Node<A, B extends Node<A, B>> {

    public Object custom;

    private A value;

    private static final Logger LOG = Logger.getLogger(Node.class.getName());

    protected B parent;

    private ArrayList<B> children = new ArrayList<B>();

    private ArrayList<A> properties = new ArrayList<A>();

    public Node() {
        this.custom = this.toString();
    }

    public Node(Object a) {
        this.custom = a;
    }

    public Object getCustom() {
        return custom;
    }

    @SuppressWarnings("unchecked")
    public void addNode(B e) {
        e.parent = (B) this;
        getChildren().add(e);
    }

    public void removeNode(B e) {
        e.parent = null;
        getChildren().remove(e);
    }

    public void addAllNodes(Collection<B> c) {
        for(B n : c) {
            addNode(n);
        }
    }

    public void addAllNodes(B... nodes) {
        addAllNodes(Arrays.asList(nodes));
    }

    public void add(A property) {
        this.getProperties().add(property);
    }

    public void addAll(Collection<A> c) {
        for(A property : c) {
            add(property);
        }
    }

    public void addAll(A... properties) {
        addAll(Arrays.asList(properties));
    }

    public B getNamedNode(Object identifier) {
        for(B c : this.getChildren()) {
            if(c.custom.equals(identifier)) {
                return c;
            }
        }
        return null;
    }

    public boolean has(Object identifier) {
        return getNamedNode(identifier) != null;
    }

    /**
     * @return the children
     */
    public ArrayList<B> getChildren() {
        return children;
    }

    /**
     * @return the parent
     */
    public B getParent() {
        return parent;
    }

    /**
     * @return the properties
     */
    public ArrayList<A> getProperties() {
        return properties;
    }

    /**
     * @return the value
     */
    public A getValue() {
        return value;
    }

}
