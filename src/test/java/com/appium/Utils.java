package com.appium;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class Utils {

    public static String getValueFromYaml(String fileName, String segment, String keyName) {

        Yaml yaml = new Yaml();

        try {

            InputStream in = new FileInputStream(fileName);
            HashMap<String, HashMap> resLocators = (HashMap<String, HashMap>) yaml.load(in);

            HashMap resTexts = resLocators.get(segment);
            return resTexts.get(keyName).toString();

        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static HashMap getSegment(String fileName, String segment) {

        Yaml yaml = new Yaml();

        try {

            InputStream in = new FileInputStream(fileName);
            HashMap<String, HashMap> resLocators = (HashMap<String, HashMap>) yaml.load(in);

            return resLocators.get(segment);

        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {

        }
    }
}
