package com.itechart.contacts.web.util;

import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.entity.impl.Photo;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class RequestParserTest {

    @Test
    void createContact() {
        HashMap<String, String> map = new HashMap<>();
        map.put("idContact", "13");
        map.put("contactName", "Charles");
        map.put("surname", "Darwin");
        map.put("patronymic", "");
        map.put("gender", "male");
        map.put("birthday", "");
        map.put("work", "");
        map.put("status", "");
        map.put("citizenship", "");
        map.put("email", "");
        map.put("website", "www.dot.com");
        map.put("country", "");
        map.put("city", "");
        map.put("address", "");
        map.put("zipcode", "");
        map.put("idPhoto", "13");
        Contact contact = RequestParser.createContact(map);
        assertNotNull(contact);
        assertEquals("Charles", contact.getName());
    }

    @Test
    void createPhoto() {
        HashMap<String, String> map = new HashMap<>();
        map.put("idPhoto", "");
        map.put("photo_name", "aaa.jpg");
        map.put("photo_path", "");
        map.put("photo_status", "");
        Photo photo = RequestParser.createPhoto(map);
        assertNotNull(photo);
        assertEquals("aaa.jpg", photo.getName());
    }
}