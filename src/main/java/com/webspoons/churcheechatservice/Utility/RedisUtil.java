package com.webspoons.churcheechatservice.Utility;

import com.amazonaws.services.dynamodbv2.xspec.M;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.lang.Nullable;
import com.webspoons.churcheechatservice.model.Member;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil<T> {

    private RedisTemplate<String, T> redisTemplate;
    private ValueOperations<String, T> valueOperations;
    private ListOperations<String, T> listOperations;
    private SetOperations<String, T> setOperations;
    private HashOperations<String, Integer, T> hashOperations;
    private ObjectMapper objectMapper;

    @Autowired
    public RedisUtil(RedisTemplate<String, T> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.listOperations = redisTemplate.opsForList();
        this.setOperations = redisTemplate.opsForSet();
        this.hashOperations = redisTemplate.opsForHash();
        this.objectMapper = objectMapper;
    }

    //region String
    public void addValue(String key, @Nullable String subKey, T value) throws JsonProcessingException {
        String  valueToSave;
        if(subKey != null)
            key = key.concat("::").concat(subKey);
        if(!(value instanceof String))
            valueToSave = objectMapper.writeValueAsString(value);
        else
            valueToSave = (String) value;
        valueOperations.set(key, (T)valueToSave);
    }

    public T getValue(String key, @Nullable String subKey, Class<T> returnType) throws JsonProcessingException {
        if(subKey != null)
            key = key.concat("::").concat(subKey);
        var value = (String)valueOperations.get(key);
        if(value instanceof String){
            var valueToSave = objectMapper.readValue(value, returnType);
            return valueToSave;
        }
        return null;
    }

    public void setExpire(String key, @Nullable String subKey, long timeout, TimeUnit unit) {
        if(subKey != null)
            key = key.concat("::").concat(subKey);
        redisTemplate.expire(key, timeout, unit);
    }

    public void delete(String key, @Nullable String subKey) {
        if(subKey != null)
            key = key.concat("::").concat(subKey);
        redisTemplate.delete(key);
    }

    public boolean haskey(String key, @Nullable String subKey) {
        if(subKey != null)
            key = key.concat("::").concat(subKey);
        return redisTemplate.hasKey(key);
    }
    //endregion


    //region List
    public void addList(String key, @Nullable String subKey, T value) throws JsonProcessingException {
        if(subKey != null)
            key = key.concat("::").concat(subKey);
        var valueToSave = objectMapper.writeValueAsString(value);
        listOperations.leftPush(key, (T)valueToSave);
    }

    public void addList(String key, @Nullable String subKey, List<T> value) throws JsonProcessingException {
        if(subKey != null)
            key = key.concat("::").concat(subKey);
        for(T val: value){
            var valueToSave = objectMapper.writeValueAsString(val);
            listOperations.leftPush(key, (T)valueToSave);
        }
    }

    public List<T> getListMembers(String key, @Nullable String subKey, Class<T> returnType) throws IOException {
        var returnValue = new ArrayList<T>();
        if(subKey != null)
            key = key.concat("::").concat(subKey);
        var value = listOperations.range(key, 0, -1);
        for(T val : value){
            returnValue.add(objectMapper.readValue(val.toString(), returnType));
        }
        return returnValue;
    }

    public Long getListSize(String key, @Nullable String subKey) {
        if(subKey != null)
            key = key.concat("::").concat(subKey);
        return listOperations.size(key);
    }
    //endregion


    //region Set
    public void addToSet(String key, T... values) {
        setOperations.add(key, values);
    }

    public Set<T> getSetMembers(String key) {
        return setOperations.members(key);
    }
    //endregion


    //region Hash
    public void saveHash(String key, Integer id, T value) {
        hashOperations.put(key, id, value);
    }

    public T findInHash(String key, int id) {
        return hashOperations.get(key, id);
    }

    public void deleteHash(String key, int id) {
        hashOperations.delete(key, id);
    }
}
