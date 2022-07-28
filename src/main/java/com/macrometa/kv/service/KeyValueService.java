package com.macrometa.kv.service;

import com.macrometa.kv.model.KeyValue;

import java.util.Collection;
import java.util.Optional;

public interface KeyValueService {

    KeyValue set(KeyValue keyValue);

    Optional<KeyValue> get(String key);

    void remove(String key);

    void clear();

    boolean exists(String key);

    Collection<KeyValue> getKeys(Integer page, Integer size);

    Collection<KeyValue> getValues(Integer page, Integer size);

    Collection<KeyValue> getAll(Integer page, Integer size);
}
