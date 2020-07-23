package com.itechart.contacts.domain.entity;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1468398121933623850L;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
