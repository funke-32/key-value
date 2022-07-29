package com.macrometa.kv.messaging;

import com.macrometa.kv.config.MessageConfig.Operation;
import com.macrometa.kv.model.KeyValue;
import com.macrometa.kv.service.KeyValueCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import static com.macrometa.kv.config.MessageConfig.OPERATION_HEADER;

@Slf4j
public class CacheConsumer {

    @Autowired
    private Queue cacheQ;

    @Autowired
    private KeyValueCacheService keyValueCacheService;

    @RabbitListener(queues = "#{cacheQ.getName()}")
    public void receive(@Payload KeyValue keyValue, @Header(OPERATION_HEADER) Operation operation) {
        log.info("message received message = {}, operation = {}", keyValue.toString(), operation.toString());
        receiveMsg(keyValue, operation);
    }

    void receiveMsg(KeyValue keyValue, Operation operation) {
        switch (operation){
            case ADD:
                keyValueCacheService.addInCache(keyValue);
                break;
            case REMOVE:
                keyValueCacheService.removeFromCache(keyValue.getKey());
                break;
            case CLEAR:
                keyValueCacheService.clearCache();
                break;
        }
    }

}