package com.itechart.contacts.domain.util;

/**
 * Class for returning clear attachment path (without timestamp adds)
 * @author Marianna Patrusova
 * @version 1.0
 */
public class PathParser {

    private PathParser() {}

    public static String parse(String path) {
        if(!path.isEmpty()) {
            String timestamp = path.substring(path.lastIndexOf("_"));
            path = path.replace(timestamp, "");
        }
        return path;
    }

}
