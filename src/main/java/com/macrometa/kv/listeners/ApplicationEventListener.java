package com.macrometa.kv.listeners;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventListener {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private EurekaClient discoveryClient;

    @EventListener
    @SneakyThrows
    public void onApplicationEvent(ContextRefreshedEvent event) {
        while (true){
            if(null != discoveryClient.getApplication(applicationName)){
                discoveryClient.getApplicationInfoManager().setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);
                break;
            } else {
                Thread.sleep(1000);
            }
        }
    }
}
