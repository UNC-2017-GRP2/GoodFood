package com.netcracker.model;

public class MapParameter {
    private long attributeId;
    private Object value;

    public MapParameter(long attributeId, Object value) {
        this.attributeId = attributeId;
        this.value = value;
    }

    public long getAttributeId() {
        return attributeId;
    }

    public Object getValue() {
        return value;
    }

    public void setAttributeId(long attributeId) {
        this.attributeId = attributeId;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
