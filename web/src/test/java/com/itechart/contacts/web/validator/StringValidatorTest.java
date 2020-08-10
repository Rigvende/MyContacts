package com.itechart.contacts.web.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValidatorTest {

    @Test
    void isValidName() {
        String name1 = "Иван";
        String name2 = "Эрих Мария";
        String name3 = "Петров-Водкин";
        String name4 = "bbb8";
        String name5 = "jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj";
        String name6 = "-Иван";
        String name7 = "";
        assertTrue(StringValidator.isValidName(name1));
        assertTrue(StringValidator.isValidName(name2));
        assertTrue(StringValidator.isValidName(name3));
        assertFalse(StringValidator.isValidName(name4));
        assertFalse(StringValidator.isValidName(name5));
        assertFalse(StringValidator.isValidName(name6));
        assertFalse(StringValidator.isValidName(name7));
    }

    @Test
    void isValidPatronymic() {
        String name1 = "Иванович";
        String name2 = "";
        String name3 = "аааЁ";
        String name4 = "bbb8";
        String name5 = "jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj";
        assertTrue(StringValidator.isValidPatronymic(name1));
        assertTrue(StringValidator.isValidPatronymic(name2));
        assertTrue(StringValidator.isValidPatronymic(name3));
        assertFalse(StringValidator.isValidPatronymic(name4));
        assertFalse(StringValidator.isValidPatronymic(name5));
    }

    @Test
    void isValidData() {
        String data1 = "РБ";
        String data2 = "";
        String data3 = "df;";
        String data4 = "bb\"b8";
        String data5 = "jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj";
        String data6 = "jjjjjjjjjjjjjjjjjjjjjj, -\\()33jj";
        assertTrue(StringValidator.isValidData(data1));
        assertTrue(StringValidator.isValidData(data2));
        assertFalse(StringValidator.isValidData(data3));
        assertFalse(StringValidator.isValidData(data4));
        assertFalse(StringValidator.isValidData(data5));
        assertTrue(StringValidator.isValidData(data6));
    }

    @Test
    void isValidEmail() {
        String mail1 = "ivanov@tut.by";
        String mail2 = "aaa-8@gmail.com";
        String mail3 = "ffffffffff";
        String mail4 = "///ddd@mail.ru";
        String mail5 = "";
        assertTrue(StringValidator.isValidEmail(mail1));
        assertTrue(StringValidator.isValidEmail(mail2));
        assertFalse(StringValidator.isValidEmail(mail3));
        assertFalse(StringValidator.isValidEmail(mail4));
        assertTrue(StringValidator.isValidEmail(mail5));
    }

    @Test
    void isValidWebsite() {
        String site1 = "www.dot.com";
        String site2 = "http://dot.com";
        String site3 = "ffffffffff";
        String site4 = "12333333";
        String site5 = "";
        assertTrue(StringValidator.isValidWebsite(site1));
        assertTrue(StringValidator.isValidWebsite(site2));
        assertFalse(StringValidator.isValidWebsite(site3));
        assertFalse(StringValidator.isValidWebsite(site4));
        assertTrue(StringValidator.isValidWebsite(site5));
    }

    @Test
    void isValidZipcode() {
        String zip1 = "220000";
        String zip2 = "236-2366";
        String zip3 = "df2536";
        String zip4 = "25636_1";
        String zip5 = "";
        assertTrue(StringValidator.isValidZipcode(zip1));
        assertTrue(StringValidator.isValidZipcode(zip2));
        assertFalse(StringValidator.isValidZipcode(zip3));
        assertFalse(StringValidator.isValidZipcode(zip4));
        assertTrue(StringValidator.isValidZipcode(zip5));
    }

    @Test
    void isValidCode() {
        String code1 = "+375";
        String code2 = "0-29";
        String code3 = "111111";
        String code4 = "375f";
        String code5 = "";
        assertTrue(StringValidator.isValidCode(code1));
        assertTrue(StringValidator.isValidCode(code2));
        assertFalse(StringValidator.isValidCode(code3));
        assertFalse(StringValidator.isValidCode(code4));
        assertTrue(StringValidator.isValidCode(code5));
    }

    @Test
    void isValidPhone() {
        String phone1 = "2222222";
        String phone2 = "222-22-22";
        String phone3 = "-2222";
        String phone4 = "452kl";
        String phone5 = "";
        assertTrue(StringValidator.isValidPhone(phone1));
        assertTrue(StringValidator.isValidPhone(phone2));
        assertFalse(StringValidator.isValidPhone(phone3));
        assertFalse(StringValidator.isValidPhone(phone4));
        assertTrue(StringValidator.isValidPhone(phone5));
    }

    @Test
    void isValidComment() {
        String comment1 = "Hello, World!";
        String comment2 = "123456789";
        String comment3 = "";
        String comment4 = "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "123456789012345678901234567890123456789012345678901234567890";
        assertTrue(StringValidator.isValidComment(comment1));
        assertTrue(StringValidator.isValidComment(comment2));
        assertTrue(StringValidator.isValidComment(comment3));
        assertFalse(StringValidator.isValidComment(comment4));
    }

    @Test
    void isValidId() {
        String id1 = "1";
        String id2 = "-5";
        String id3 = "";
        String id4 = "dfd";
        String id5 = "111111111111111111111111111111111111111111111111111111111111";
        assertTrue(StringValidator.isValidId(id1));
        assertTrue(StringValidator.isValidId(id3));
        assertFalse(StringValidator.isValidId(id2));
        assertFalse(StringValidator.isValidId(id4));
        assertFalse(StringValidator.isValidId(id5));
    }

    @Test
    void isValidDate() {
        String birthday1 = "2000-01-01";
        String birthday2 = "";
        String birthday3 = "1999-1-11";
        String birthday4 = "20-10-19";
        String birthday5 = "aaaa-10-10";
        assertTrue(StringValidator.isValidDate(birthday1));
        assertTrue(StringValidator.isValidDate(birthday2));
        assertTrue(StringValidator.isValidDate(birthday3));
        assertFalse(StringValidator.isValidDate(birthday4));
        assertFalse(StringValidator.isValidDate(birthday5));
    }

    @Test
    void isValidGender() {
        String gender1 = "male";
        String gender2 = "female";
        String gender3 = "";
        String gender4 = "transgender";
        assertTrue(StringValidator.isValidGender(gender1));
        assertTrue(StringValidator.isValidGender(gender2));
        assertFalse(StringValidator.isValidGender(gender3));
        assertFalse(StringValidator.isValidGender(gender4));
    }

    @Test
    void isValidType() {
        String type1 = "мобильный";
        String type2 = "иной";
        String type3 = "";
        String type4 = "другой";
        assertTrue(StringValidator.isValidType(type1));
        assertTrue(StringValidator.isValidType(type2));
        assertFalse(StringValidator.isValidType(type3));
        assertFalse(StringValidator.isValidType(type4));
    }

    @Test
    void isValidStatus() {
        String status1 = "deleted";
        String status2 = "updated";
        String status3 = "";
        String status4 = "created";
        assertTrue(StringValidator.isValidStatus(status1));
        assertTrue(StringValidator.isValidStatus(status2));
        assertTrue(StringValidator.isValidStatus(status3));
        assertFalse(StringValidator.isValidStatus(status4));
    }

    @Test
    void isValidFileName() {
        String file1 = "";
        String file2 = "aaa.jpg";
        String file3 = "a123d_7.txt";
        String file4 = "aaa.dddddd";
        String file5 = "aaa/aa.txt";
        assertTrue(StringValidator.isValidFileName(file1));
        assertTrue(StringValidator.isValidFileName(file2));
        assertTrue(StringValidator.isValidFileName(file3));
        assertFalse(StringValidator.isValidFileName(file4));
        assertFalse(StringValidator.isValidFileName(file5));
    }

    @Test
    void isValidPhotoPath() {
        String path1 = "../image/photos/1/";
        String path2 = "";
        String path3 = "C:\\temp\\";
        assertTrue(StringValidator.isValidPhotoPath(path1));
        assertTrue(StringValidator.isValidPhotoPath(path2));
        assertFalse(StringValidator.isValidPhotoPath(path3));
    }

    @Test
    void isValidFilePath() {
        String path1 = "../attachments/1/";
        String path2 = "";
        String path3 = "C:\\temp\\";
        assertTrue(StringValidator.isValidFilePath(path1));
        assertTrue(StringValidator.isValidFilePath(path2));
        assertFalse(StringValidator.isValidFilePath(path3));
    }

    @Test
    void isValidTextLength() {
        String text1 = "";
        String text2 = "dsflajflkjfkafj";
        String text3 = "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "123456789012345678901234567890123456789012345678901234567890";
        assertTrue(StringValidator.isValidTextLength(text1));
        assertTrue(StringValidator.isValidTextLength(text2));
        assertFalse(StringValidator.isValidTextLength(text3));
    }

    @Test
    void isValidMiniText() {
        String text1 = "";
        String text2 = "dsflajflkjfkafj";
        String text3 = "1234567890123456789012345678901234567890123456789012345678901234567890";
        assertTrue(StringValidator.isValidMiniText(text1));
        assertTrue(StringValidator.isValidMiniText(text2));
        assertFalse(StringValidator.isValidMiniText(text3));
    }

    @Test
    void isValidMessage() {
        String text1 = "";
        String text2 = "dsflajflkjfkafj";
        String text3 = "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890";
        assertTrue(StringValidator.isValidMiniText(text1));
        assertTrue(StringValidator.isValidMiniText(text2));
        assertFalse(StringValidator.isValidMiniText(text3));
    }
}