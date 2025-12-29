package com.EMS.User_Service1.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.EMS.User_Service1.DTOs.LoginRequestDTO;
import com.EMS.User_Service1.DTOs.LoginResponseDTO;
import com.EMS.User_Service1.DTOs.RegisterRequetsDTO;
import com.EMS.User_Service1.DTOs.RegisterResponseDTO;
import com.EMS.User_Service1.Entity.User;
import com.EMS.User_Service1.Exception.InvalidCredentialsException;
import com.EMS.User_Service1.Exception.UserAlreadyExistsException;
import com.EMS.User_Service1.Exception.UserDisabledException;
import com.EMS.User_Service1.Security.JwtUtil;
import com.EMS.User_Service1.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	
	public RegisterResponseDTO register(RegisterRequetsDTO request) {
		
		if(repository.existsByUsername(request.getUsername())) {
			throw new UserAlreadyExistsException("Username already exist");
		}
		
		if(repository.existsByEmail(request.getEmail())) {
			throw new UserAlreadyExistsException("Email already exsit");
		}
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEmail(request.getEmail());
		user.setRole(request.getRole());
		user.setEnabled(true);
		
		User saved = repository.save(user);
		
		RegisterResponseDTO response = RegisterResponseDTO.builder()
				.userId(saved.getId())
				.username(saved.getUsername())
				.role(saved.getRole())
				.build();
		
		return response;
	}
	
	public LoginResponseDTO Login(LoginRequestDTO dto) {
		
		User user = repository.findByUsername(dto.getUsername())
				.orElseThrow(() -> new InvalidCredentialsException("Ivalid user or password"));
		
		if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new InvalidCredentialsException("Invalid Username or password");
		}
		
		if(!user.isEnabled()) {
			throw new UserDisabledException("User account is disabled");
		}
		
		String token = jwtUtil.generateToken(user);
		
		return LoginResponseDTO.builder()
				.userId(user.getId())
				.username(user.getUsername())
				.role(user.getRole())
				.token(token)
				.build();
	}
	

}
