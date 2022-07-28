package com.macrometa.kv.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceInstanceResource {

    @Autowired
    private EurekaClient discoveryClient;

    @Value("${spring.application.name}")
    private String applicationName;

    @RequestMapping("/services")
    public Applications serviceInstances() {
        return this.discoveryClient.getApplications(applicationName);
    }

    @RequestMapping("/up")
    public ResponseEntity<String> serviceUp() {
        discoveryClient.getApplicationInfoManager().setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/down")
    public ResponseEntity<String> serviceDown() {
        discoveryClient.getApplicationInfoManager().setInstanceStatus(InstanceInfo.InstanceStatus.DOWN);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
