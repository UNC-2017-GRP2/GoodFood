package com.netcracker.service.impl;
import com.netcracker.config.Constant;
import com.netcracker.model.Entity;
import com.netcracker.model.MapParameter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class ServiceImpl implements com.netcracker.service.Service {

    final String ENTITY_BASE_URL = Constant.BASE_URL_REST + "/entity";

    @Override
    public Entity getEntityById(BigInteger objectId, long objectTypeId) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Entity> userResponse =
                restTemplate.exchange(ENTITY_BASE_URL+"/id/" + objectId + "/typeid/"+objectTypeId+'/',
                        HttpMethod.GET, null, new ParameterizedTypeReference<Entity>() {
                        });
        Entity result = userResponse.getBody();

        return result;
    }

    @Override
    public Entity getEntityByName(String name, long objectTypeId) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Entity> userResponse =
                restTemplate.exchange(ENTITY_BASE_URL+"/name/" + name + "/typeid/"+objectTypeId+'/',
                        HttpMethod.GET, null, new ParameterizedTypeReference<Entity>() {
                        });
        Entity result = userResponse.getBody();

        return result;
    }

    @Override
    public List<Entity> getEntitiesByObjectTypeId(long objectTypeId) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Entity>> userResponse =
                restTemplate.exchange(ENTITY_BASE_URL+"/typeid/"+objectTypeId+'/',
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Entity>>() {
                        });
        List<Entity> result = userResponse.getBody();

        return result;
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

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Entity> request = new HttpEntity<>(entity);

        restTemplate.exchange(ENTITY_BASE_URL+"/save/",
                HttpMethod.POST, request, new ParameterizedTypeReference<Entity>() {
                });

    }

    @Override
    public void updateEntity(Entity oldEntity, Entity newEntity) {
        RestTemplate restTemplate = new RestTemplate();
        Map req_payload = new HashMap();
        req_payload.put("oldEntity", oldEntity);
        req_payload.put("newEntity", newEntity);
        HttpEntity<?> request = new HttpEntity<>(req_payload);

        restTemplate.exchange(ENTITY_BASE_URL+"/update/",
                HttpMethod.PUT, request, new ParameterizedTypeReference<Entity>() {
                });
    }
}
