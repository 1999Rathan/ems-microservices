package com.EMS.Employee_Service1.Service;

import org.springframework.stereotype.Service;

import com.EMS.Employee_Service1.Client.DepartmentClient;
import com.EMS.Employee_Service1.DTO.DepartmentResponseDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentGatewayService {
	
	private final DepartmentClient departmentClient;
	
	@CircuitBreaker(
	        name = "departmentService",
	        fallbackMethod = "departmentFallback"
	    )
	    public DepartmentResponseDto fetchDepartment(Long departmentId) {
	        return departmentClient.getDertment(departmentId);
	    }

	    public DepartmentResponseDto departmentFallback(Long departmentId, Throwable ex) {
	        return DepartmentResponseDto.builder()
	                .id(departmentId)
	                .name("Department Service Temporarily Unavailable")
	                .build();
	    }

}
