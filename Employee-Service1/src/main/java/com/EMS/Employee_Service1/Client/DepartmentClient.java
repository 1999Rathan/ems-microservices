package com.EMS.Employee_Service1.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.EMS.Employee_Service1.DTO.DepartmentResponseDto;

@FeignClient(name = "Department-Service1")
public interface DepartmentClient {
	
	@GetMapping("/department/{id}")
	DepartmentResponseDto getDertment(@PathVariable Long id);

}
