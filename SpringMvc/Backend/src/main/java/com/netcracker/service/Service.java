package com.netcracker.service;

import com.netcracker.model.Entity;

import java.math.BigInteger;
import java.util.List;

public interface Service {
    Entity getEntityById(BigInteger objectId, long objectTypeId);
    Entity getEntityByName(String name, long objectTypeId);
    List<Entity> getEntitiesByObjectTypeId(long objectTypeId);
    void saveEntity(Entity entity);
    void updateEntity(Entity oldEntity, Entity newEntity);
}
