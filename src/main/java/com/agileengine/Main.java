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

        SmartXmlAnalyzer smartXmlAnalyzer = new SmartXmlAnalyzer(originFile, otherFile);
        Element bestMatchedElement = smartXmlAnalyzer.findBestMatchedElement(targetId);

        String pathToElement = Helper.printPathToElement(bestMatchedElement);

        LOGGER.info("XML path to the element: " + pathToElement);

        System.out.println(pathToElement);
    }


}
