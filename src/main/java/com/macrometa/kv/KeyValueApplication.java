package com.macrometa.kv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KeyValueApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeyValueApplication.class, args);
	}

}
