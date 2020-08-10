package com.itechart.contacts.domain.util;

import com.itechart.contacts.domain.entity.impl.Attachment;
import com.itechart.contacts.domain.entity.impl.Photo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathBuilderTest {

    @Test
    void buildPath() {
        Photo photo1 = new Photo();
        photo1.setName("aaa.jpg");
        photo1.setPath("../image/photos/1/");
        Photo photo2 = new Photo();
        photo2.setPath("");
        photo2.setPath("");
        assertEquals("../image/photos/1/aaa.jpg", PathBuilder.buildPath(photo1));
        assertEquals("", PathBuilder.buildPath(photo2));
    }

    @Test
    void testBuildPath() {
        Attachment attachment1 = new Attachment();
        attachment1.setName("aaa.jpg");
        attachment1.setPath("../attachments/1/");
        Attachment attachment2 = new Attachment();
        attachment2.setPath("");
        attachment2.setPath("");
        assertEquals("../attachments/1/aaa.jpg", PathBuilder.buildPath(attachment1));
        assertEquals("", PathBuilder.buildPath(attachment2));
    }
}