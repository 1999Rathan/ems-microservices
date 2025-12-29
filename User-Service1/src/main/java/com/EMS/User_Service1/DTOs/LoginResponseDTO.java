package com.EMS.User_Service1.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
	
	private Long userId;
	private String username;
	private String role;
	private String token;

}
