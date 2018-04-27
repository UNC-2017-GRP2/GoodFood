package com.netcracker.controller;

import com.netcracker.model.Entity;
import com.netcracker.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/entity")
public class EntityController {
    @Autowired
    ServiceImpl service;

    @RequestMapping(value = "/id/{objectId}/typeid/{objectTypeId}", method = RequestMethod.GET)
    public Entity getEntityById(@PathVariable BigInteger objectId, @PathVariable long objectTypeId){
        return service.getEntityById(objectId,objectTypeId);
    }
    @RequestMapping(value = "/name/{name}/typeid/{objectTypeId}", method = RequestMethod.GET)
    public Entity getEntityByName(@PathVariable String name, @PathVariable long objectTypeId){
        return service.getEntityByName(name,objectTypeId);
    }
    @RequestMapping(value = "/typeid/{objectTypeId}", method = RequestMethod.GET)
    public List<Entity> getEntitiesByObjectType( @PathVariable long objectTypeId){
        return service.getEntitiesByObjectTypeId(objectTypeId);
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Entity entity) {
        //TODO: сделать проверку на то, что юзер существует
        service.saveEntity(entity);
        //TODO: поменять в сервисе void методы на Long, чтобы можно было возвращать код результата/ошибки
    }
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Entity oldEntity, @RequestBody Entity newEntity) {
        //TODO: сделать проверку на то, что юзер существует
        service.updateEntity(oldEntity, newEntity);
        //TODO: поменять в сервисе void методы на Long, чтобы можно было возвращать код результата/ошибки
    }
}
