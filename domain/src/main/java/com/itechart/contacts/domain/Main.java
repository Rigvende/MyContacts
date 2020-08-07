package com.itechart.contacts.domain;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.ContactDao;
import com.itechart.contacts.domain.dao.impl.PhotoDao;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.entity.impl.Gender;
import com.itechart.contacts.domain.entity.impl.Photo;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.UpdateAttachmentService;
import com.itechart.contacts.domain.service.UpdateContactService;
import com.itechart.contacts.domain.service.UpdatePhotoService;
import freemarker.template.TemplateException;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws DaoException, SQLException, ClassNotFoundException, ServiceException, IOException, TemplateException {
//        Connection connection = DbcpManager.getConnection();
//        System.out.println(connection);
//        connection.close();
//        System.out.println(connection);
////////////////////////////////////////////////
//        ContactDao contactDao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
//        List<AbstractEntity> contactList = contactDao.findAll();
//        StringBuilder json = new StringBuilder("[\n");
//        for (AbstractEntity entity : contactList) {
//            Contact contact = (Contact) entity;
//            json.append("{\n");
//            json.append("\"id\": ").append(JSONObject.numberToString(contact.getContactId())).append(",\n");
//            json.append("\"name\": ").append(JSONObject.quote(contact.getName())).append(",\n");
//            json.append("\"surname\": ").append(JSONObject.quote(contact.getSurname())).append(",\n");
//            json.append("\"patronymic\": ").append(JSONObject.quote(contact.getPatronymic())).append("\n");
//            json.append("\"country\": ").append(JSONObject.quote(contact.getCountry())).append("\n");
//            json.append("\"city\": ").append(JSONObject.quote(contact.getCity())).append("\n");
//            json.append("\"address\": ").append(JSONObject.quote(contact.getAddress())).append("\n");
//            json.append("\"birthday\": ").append(JSONObject.valueToString(contact.getBirthday())).append("\n");
//            json.append("\"work\": ").append(JSONObject.quote(contact.getWork())).append("\n");
//            json.append("},\n");
//        }
//        String responseJson = json.toString().substring(0, json.length() - 2) + "\n]";
//        System.out.println(responseJson);
//        contactDao.exit();
////////////////////////////////////////////
//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/contacts_patrusova?serverTimezone=Europe/Minsk&useSSL=false&allowPublicKeyRetrieval=true&autoReconnect=true&useUnicode=true&characterEncoding=utf8", "root", "root");
//        connection.close();

        /////////////////////////////////
//        PhotoDao photoDao = (PhotoDao) DaoFactory.createDao(EntityType.PHOTO);
//        Photo photo = new Photo(0, "");
//        photo = (Photo) photoDao.create(photo);
//        System.out.println(photo);
//        photoDao.exit();
//        ContactDao contactDao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
//        Contact contact = new Contact(0, "Ольга", "Петренко", "Ивановна",
//                LocalDate.of(2000,5, 6), Gender.FEMALE, "РБ",
//                "не замужем", "", "olga@yandex.by", "БПС-Сбербанк",
//                "РБ", "Минск", "Каменногорская 20-10", "220036",
//                photo.getPhotoId(), null);
//        contact = (Contact)contactDao.create(contact);
//        System.out.println(contact);
//        contactDao.exit();
//        PhoneDao phoneDao = (PhoneDao) DaoFactory.createDao(EntityType.PHONE);
//        Phone phone = new Phone(0, "+375", "17",
//                "214-56-23", PhoneType.WORK, "", contact.getContactId());
//        phone = (Phone) phoneDao.create(phone);
//        System.out.println(phone);
//        phoneDao.exit();
//        AttachmentDao attachmentDao = (AttachmentDao) DaoFactory.createDao(EntityType.ATTACHMENT);
//        Attachment attachment = new Attachment(0, "C:\\\\attachments\\xssflt.jar", "xssflt.jar",
//                LocalDate.of(2020, 7, 22), "", contact.getContactId());
//        attachment = (Attachment) attachmentDao.create(attachment);
//        System.out.println(attachment);
//        attachmentDao.exit();
        //////////////////////////////
//        long id = 1;
//        ContactDao contactDao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
//        PhotoDao photoDao = (PhotoDao) DaoFactory.createDao(EntityType.PHOTO);
//        PhoneDao phoneDao = (PhoneDao) DaoFactory.createDao(EntityType.PHONE);
//        AttachmentDao attachmentDao = (AttachmentDao) DaoFactory.createDao(EntityType.ATTACHMENT);
//        Contact contact = (Contact) contactDao.findEntityById(id);
//        System.out.println(contact);
//        if (contact != null) {
//            Photo photo = (Photo) photoDao.findEntityById(contact.getPhotoId());
//            List<AbstractEntity> alist = attachmentDao.findByContactId(id);
//            List<AbstractEntity> plist = phoneDao.findByContactId(id);
//            StringBuilder json = new StringBuilder("{\n");
//            json.append("\"gender\": ").append(JSONObject.quote(contact.getGender().getValue())).append(",\n");
//            json.append("\"citizenship\": ").append(JSONObject.quote(contact.getCitizenship())).append(",\n");
//            json.append("\"status\": ").append(JSONObject.quote(contact.getFamilyStatus())).append(",\n");
//            json.append("\"website\": ").append(JSONObject.quote(contact.getWebsite())).append("\n");
//            json.append("\"email\": ").append(JSONObject.quote(contact.getEmail())).append("\n");
//            json.append("\"zipcode\": ").append(JSONObject.quote(contact.getZipcode())).append("\n");
//            json.append("\"id_photo\": ").append(JSONObject.numberToString(photo.getPhotoId())).append("\n");
//            json.append("\"photo\": ").append(JSONObject.quote(photo.getPath())).append("\n");
//            if (!alist.isEmpty()) {
//                for (AbstractEntity entity : alist) {
//                    Attachment attachment = (Attachment) entity;
//                    json.append("\"id_attachment\": ").append(JSONObject.numberToString(attachment.getAttachmentId())).append(",\n");
//                    json.append("\"a_path\": ").append(JSONObject.quote(attachment.getPath())).append(",\n");
//                    json.append("\"a_name\": ").append(JSONObject.quote(attachment.getName())).append(",\n");
//                    json.append("\"a_date\": ").append(JSONObject.valueToString(attachment.getLoadDate())).append("\n");
//                    json.append("\"a_comments\": ").append(JSONObject.quote(attachment.getComments())).append("\n");
//                }
//            }
//           if (!plist.isEmpty()) {
//                for (AbstractEntity entity : plist) {
//                    Phone phone = (Phone) entity;
//                    json.append("\"id_phone\": ").append(JSONObject.numberToString(phone.getPhoneId())).append(",\n");
//                    json.append("\"p_country\": ").append(JSONObject.quote(phone.getCountryCode())).append(",\n");
//                    json.append("\"p_operator\": ").append(JSONObject.quote(phone.getOperatorCode())).append(",\n");
//                    json.append("\"p_number\": ").append(JSONObject.quote(phone.getNumber())).append("\n");
//                    json.append("\"p_type\": ").append(JSONObject.quote(phone.getType().getValue())).append(",\n");
//                    json.append("\"p_comments\": ").append(JSONObject.quote(phone.getComments())).append("\n");
//                }
//            }
//            json.append("\"id\": ").append(JSONObject.numberToString(contact.getContactId())).append(",\n");
//            json.append("\"name\": ").append(JSONObject.quote(contact.getName())).append(",\n");
//            json.append("\"surname\": ").append(JSONObject.quote(contact.getSurname())).append(",\n");
//            json.append("\"patronymic\": ").append(JSONObject.quote(contact.getPatronymic())).append("\n");
//            json.append("\"country\": ").append(JSONObject.quote(contact.getCountry())).append("\n");
//            json.append("\"city\": ").append(JSONObject.quote(contact.getCity())).append("\n");
//            json.append("\"address\": ").append(JSONObject.quote(contact.getAddress())).append("\n");
//            json.append("\"birthday\": ").append(JSONObject.valueToString(contact.getBirthday())).append("\n");
//            json.append("\"work\": ").append(JSONObject.quote(contact.getWork())).append("\n");
//            json.append("},\n");
//            System.out.println(json.toString());
//        }
//        contactDao.exit();
//        photoDao.exit();
//        phoneDao.exit();
//        attachmentDao.exit();
///////////////////////////////////////////////////////////
//        System.out.println(PathParser.parse(""));
//        System.out.println(PathParser.parse("df_1546294984"));
//////////////////////////////////////////

//        MailService service = new MailService();
//        System.out.println(service.service("zvezdo4ka13@yandex.by", "Привет сервлет", "Привет, Мэри. Ещё не сдохла?"));
/////////////////////////
//        ContactDao dao = null;
//        StringBuilder message = new StringBuilder("Дни рождения: \n");
//        try {
//            dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
//            List<String> birthdayList = dao.findAllByBirthday();
//            for (String person : birthdayList) {
//                message.append(person).append("\n");
//            }
//            System.out.println(message.toString());
//            MailService service = new MailService();
//            String header = "Дни рождения" + LocalDate.now() ;
//            String body = message.toString();
//            service.service("zvezdo4ka13@yandex.by", header, body);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        /////////////////
//        ContactDao dao = null;
//        String query = "SELECT id_contact, contact_name, surname, patronymic, " +
//                "birthday, gender, citizenship, family_status, website, email, " +
//                "work_place, country, city, address, zipcode, id_photo " +
//                "FROM contacts WHERE contact_name = 'Ольга' AND birthday > '1980-11-08' AND deleted is null;";
//        try {
//            dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
//            List<AbstractEntity> birthdayList = dao.findAllByFilter(query);
//            for (AbstractEntity person : birthdayList) {
//                Contact contact = (Contact) person;
//                System.out.println(contact);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (dao != null) {
//                dao.exit();
//            }
//        }

        ////////////////////
//        String email = "zvezdo4ka13@yandex.by";
//        Pattern pattern = Pattern.compile("^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$");
//        Matcher matcher = pattern.matcher(email);
//
//        System.out.println(matcher.find());

////////////////////
//        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
//        FileTemplateLoader ftl1 = new FileTemplateLoader(new File("view\\src\\main\\webapp\\templates"));
//        cfg.setTemplateLoader(ftl1);
//        ContactDao dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
//        Contact contact = (Contact) dao.findEntityById(1);
//        Map<String, Object> root = new HashMap<>();
//        root.put("user", contact);
//        Template temp = cfg.getTemplate("birthday.ftl");
//        Writer out = new OutputStreamWriter(System.out);
        //////Writer out = response.getWriter();
//        temp.process(root, out);
//        out.close();

        /////////////////////

//        StringBuilder builder = new StringBuilder("SELECT id_contact, contact_name, surname, patronymic, ");
//        builder.append("birthday, gender, citizenship, family_status, website, email, work_place, ")
//                .append("country, city, address, zipcode, id_photo  FROM contacts WHERE ");
//        String name = "Максим";
//        if (name != null && !name.isEmpty()) {
//            builder.append("contact_name = '").append(name).append("' AND ");
//        }
//        String birthday = "1999-01-01";
//        if (birthday != null && !birthday.isEmpty()) {
//            String condition = "after";
//            if (condition != null && !condition.isEmpty()) {
//                switch (condition) {
//                    case "strict":
//                        builder.append("birthday = '").append(birthday).append("' AND ");
//                        break;
//                    case "before":
//                        builder.append("birthday < '").append(birthday).append("' AND ");
//                        break;
//                    case "after":
//                        builder.append("birthday > '").append(birthday).append("' AND ");
//                        break;
//                }
//            }
//        }
//        String gender = "male";
//        if (gender != null && !gender.isEmpty()) {
//            builder.append("gender = '").append(gender).append("' AND ");
//        }
//        String city = "Минск";
//        if (city != null && !city.isEmpty()) {
//            builder.append("city = '").append(city).append("' AND ");
//        }
//        builder.append("deleted IS NULL;");
//        System.out.println(builder.toString());
//        SearchService service = new SearchService();
//        String string = service.service(builder.toString());
//        System.out.println(string);
        /////////////////////

//        DeleteContactService service = new DeleteContactService();
//        System.out.println(service.service(64, 65));
//        ContactDao dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
//        System.out.println(dao.findEntityById(64));
//        System.out.println(dao.findEmailById(65).equals(""));
//        dao.exit();

        /////////

//        String[] ids = "g 103".split(" ");
//        int size = ids.length;
//        long[] parsed = new long[size];
//        for (int i = 0; i < size; i++) {
//            if (isValidId(ids[i])) {
//                parsed[i] = Long.parseLong(ids[i]);
//                System.out.println(parsed[i]);
//            }
//        }
        ///////////
//        String email = "Адрес отсутствует. Обновите данные контакта.";
//        Pattern pattern = Pattern.compile("^[-\\\\w.]+@([A-z0-9][-A-z0-9]+\\\\.)+[A-z]{2,4}$");
//        Matcher matcher = pattern.matcher(email);
//        System.out.println(matcher.find());

        /////////////////////

//        PhotoDao dao = (PhotoDao) DaoFactory.createDao(EntityType.PHOTO);
//        Photo photo = (Photo) dao.findEntityById(1);
//        String photoPath =photo.getPath().replace("\\\\", "\\")
//                + "\\" + photo.getPhotoId() + "\\" + photo.getName();
//        System.out.println(photoPath);
//        dao.exit();

///////////////////////////////
//        StringBuilder json = new StringBuilder("dfsfsf, sdfdsfs, sddfsfs, },");
//        json.replace(json.length() - 5 , json.length(), "\n}\n");
//        json.append("]\n");
//        System.out.println(json.toString());

        /////////////////
//        String x = "C:\\Users\\Администратор.000\\IdeaProjects\\Contacts_Patrusova\\view\\src\\main\\webapp\\css\\style.css";
//        x = x.substring(x.lastIndexOf("\\") + 1);
//        System.out.println(x);

        ///////////////////
//
//        Contact contact = new Contact(0, "Максим", "Жуковский", "", null,
//                Gender.getGender("unknown"), "", "", "", "", "",
//                "", "", "", "", 0, null);
//        Photo photo = new Photo(0, "", "user_no_photo.png");
//        UpdatePhotoService ups = new UpdatePhotoService();
//        photo = (Photo) ups.service(photo);
//        System.out.println(photo);
//        contact.setPhotoId(photo.getPhotoId());
//        UpdateContactService ucs = new UpdateContactService();
//        contact = (Contact) ucs.service(contact);
//        System.out.println(contact);
//        Contact contact = new Contact(1, "Иван", "Петров", "Иванович",
//                LocalDate.of(2000, 1, 1), Gender.MALE, "aa",
//                "женат", "www.petrov.com", "petrov@tut.by", "I-Tech-Art",
//                "РБ", "Минск", "Руссиянова, 20-15", "220000", 1, null);
////
//        ContactDao dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
//        System.out.println(dao.update(contact));
//        dao.exit();

    }
//
//    public static boolean isValidId(String data) {
//        Pattern pattern = Pattern.compile("[\\d]+");
//        Matcher matcher = pattern.matcher(data);
//        if (matcher.find()) {
//            return  (new BigInteger(data).compareTo(BigInteger.valueOf(Long.MAX_VALUE)) < 0);
//        }
//        return false;
//    }


}
