package com.macrometa.kv.dao;

import com.macrometa.kv.model.KeyValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface KeyValueRepository extends MongoRepository<KeyValue,String> {

    @Query(value = "{}", fields = "{ key : 1, value : 0 }")
    Page<KeyValue> findAllKeys(Pageable pageRequest);

    @Query(value = "{}", fields = "{ key : 0, value : 1 }")
    Page<KeyValue> findAllValues(Pageable pageRequest);
}
