package com.EMS.Employee_Service1.Mapper;

import java.util.stream.Collectors;

import com.EMS.Employee_Service1.DTO.AddressResponseDTO;
import com.EMS.Employee_Service1.DTO.EmployeeResponseDTO;
import com.EMS.Employee_Service1.Entity.Employee;

public class EmployeeMapper {
	
	public static EmployeeResponseDTO toDto(Employee employee) {

        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .userId(employee.getUserId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .departmentId(employee.getDepartmentId())
                .active(employee.isActive())
                .addresses(
                		employee.getAddresses().stream()
                				.map(address -> 
                						AddressResponseDTO.builder()
                							.type(address.getType())
                							.city(address.getCity())
                							.state(address.getState())
                							.build()
                					)
                					.collect(Collectors.toList())
                	)
                .build();
    }

}
