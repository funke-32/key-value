package com.macrometa.kv.service;

import com.macrometa.kv.model.KeyValue;

import java.util.Collection;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;


public abstract class KeyValueService {

    protected void validate(KeyValue keyValue){
        if(null == keyValue || isBlank(keyValue.getKey()) || isBlank(keyValue.getValue())){
            throw new IllegalArgumentException("key or Value is null/emply");
        }
        if(keyValue.getKey().length() > 64){
            throw new IllegalArgumentException("key length should be less or equal to 64 chars : " + keyValue.getKey());
        }
        if(keyValue.getValue().length() > 256){
            throw new IllegalArgumentException("value length should be less or equal to 256 chars : " + keyValue.getValue());
        }
    }

    public abstract KeyValue set(KeyValue keyValue);

    public abstract Optional<KeyValue> get(String key);

    public abstract KeyValue remove(String key);

    public abstract void clear();

    public abstract boolean exists(String key);

    public abstract Collection<KeyValue> getKeys(Integer page, Integer size);

    public abstract Collection<KeyValue> getValues(Integer page, Integer size);

    public abstract Collection<KeyValue> getAll(Integer page, Integer size);
}
