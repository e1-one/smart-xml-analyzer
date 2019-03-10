package com.agileengine;

import com.agileengine.xml.ElementMapReady;

public class TargetTagByElement {
    private String tag;
    private ElementMapReady elementMapReady;

    public TargetTagByElement(String tag, ElementMapReady elementMapReady) {
        this.tag = tag;
        this.elementMapReady = elementMapReady;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ElementMapReady getElementMapReady() {
        return elementMapReady;
    }

    public void setElementMapReady(ElementMapReady elementMapReady) {
        this.elementMapReady = elementMapReady;
    }
}
