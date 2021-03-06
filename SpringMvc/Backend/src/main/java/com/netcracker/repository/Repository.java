package com.netcracker.repository;

import com.netcracker.model.Entity;

import java.math.BigInteger;
import java.util.List;

public interface Repository {
    void addSobOrder(BigInteger userId, BigInteger sobOrdId);
    List<BigInteger> getSobOrdersByUsername(String username);
    Entity getEntityById(BigInteger objectId, long objectTypeId);
    Entity getEntityByName (String name, long objectTypeId);
    List<Entity> getEntitiesByObjectTypeId(long objectTypeId);
    void saveEntity(Entity entity);
    void updateEntity (Entity oldEntity, Entity newEntity);
    long getEnumIdByValue(Object enumValue);
}
