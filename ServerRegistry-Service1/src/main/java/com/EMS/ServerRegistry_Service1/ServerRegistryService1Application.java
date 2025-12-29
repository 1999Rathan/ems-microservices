package com.EMS.ServerRegistry_Service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServerRegistryService1Application {

	public static void main(String[] args) {
		SpringApplication.run(ServerRegistryService1Application.class, args);
	}

}
