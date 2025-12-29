package com.EMS.User_Service1.DTOs;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
	
	private int status;
	private String message;
	private LocalDateTime timeStamp;

}
