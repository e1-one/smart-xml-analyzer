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

public class Main {

    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        //todo add additional checks
        String inputOriginFilePath = args[0];
        File originFile = new File(inputOriginFilePath);
        if (!originFile.exists()) {
            throw new IllegalArgumentException(" First param is not valid. File not exists: " + originFile);
        }

        String inputOtherSampleFilePath = args[1];
        File otherFile = new File(inputOtherSampleFilePath);
        if (!otherFile.exists()) {
            throw new IllegalArgumentException(" Second param is not valid. File not exists: " + inputOtherSampleFilePath);
        }

        String targetId = args[2];
        if (targetId == null || targetId.length() == 0) {
            throw new IllegalArgumentException(" Third param is not valid. Please specify targetId.");
        }

        Optional<Element> targetElementById = JsoupFindByIdSnippet.findElementById(originFile, targetId);
        if (!targetElementById.isPresent()) {
            LOGGER.info("Target element is not present in the origin html");
        }
        Attributes targetElementAttributes = targetElementById.get().attributes();

        //todo also check child nodes
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
        Set<ElementMapReady> keysByValue = getKeysByValue(elementsCounted, first.get());
        ElementMapReady elementMapReady = keysByValue.stream().findFirst().get();

        List<TargetTagByElement> matchedAttributes
                = allMatchedElementsInOneRow.stream().filter(e -> e.getElementMapReady().equals(elementMapReady)).collect(Collectors.toList());
        printDecisionMaking(first.get(), matchedAttributes);

        String pathToElement = printPathToElement(keysByValue.stream().findFirst().get());
        LOGGER.info("XML path to the element: " + pathToElement);
        System.out.println(pathToElement);
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static String printPathToElement(Element currentElement) {
        StringBuilder absPath = new StringBuilder();
        Elements parents = currentElement.parents();

        for (int j = parents.size() - 1; j >= 0; j--) {
            Element element = parents.get(j);
            absPath.append(printElement(element));
        }
        return absPath.toString() + printElement(currentElement);
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

    private static String printElement(Element element) {
        return "/" + element.tagName() + "[" + element.siblingIndex() + "]";
    }

    private static void printDecisionMaking(Integer totalScore, List<TargetTagByElement> matchedAttributes) {
        LOGGER.info("Element Attributes and the values of their contribution to the result");
        matchedAttributes.forEach(attributes -> {

            LOGGER.info("{}     with value: {} ", attributes.getTag(), TagsSimilarityLevel.getSimilarityLvl(attributes.getTag()));

        });
        LOGGER.info("Total contribution score is {}", totalScore);

    }

}
