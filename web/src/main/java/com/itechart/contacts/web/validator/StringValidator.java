package com.itechart.contacts.web.validator;

import com.itechart.contacts.domain.entity.impl.Gender;
import com.itechart.contacts.domain.entity.impl.PhoneType;
import com.itechart.contacts.web.util.Status;
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

    private final static String CHECK_NAME = "^[A-zА-яЁё]+([\\s-]+[A-zА-яЁё]+)?$";
    private final static String CHECK_PATRONYMIC = "^[A-zА-яЁё]+$";
    private final static String CHECK_DATA = "^[-)\\\\(.,A-zА-яЁё\\d\\s/]+$";
    private final static String CHECK_EMAIL = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";
    private final static String CHECK_WEB = "([-\\w]+\\.)?([-\\w]+)\\.\\w+(?:\\.\\w+)?/?.*";
    private final static String CHECK_ZIP = "^[\\d]+[-]?[\\d]+$";
    private final static String CHECK_CODE = "^([-+\\d]){2,4}$";
    private final static String CHECK_NUMBER = "^[\\d]([-\\d]){4,9}$";
    private final static String CHECK_ID = "[\\d]+";
    private final static String CHECK_BIRTHDAY = "^([\\d]){4}-([\\d]){1,2}-([\\d]){1,2}$";
    private final static String CHECK_PATH_PHOTO = "^\\.\\./image/photos/[\\d]+/$";
    private final static String CHECK_PATH_FILE = "^\\.\\./attachments/[\\d]+/$";
    private final static String CHECK_FILE_NAME = "^[-+=!#$%&^'.)(\\]\\[\\wА-яЁё\\s_]+\\.([\\w]){2,4}$";
    private final static int MINI_TEXT_LENGTH = 45;
    private final static int TEXT_LENGTH = 255;
    private final static int MESSAGE_LENGTH = 1000;

    //name, surname
    public static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(CHECK_NAME);
        Matcher matcher = pattern.matcher(name);
        return matcher.find() && isValidMiniText(name);
    }

    //patronymic
    public static boolean isValidPatronymic(String patronymic) {
        Pattern pattern = Pattern.compile(CHECK_PATRONYMIC);
        Matcher matcher = pattern.matcher(patronymic);
        return patronymic.isEmpty() || (matcher.find() && isValidMiniText(patronymic));
    }

    //citizenship, family_status, work_place, country, city, address
    public static boolean isValidData(String data) {
        Pattern pattern = Pattern.compile(CHECK_DATA);
        Matcher matcher = pattern.matcher(data);
        return data.isEmpty() || (matcher.find() && isValidMiniText(data));
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

    //id
    public static boolean isValidId(String number) {
        Pattern pattern = Pattern.compile(CHECK_ID);
        Matcher matcher = pattern.matcher(number);
        if (matcher.find()) {
            return (new BigInteger(number).compareTo(BigInteger.valueOf(Long.MAX_VALUE)) < 0
                    && Long.parseLong(number) > 0);
        }
        return number.isEmpty();
    }

    //birthday
    public static boolean isValidDate(String birthday) {
        Pattern pattern = Pattern.compile(CHECK_BIRTHDAY);
        Matcher matcher = pattern.matcher(birthday);
        return birthday.isEmpty() || matcher.find();
    }

    //gender
    public static boolean isValidGender(String gender) {
        return gender.equals(Gender.MALE.getValue())
                || gender.equals(Gender.FEMALE.getValue())
                || gender.equals(Gender.UNKNOWN.getValue());
    }

    //phone type
    public static boolean isValidType(String type) {
        return type.equals(PhoneType.HOME.getValue())
                || type.equals(PhoneType.MOBILE.getValue())
                || type.equals(PhoneType.OTHER.getValue())
                || type.equals(PhoneType.WORK.getValue());
    }

    //status
    public static boolean isValidStatus(String status) {
        return status.isEmpty()
                || status.equals(Status.UPDATED.getValue())
                || status.equals(Status.DELETED.getValue());
    }

    //file name
    public static boolean isValidFileName(String name) {
        Pattern pattern = Pattern.compile(CHECK_FILE_NAME);
        Matcher matcher = pattern.matcher(name);
        return name.isEmpty() || (matcher.find() && isValidMiniText(name));
    }

    //photo path
    public static boolean isValidPhotoPath(String path) {
        Pattern pattern = Pattern.compile(CHECK_PATH_PHOTO);
        Matcher matcher = pattern.matcher(path);
        return path.isEmpty() || (matcher.find() && isValidTextLength(path));
    }

    //file path
    public static boolean isValidFilePath(String path) {
        Pattern pattern = Pattern.compile(CHECK_PATH_FILE);
        Matcher matcher = pattern.matcher(path);
        return path.isEmpty() ||(matcher.find() && isValidTextLength(path));
    }

    //long field
    public static boolean isValidTextLength(String header) {
        return header.length() < TEXT_LENGTH;
    }


    //short field
    public static boolean isValidMiniText(String field) {
        return field.length() < MINI_TEXT_LENGTH;
    }

    //message
    public static boolean isValidMessage(String message) {
        return message.length() < MESSAGE_LENGTH;
    }

}
