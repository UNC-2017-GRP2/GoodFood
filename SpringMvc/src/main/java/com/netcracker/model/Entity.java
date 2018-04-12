package com.netcracker.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Entity {
    private BigInteger objectId;
    private long objectTypeId;
    private String name;
    private List<MapParameter> parameters;

    public Entity() { }

    public Entity(BigInteger objectId, long objectTypeId, String name){
        this.objectId = objectId;
        this.objectTypeId = objectTypeId;
        this.name = name;
    }

    public Entity(BigInteger objectId, long objectTypeId, String name, List<MapParameter> parameters) {
        this.objectId = objectId;
        this.objectTypeId = objectTypeId;
        this.name = name;
        this.parameters = parameters;
    }

    public BigInteger getObjectId() {
        return objectId;
    }

    public long getObjectTypeId() {
        return objectTypeId;
    }

    public String getName() {
        return name;
    }

    public List<MapParameter> getParameters() {
        return parameters;
    }

    public void setObjectId(BigInteger objectId) {
        this.objectId = objectId;
    }

    public void setObjectTypeId(long objectTypeId) {
        this.objectTypeId = objectTypeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParameters(List<MapParameter> parameters) {
        this.parameters = parameters;
    }

    public Object getParameterValueByAttrId(long attrId){
        for(MapParameter param : parameters){
            if (param.getAttributeId() == attrId){
                return param.getValue();
            }
        }
        return null;
    }
    public MapParameter getParameterByAttrId(long attrId){
        for(MapParameter param : parameters){
            if (param.getAttributeId() == attrId){
                return param;
            }
        }
        return null;
    }
    public List<Object> getListParametersById(long attrId){
        List<Object> params = new ArrayList<>();
        for(MapParameter param : parameters){
            if (param.getAttributeId() == attrId){
                params.add(param.getValue());
            }
        }
        return (params.size() != 0)? params : null;
    }
}
