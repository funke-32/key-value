package com.macrometa.kv.service;

import com.macrometa.kv.messaging.CachePublisher;
import com.macrometa.kv.model.KeyValue;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.macrometa.kv.config.MessageConfig.Operation.*;

@Profile("cache")
@Component
public class KeyValueCacheService implements KeyValueService {

    private Map<String, KeyValue> keyValueCache = new ConcurrentHashMap<>();

    @Autowired
    private CachePublisher cachePublisher;

    @Override
    public KeyValue set(KeyValue keyValue) {
        addInCache(keyValue);
        cachePublisher.publishMessage(keyValue, ADD);
        return keyValue;
    }

    @Override
    public Optional<KeyValue> get(String key) {
        return Optional.ofNullable(keyValueCache.get(key));
    }

    @Override
    public void remove(String key) {
        removeFromCache(key);
        cachePublisher.publishMessage(KeyValue.builder().key(key).build(), REMOVE);
    }


    @Override
    public void clear() {
        clearCache();
        cachePublisher.publishMessage(KeyValue.builder().build(), CLEAR);
    }

    @Override
    public boolean exists(String key) {
        return keyValueCache.containsKey(key);
    }

    @Override
    public Collection<KeyValue> getKeys(Integer page, Integer size) {

        ArrayList<String> values = new ArrayList<>();
        int fromIndex = size * page;
        int toIndex = fromIndex + size;

        if (toIndex > keyValueCache.size()) {
            toIndex = keyValueCache.size() - 1;
        }

        if(!keyValueCache.isEmpty()) {
            values.addAll(keyValueCache.keySet());
            return values.subList(fromIndex, toIndex).stream().map(k -> KeyValue.builder().key(k).build()).collect(Collectors.toList());
        }
        return CollectionUtils.EMPTY_COLLECTION;
    }

    @Override
    public Collection<KeyValue> getValues(Integer page, Integer size) {

        ArrayList<KeyValue> values = new ArrayList<>();
        int fromIndex = size * page;
        int toIndex = fromIndex + size;

        if (toIndex > keyValueCache.size()) {
            toIndex = keyValueCache.size() - 1;
        }

        if(!keyValueCache.isEmpty()){
            values.addAll(keyValueCache.values());
            return values.subList(fromIndex, toIndex).stream().map(k -> KeyValue.builder().value(k.getValue()).build()).collect(Collectors.toList());
        }
        return CollectionUtils.EMPTY_COLLECTION;
    }

    @Override
    public Collection<KeyValue> getAll(Integer page, Integer size) {
        return null;
    }

    public void addInCache(KeyValue keyValue) {
        keyValueCache.putIfAbsent(keyValue.getKey(), keyValue);
    }

    public void removeFromCache(String key) {
        keyValueCache.remove(key);
    }

    public void clearCache(){
        keyValueCache.clear();
    }

}
