package com.itechart.contacts.domain.entity.impl;

import com.itechart.contacts.domain.entity.AbstractEntity;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class of entity-type for keeping phone data
 * @author Marianna Patrusova
 * @version 1.0
 */
public class Phone extends AbstractEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 8515726660858241742L;
    private long phoneId;
    private String countryCode;
    private String operatorCode;
    private String number;
    private PhoneType type;
    private String comments;
    private long contactId;

    public Phone() {}
    public Phone(long phoneId, String countryCode, String operatorCode,
                 String number, PhoneType type, String comments, long contactId) {
        this.phoneId = phoneId;
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.number = number;
        this.type = type;
        this.comments = comments;
        this.contactId = contactId;
    }

    public long getPhoneId() {
        return phoneId;
    }
    public void setPhoneId(long phoneId) {
        this.phoneId = phoneId;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public String getOperatorCode() {
        return operatorCode;
    }
    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public PhoneType getType() {
        return type;
    }
    public void setType(PhoneType type) {
        this.type = type;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public long getContactId() {
        return contactId;
    }
    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Phone phone = (Phone) o;
        return phoneId == phone.phoneId
                && contactId == phone.contactId
                && Objects.equals(countryCode, phone.countryCode)
                && Objects.equals(operatorCode, phone.operatorCode)
                && Objects.equals(number, phone.number)
                && Objects.equals(type, phone.type)
                && Objects.equals(comments, phone.comments);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int)(prime * result + contactId + phoneId);
        result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
        result = prime * result + ((operatorCode == null) ? 0 : operatorCode.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((comments == null) ? 0 : comments.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Phone{");
        stringBuilder.append("phoneId=").append(phoneId).append(", countryCode='")
                .append(countryCode).append('\'').append(", operatorCode='")
                .append(operatorCode).append('\'').append(", number='")
                .append(number).append('\'').append(", type='").append(type)
                .append('\'').append(", comments='").append(comments).append('\'')
                .append(", contactId=").append(contactId).append('}');
        return stringBuilder.toString();
    }

}
