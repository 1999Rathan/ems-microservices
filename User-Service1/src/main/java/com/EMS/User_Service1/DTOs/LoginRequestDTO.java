package com.EMS.User_Service1.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;

}
