package com.netcracker.service.impl;
import com.netcracker.config.Constant;
import com.netcracker.model.Entity;
import com.netcracker.model.MapParameter;
import com.netcracker.repository.Repository;
import com.netcracker.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import java.math.BigInteger;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements com.netcracker.service.Service {

    @Autowired
    private Repository repository;

    @Override
    public Entity getEntityById(BigInteger objectId, long objectTypeId) {
        return repository.getEntityById(objectId, objectTypeId);
    }

    @Override
    public Entity getEntityByName(String name, long objectTypeId) {
        return repository.getEntityByName(name, objectTypeId);
    }

    @Override
    public List<Entity> getEntitiesByObjectTypeId(long objectTypeId) {
        return repository.getEntitiesByObjectTypeId(objectTypeId);
    }

    @Override
    public void saveEntity(Entity entity) {
        if (entity.getObjectTypeId() == Constant.USER_OBJ_TYPE_ID){
            entity.getParameters().add(new MapParameter(Constant.USER_ROLE_ATTR_ID, Constant.ROLE_USER_ENUM_ID));
            for(MapParameter parameter : entity.getParameters()){
                if(parameter.getAttributeId() == Constant.PASSWORD_HASH_ATTR_ID){
                    ShaPasswordEncoder encoder = new ShaPasswordEncoder();
                    parameter.setValue(encoder.encodePassword(parameter.getValue().toString(), null));
                }
            }
        }
        repository.saveEntity(entity);
    }

    @Override
    public void updateEntity(Entity oldEntity, Entity newEntity) {
        repository.updateEntity(oldEntity, newEntity);
    }
}
