package com.agileengine;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class Helper {
    public static String printPathToElement(Element currentElement) {
        StringBuilder absPath = new StringBuilder();
        Elements parents = currentElement.parents();

        for (int j = parents.size() - 1; j >= 0; j--) {
            Element element = parents.get(j);
            absPath.append(printElement(element));
        }
        return absPath.toString() + printElement(currentElement);
    }


    private static String printElement(Element element) {
        return "/" + element.tagName() + "[" + element.siblingIndex() + "]";
    }
}
