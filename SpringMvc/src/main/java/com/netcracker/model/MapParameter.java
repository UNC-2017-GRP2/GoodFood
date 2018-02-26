package com.netcracker.model;

public class MapParameter {
    private long atttributeId;
    private Object value;

    public MapParameter(long atttributeId, Object value) {
        this.atttributeId = atttributeId;
        this.value = value;
    }

    public long getAtttributeId() {
        return atttributeId;
    }

    public Object getValue() {
        return value;
    }

    public void setAtttributeId(long atttributeId) {
        this.atttributeId = atttributeId;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
