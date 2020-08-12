package com.itechart.contacts.domain.entity.impl;

import com.itechart.contacts.domain.entity.AbstractEntity;
import java.util.Objects;

/**
 * Class of entity-type for keeping photo data
 * @author Marianna Patrusova
 * @version 1.0
 */
public class Photo extends AbstractEntity {

    private static final long serialVersionUID = -8168785902630912721L;
    private long photoId;
    private String path;
    private String name;
    private String status;

    public Photo() {}
    public Photo(long photoId, String path, String name, String status) {
        this.photoId = photoId;
        this.path = path;
        this.name = name;
        this.status = status;
    }

    public long getPhotoId() {
        return photoId;
    }
    public void setPhotoId(long photoId) {
        this.photoId = photoId;
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
        Photo photo = (Photo) o;
        return photoId == photo.photoId
                && Objects.equals(name, photo.name)
                && Objects.equals(status, photo.status)
                && Objects.equals(path, photo.path);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int)(prime * result + photoId);
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Photo{");
        stringBuilder.append("photoId=").append(photoId).append(", path='")
                .append(path).append('\'').append(", status='")
                .append(status).append('\'').append(", name='")
                .append(name).append('\'').append('}');
        return stringBuilder.toString();
    }

}
