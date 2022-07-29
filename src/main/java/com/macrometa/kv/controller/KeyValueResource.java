package com.macrometa.kv.controller;

import com.macrometa.kv.model.KeyValue;
import com.macrometa.kv.service.KeyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.Optional;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@RestController
public class KeyValueResource {

    @Autowired
    private KeyValueService service;

    @GetMapping(path = "/set")
    public KeyValue set(@RequestParam(name = "k") String key, @RequestParam(name = "v") String value){
        return service.set(KeyValue.builder().key(key).value(value).build());
    }

    @GetMapping(path = "/get")
    public ResponseEntity get(@RequestParam(name = "k") String key){
        Optional<KeyValue> keyValueOption = service.get(key);
        if(keyValueOption.isPresent()){
            return new ResponseEntity<>(keyValueOption.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("null", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/is")
    public ResponseEntity is(@RequestParam(name = "k") String key){
        if(service.exists(key)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("null", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/getKeys")
    public ResponseEntity getKeys(@RequestParam(required = false,  defaultValue = "0") @Min(0) @Max(100) int page, @RequestParam(required = false,  defaultValue = "10") @Min(0) @Max(100) int size){

        Collection<KeyValue> keys = service.getKeys(page, size);
        if(isNotEmpty(keys)){
            return new ResponseEntity<>(keys, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("null", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/getValues")
    public ResponseEntity<Collection<KeyValue>> getValues(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(100)  int page, @RequestParam(required = false,  defaultValue = "10") @Min(0) @Max(100) int size){

        Collection<KeyValue> keys = service.getValues(page, size);
        if(isNotEmpty(keys)){
            return new ResponseEntity<>(keys, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity getAll(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(100)  int page, @RequestParam(required = false,  defaultValue = "10") @Min(0) @Max(100) int size){

        Collection<KeyValue> keyValues = service.getAll(page, size);
        if(isNotEmpty(keyValues)){
            return new ResponseEntity<>(keyValues, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("null", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/rm")
    public ResponseEntity remove(@RequestParam(name = "k") String key){
        if(null != service.remove(key)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("null",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/clear")
    public ResponseEntity clear(){
        service.clear();
        return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }
}

