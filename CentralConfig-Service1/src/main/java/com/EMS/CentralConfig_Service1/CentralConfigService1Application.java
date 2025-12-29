package com.EMS.CentralConfig_Service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CentralConfigService1Application {

	public static void main(String[] args) {
		SpringApplication.run(CentralConfigService1Application.class, args);
	}

}
