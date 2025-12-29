package com.EMS.Employee_Service1.DTO;

import lombok.Data;
import java.util.List;

@Data
public class EmployeeRequestDTO {
	
	private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Long departmentId;

    private List<AddressRequestDTO> addresses;

}
