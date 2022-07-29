package com.macrometa.kv.config;


import com.macrometa.kv.messaging.CacheConsumer;
import com.macrometa.kv.messaging.CachePublisher;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("cache")
@Configuration
public class MessageConfig {

    public static final String OPERATION_HEADER = "operation";
    public static final String FANOUT_EXCHANGE_NAME = "cachebalance.fanout";
    public enum Operation {ADD, REMOVE, CLEAR};

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Bean
    public Queue cacheQ() {
        Queue queue =  new AnonymousQueue();
        amqpAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public FanoutExchange exchange(){
        FanoutExchange fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE_NAME, true, false);
        amqpAdmin.declareExchange(fanoutExchange);
        return fanoutExchange;
    }

    @Bean
    public Binding binding(Queue cacheQ, FanoutExchange exchange) {
        Binding binding = BindingBuilder
                .bind(cacheQ)
                .to(exchange);
        amqpAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    public CacheConsumer cacheConsumer() {
        return new CacheConsumer();
    }

    @Bean
    public CachePublisher cachePublisher() {
        return new CachePublisher();
    }

}