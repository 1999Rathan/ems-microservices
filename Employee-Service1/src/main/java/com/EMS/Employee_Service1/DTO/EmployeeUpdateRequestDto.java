package com.EMS.Employee_Service1.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeUpdateRequestDto {
	
	private String firstName;
    private String lastName;
    private String phone;
    private String email;

}
