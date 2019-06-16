package com.agileengine;

import java.util.HashMap;
import java.util.Set;

public final class TagsSimilarityLevel {
    private final static HashMap<String, Integer> similarityLvl = new HashMap<>();

    static {
        similarityLvl.put("id", 25);
        similarityLvl.put("class", 7);
        similarityLvl.put("href", 6);
        similarityLvl.put("title", 10);
        similarityLvl.put("rel", 3);
        similarityLvl.put("style", 2);
        similarityLvl.put("onclick", 1);
    }

    public static Integer getSimilarityLvl(String tag) {
        return similarityLvl.getOrDefault(tag, 1);
    }
}
