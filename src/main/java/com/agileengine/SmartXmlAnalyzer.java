package com.agileengine;

import com.agileengine.xml.ElementMapReady;
import com.agileengine.xml.JsoupCssSelectSnippet;
import com.agileengine.xml.JsoupFindByIdSnippet;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class SmartXmlAnalyzer {
    private static Logger LOGGER = LoggerFactory.getLogger(SmartXmlAnalyzer.class);

    private File originFile;
    private File otherFile;

    public SmartXmlAnalyzer(File originFile, File otherFile) {
        this.originFile = originFile;
        this.otherFile = otherFile;
    }

    public Element findBestMatchedElement(String targetId) {
        Optional<Element> targetElementById = JsoupFindByIdSnippet.findElementById(originFile, targetId);
        if (!targetElementById.isPresent()) {
            throw new IllegalStateException("Target element is not present in the origin html");
        }

        Attributes targetElementAttributes = targetElementById.get().attributes();
        //todo also we can check child nodes
        HashMap<String, Elements> elementsByAttributes = new HashMap<>();
        targetElementAttributes.asList().stream().forEach(a -> {
            String cssQuery = "[" + a.getKey() + "=\"" + a.getValue() + "\"]";
            Optional<Elements> elements = JsoupCssSelectSnippet.findElementsByQuery(otherFile, cssQuery);
            if (elements.isPresent()) {
                elementsByAttributes.put(a.getKey(), elements.get());
            }
        });

        List<TargetTagByElement> allMatchedElementsInOneRow = new LinkedList<>();
        elementsByAttributes.entrySet().forEach(entry -> {
            Elements elements = entry.getValue();
            elements.forEach(e -> {
                allMatchedElementsInOneRow.add(new TargetTagByElement(entry.getKey(), new ElementMapReady(e)));
            });
        });

        Map<ElementMapReady, Integer> elementsCounted = countContributionLevelBySimilarityLevel(allMatchedElementsInOneRow);

        Optional<Integer> first = elementsCounted.values().stream().sorted(Comparator.reverseOrder()).findFirst();
        if (!first.isPresent()) {
            throw new IllegalStateException("No elements was found");
        }
        Integer topScore = first.get();

        ElementMapReady bestMatchedElement = getBestMatched(elementsCounted, topScore);

        List<TargetTagByElement> matchedAttributes
                = allMatchedElementsInOneRow
                .stream()
                .filter(e -> e.getElementMapReady().equals(bestMatchedElement))
                .collect(Collectors.toList());


        printDecisionMaking(topScore, matchedAttributes);

        return bestMatchedElement;
    }


    private static Map<ElementMapReady, Integer> countContributionLevelBySimilarityLevel(List<TargetTagByElement> elements) {
        Map<ElementMapReady, Integer> elementsContribution = new HashMap<>();
        elements.forEach(e -> {
            if (elementsContribution.containsKey(e.getElementMapReady())) {
                Integer value = elementsContribution.get(e.getElementMapReady());
                elementsContribution.put(e.getElementMapReady(), value + TagsSimilarityLevel.getSimilarityLvl(e.getTag()));
            } else {
                elementsContribution.put(e.getElementMapReady(), TagsSimilarityLevel.getSimilarityLvl(e.getTag()));
            }
        });
        return elementsContribution;
    }

    private static ElementMapReady getBestMatched(Map<ElementMapReady, Integer> elementsCounted, Integer first) {
        return getKeysByValue(elementsCounted, first).stream().findFirst().get();
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }


    private static void printDecisionMaking(Integer totalScore, List<TargetTagByElement> matchedAttributes) {
        LOGGER.info("Element: {}", matchedAttributes.get(0).getElementMapReady());
        LOGGER.info("Element Attributes and the values of their contribution to the result");
        matchedAttributes.forEach(attributes -> {

            LOGGER.info("{}     with value: {} ", attributes.getTag(), TagsSimilarityLevel.getSimilarityLvl(attributes.getTag()));

        });
        LOGGER.info("Total contribution score of this element is {}", totalScore);

    }


}
