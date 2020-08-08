package com.itechart.contacts.web.validator;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for validating user's string input.
 * @author Marianna Patrusova
 * @version 1.0
 */
public class StringValidator {

    private StringValidator(){}

    private final static String CHECK_NAME = "^[A-Za-zА-Яа-яЁё]+([\\s-]+[A-Za-zА-Яа-яЁё]+)?$";
    private final static String CHECK_PATRONYMIC = "^([A-Za-zА-Яа-яЁё]){1,45}$";
    private final static String CHECK_DATA = "^([-)(.,\\w\\s/\\\\]){1,45}$";
    private final static String CHECK_EMAIL = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";
    private final static String CHECK_WEB = "([-\\w]+\\.)?([-\\w]+)\\.\\w+(?:\\.\\w+)?/?.*";
    private final static String CHECK_ZIP = "^[\\d]+[-]?[\\d]+$";
    private final static String CHECK_CODE = "^([-+\\d]){2,4}$";
    private final static String CHECK_NUMBER = "^[\\d]([-\\d]){4,9}$";
    private final static String CHECK_ID = "[\\d]+";
    private final static int TEXT_LENGTH = 255;
    private final static int MESSAGE_LENGTH = 1000;

    //name, surname
    public static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(CHECK_NAME);
        Matcher matcher = pattern.matcher(name);
        return matcher.find() && name.length() <= 45;
    }

    //patronymic
    public static boolean isValidPatronymic(String patronymic) {
        Pattern pattern = Pattern.compile(CHECK_PATRONYMIC);
        Matcher matcher = pattern.matcher(patronymic);
        return matcher.find();
    }

    //citizenship, family_status, work_place, country, city, address
    public static boolean isValidData(String data) {
        Pattern pattern = Pattern.compile(CHECK_DATA);
        Matcher matcher = pattern.matcher(data);
        return data.isEmpty() || matcher.find();
    }

    //email
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(CHECK_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return email.isEmpty() || (matcher.find() && isValidTextLength(email) && email.length() >= 10);
    }

    //web
    public static boolean isValidWebsite(String web) {
        Pattern pattern = Pattern.compile(CHECK_WEB);
        Matcher matcher = pattern.matcher(web);
        return web.isEmpty() || (matcher.find() && isValidTextLength(web) && web.length() > 5);
    }

    //zipcode
    public static boolean isValidZipcode(String zipcode) {
        Pattern pattern = Pattern.compile(CHECK_ZIP);
        Matcher matcher = pattern.matcher(zipcode);
        return zipcode.isEmpty() || (matcher.find() && zipcode.length() > 5 && zipcode.length() < 10);
    }

    //country_code, operator_code
    public static boolean isValidCode(String code) {
        Pattern pattern = Pattern.compile(CHECK_CODE);
        Matcher matcher = pattern.matcher(code);
        return code.isEmpty() || matcher.find();
    }

    //phone_number
    public static boolean isValidPhone(String number) {
        Pattern pattern = Pattern.compile(CHECK_NUMBER);
        Matcher matcher = pattern.matcher(number);
        return number.isEmpty() || matcher.find();
    }

    //comments
    public static boolean isValidComment(String comment) {
        return comment.isEmpty() || isValidTextLength(comment);
    }

    //attachment_name
    public static boolean isValidAttachment(String name) {
        return name.length() <= 45;
    }

    //id
    public static boolean isValidId(String number) {
        Pattern pattern = Pattern.compile(CHECK_ID);
        Matcher matcher = pattern.matcher(number);
        if (matcher.find()) {
            return new BigInteger(number).compareTo(BigInteger.valueOf(Long.MAX_VALUE)) < 0;
        }
        return false;
    }

    //Mail header
    public static boolean isValidTextLength(String header) {
        return header.length() < TEXT_LENGTH;
    }

    //message
    public static boolean isValidMessage(String message) {
        return message.length() < MESSAGE_LENGTH;
    }

}
