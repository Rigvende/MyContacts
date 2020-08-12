package com.itechart.contacts.domain.entity.impl;

import com.itechart.contacts.domain.entity.AbstractEntity;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Class of entity-type for keeping attachment data
 * @author Marianna Patrusova
 * @version 1.0
 */
public class Attachment extends AbstractEntity {

    private static final long serialVersionUID = 8377823174769700017L;
    private long attachmentId;
    private String path;
    private String name;
    private String comments;
    private LocalDate loadDate;
    private long contactId;
    private String status;

    public Attachment() {}
    public Attachment(long attachmentId, String path, String name, LocalDate loadDate,
                      String comments, long contactId, String status) {
        this.attachmentId = attachmentId;
        this.path = path;
        this.name = name;
        this.comments = comments;
        this.loadDate = loadDate;
        this.contactId = contactId;
        this.status = status;
    }

    public long getAttachmentId() {
        return attachmentId;
    }
    public void setAttachmentId(long attachmentId) {
        this.attachmentId = attachmentId;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public LocalDate getLoadDate() {
        return loadDate;
    }
    public void setLoadDate(LocalDate loadDate) {
        this.loadDate = loadDate;
    }
    public long getContactId() {
        return contactId;
    }
    public void setContactId(long contactId) {
        this.contactId = contactId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attachment attachment = (Attachment) o;
        return attachmentId == attachment.attachmentId &&
                contactId == attachment.contactId &&
                Objects.equals(path, attachment.path) &&
                Objects.equals(name, attachment.name) &&
                Objects.equals(comments, attachment.comments) &&
                Objects.equals(status, attachment.status) &&
                Objects.equals(loadDate, attachment.loadDate);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int)(prime * result + attachmentId + contactId);
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((comments == null) ? 0 : comments.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((loadDate == null) ? 0 : loadDate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Attachment{");
        stringBuilder.append("attachmentId=").append(attachmentId).append(", path='")
                .append(path).append('\'').append(", name='").append(name)
                .append('\'').append(", status='").append(status)
                .append('\'').append(", comments='").append(comments)
                .append('\'').append(", loadDate=").append(loadDate)
                .append(", contactId=").append(contactId).append('}');
        return stringBuilder.toString();
    }

}
