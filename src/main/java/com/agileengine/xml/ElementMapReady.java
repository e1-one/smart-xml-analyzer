package com.agileengine.xml;

import org.jsoup.nodes.Element;

public class ElementMapReady extends org.jsoup.nodes.Element {

    public ElementMapReady(Element e) {
        super(e.tag(), e.baseUri(), e.attributes());
        setParentNode(e.parentNode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementMapReady that = (ElementMapReady) o;

        if (tag() != null ? !tag().equals(that.tag()) : that.tag() != null) return false;
       // if (shadowChildrenRef() != null ? !shadowChildrenRef().equals(that.shadowChildrenRef) : that.shadowChildrenRef != null)
        //    return false;
        if (childNodes() != null ? !childNodes().equals(that.childNodes()) : that.childNodes() != null) return false;
        if (attributes() != null ? !attributes().equals(that.attributes()) : that.attributes() != null) return false;
        return baseUri() != null ? baseUri().equals(that.baseUri()) : that.baseUri() == null;
    }

    @Override
    public int hashCode() {
        int result = tag() != null ? tag().hashCode() : 0;
        //result = 31 * result + (shadowChildrenRef != null ? shadowChildrenRef().hashCode() : 0);
        result = 31 * result + (childNodes() != null ? childNodes().hashCode() : 0);
        result = 31 * result + (attributes() != null ? attributes().hashCode() : 0);
        result = 31 * result + (baseUri() != null ? baseUri().hashCode() : 0);
        return result;
    }
}
