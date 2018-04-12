package com.netcracker.service.impl;
import com.netcracker.config.Constant;
import com.netcracker.model.Entity;
import com.netcracker.model.MapParameter;
import com.netcracker.repository.Repository;
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
            MapParameter pass = entity.getParameterByAttrId(Constant.PASSWORD_HASH_ATTR_ID);
            if( pass != null){
                ShaPasswordEncoder encoder = new ShaPasswordEncoder();
                pass.setValue(encoder.encodePassword(pass.getValue().toString(), null));
            }
        }
        entity.setName(entity.getParameterValueByAttrId(Constant.NAME_ATTR_ID).toString());
        repository.saveEntity(entity);
    }

    @Override
    public void updateEntity(Entity oldEntity, Entity newEntity) {
        repository.updateEntity(oldEntity, newEntity);
    }
}
