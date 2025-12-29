package com.EMS.User_Service1.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.User_Service1.DTOs.LoginRequestDTO;
import com.EMS.User_Service1.DTOs.LoginResponseDTO;
import com.EMS.User_Service1.DTOs.RegisterRequetsDTO;
import com.EMS.User_Service1.DTOs.RegisterResponseDTO;
import com.EMS.User_Service1.Service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService service;

	@PostMapping("/register")
	public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequetsDTO registerRequetsDTO){
		return ResponseEntity.ok(service.register(registerRequetsDTO));
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
		return ResponseEntity.ok(service.Login(dto));
	}
}
