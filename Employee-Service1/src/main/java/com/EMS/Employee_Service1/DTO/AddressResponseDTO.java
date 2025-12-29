package com.EMS.Employee_Service1.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressResponseDTO {
	
	private String type;
	private String city;
	private String state;

}
