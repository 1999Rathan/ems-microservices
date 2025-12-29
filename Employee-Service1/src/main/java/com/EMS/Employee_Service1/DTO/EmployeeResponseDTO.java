package com.EMS.Employee_Service1.DTO;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class EmployeeResponseDTO {

	private Long id;
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private Long departmentId;
	private boolean active;
	private DepartmentResponseDto department;

	private List<AddressResponseDTO> addresses;

}
