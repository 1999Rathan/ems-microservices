package com.EMS.Employee_Service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmployeeService1Application {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeService1Application.class, args);
	}

}
