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

    public Photo() {}
    public Photo(long photoId, String path) {
        this.photoId = photoId;
        this.path = path;
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
                && Objects.equals(path, photo.path);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int)(prime * result + photoId);
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Photo{");
        stringBuilder.append("photoId=").append(photoId).append(", path='")
                .append(path).append('\'').append('}');
        return stringBuilder.toString();
    }

}
