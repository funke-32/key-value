package com.macrometa.kv.messaging;

import com.macrometa.kv.config.MessageConfig.Operation;
import com.macrometa.kv.model.KeyValue;
import com.macrometa.kv.service.KeyValueCacheService;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;

import static com.macrometa.kv.config.MessageConfig.OPERATION_HEADER;

public class CacheConsumer {

    @Autowired
    private KeyValueCacheService keyValueCacheService;

    @Autowired
    private FanoutExchange exchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "#{cacheQueue.name}")
    public void receive(KeyValue keyValue, @Header(OPERATION_HEADER) Operation operation) {
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