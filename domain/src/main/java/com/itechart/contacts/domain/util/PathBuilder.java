package com.itechart.contacts.domain.util;

import com.itechart.contacts.domain.entity.impl.Attachment;
import com.itechart.contacts.domain.entity.impl.Photo;

/**
 * Class for returning attachment path
 * @author Marianna Patrusova
 * @version 1.0
 */
public class PathBuilder {

    private PathBuilder() {
    }

    //path for photo
    public static String buildPath(Photo photo) {
        if (!photo.getPath().isEmpty() && !photo.getName().isEmpty()) {
            return (photo.getPath() + photo.getName());
        } else {
            return "";
        }
    }

    //path for attachment
    public static String buildPath(Attachment attachment) {
        if (!attachment.getPath().isEmpty() && !attachment.getName().isEmpty()) {
            return (attachment.getPath() + attachment.getName());
        } else {
            return "";
        }
    }

}
