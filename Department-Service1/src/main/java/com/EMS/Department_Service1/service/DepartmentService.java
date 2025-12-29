package com.EMS.Department_Service1.service;

import org.springframework.stereotype.Service;

import com.EMS.Department_Service1.Entity.Department;
import com.EMS.Department_Service1.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
	
	private final DepartmentRepository departmentRepository;
	
	public Department create(String name) {
		
		Department department = Department.builder()
				.name(name)
				.build();
		
		return departmentRepository.save(department);
	}
	
	public Department findById(Long id) {
		
		return departmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Department not found"));
		
	}
	
	

}
