package com.itechart.contacts.domain.util;

import com.itechart.contacts.domain.entity.impl.Attachment;
import com.itechart.contacts.domain.entity.impl.Photo;

/**
 * Class for returning clear attachment path (without timestamp adds)
 *
 * @author Marianna Patrusova
 * @version 1.0
 */
public class PathBuilder {

    private PathBuilder() {
    }

    //old version with absolute path
//    public static String buildPath(Photo photo) {
//        return (photo.getPath().replace("\\\\", "\\") +
//                "\\" + photo.getPhotoId() + "\\" + photo.getName());
//    }
//public static String buildPath(Attachment attachment) {
//    return (attachment.getPath().replace("\\\\", "\\") +
//            "\\" + attachment.getAttachmentId() + "\\" + attachment.getName());
//}

    public static String buildPath(Photo photo) {
        if (!photo.getPath().isEmpty() && !photo.getName().isEmpty()) {
            return (photo.getPath() + "/" + photo.getPhotoId() + "/" + photo.getName());
        } else {
            return "";
        }
    }

    public static String buildPath(Attachment attachment) {
        return (attachment.getPath() + "/" + attachment.getAttachmentId() + "/" + attachment.getName());
    }

}
