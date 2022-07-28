package com.macrometa.kv.messaging;

import com.macrometa.kv.config.MessageConfig.Operation;
import com.macrometa.kv.model.KeyValue;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import static com.macrometa.kv.config.MessageConfig.OPERATION_HEADER;


public class CachePublisher {

    @Autowired
    private FanoutExchange exchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishMessage(KeyValue keyValue, Operation operation) {
        rabbitTemplate.convertAndSend(exchange.getName(), "", keyValue, m -> {
            m.getMessageProperties().getHeaders().put(OPERATION_HEADER, operation);
            return m;
        });
    }
}