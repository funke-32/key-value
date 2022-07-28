package com.macrometa.kv.service;

import com.macrometa.kv.dao.KeyValueRepository;
import com.macrometa.kv.model.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Profile("!cache")
@Component
public class KeyValueStoreService implements KeyValueService {

    @Autowired
    private KeyValueRepository repository;

    @Override
    public KeyValue set(KeyValue keyValue) {
        return repository.save(keyValue);
    }

    @Override
    public Optional<KeyValue> get(String key) {
        return repository.findById(key);
    }

    @Override
    public void remove(String key) {
        repository.deleteById(key);
    }

    @Override
    public void clear() {
        repository.deleteAll();
    }

    @Override
    public boolean exists(String key) {
        return repository.existsById(key);
    }

    @Override
    public Collection<KeyValue> getKeys(Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<KeyValue> keysPage = repository.findAllKeys(pageRequest);
        return keysPage.getContent();
    }

    @Override
    public Collection<KeyValue> getValues(Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<KeyValue> keysPage = repository.findAllValues(pageRequest);
        return keysPage.getContent();
    }

    @Override
    public Collection<KeyValue> getAll(Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<KeyValue> keysPage = repository.findAll(pageRequest);
        return keysPage.getContent();
    }


}
