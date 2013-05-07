package com.timepath.beans;

import java.beans.PropertyEditor;
import java.beans.PropertyChangeListener;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Component;

public class PropertyEditorAdapter implements PropertyEditor {

    @Override
    public void setValue(Object o) {
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public boolean isPaintable() {
        return false;
    }

    @Override
    public void paintValue(Graphics grphcs, Rectangle rctngl) {
    }

    @Override
    public String getJavaInitializationString() {
        return null;
    }

    @Override
    public String getAsText() {
        return null;
    }

    @Override
    public void setAsText(String string) throws IllegalArgumentException {
    }

    @Override
    public String[] getTags() {
        return null;
    }

    @Override
    public Component getCustomEditor() {
        return null;
    }

    @Override
    public boolean supportsCustomEditor() {
        return false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pl) {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pl) {
    }
}