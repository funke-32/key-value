package com.macrometa.kv.config;


import com.macrometa.kv.messaging.CachePublisher;
import com.macrometa.kv.messaging.CacheConsumer;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("cache")
@Configuration
public class MessageConfig {

    @Value("${fanout.exchange.name}")
    private String fanoutName;

    public static final String OPERATION_HEADER = "operation";
    public enum Operation {ADD, REMOVE, CLEAR};

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(fanoutName);
    }

    @Bean
    public Queue cacheQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(FanoutExchange fanout, Queue deleteQueue1) {
        return BindingBuilder.bind(deleteQueue1).to(fanout);
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