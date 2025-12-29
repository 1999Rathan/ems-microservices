package com.EMS.Employee_Service1.Controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.Employee_Service1.DTO.AddressUpdateRequestDto;
import com.EMS.Employee_Service1.DTO.EmployeeRequestDTO;
import com.EMS.Employee_Service1.DTO.EmployeeResponseDTO;
import com.EMS.Employee_Service1.DTO.EmployeeUpdateRequestDto;
import com.EMS.Employee_Service1.Service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
	
	private final EmployeeService employeeService;
	
	/**
     * ADMIN only
     * Creates an employee profile for a user
     */
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @RequestBody EmployeeRequestDTO request,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role
    ) {
        EmployeeResponseDTO response =
                employeeService.createEmployee(request, userId, role);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	@GetMapping("/me")
	public ResponseEntity<EmployeeResponseDTO> getMyProfile(
	        @RequestHeader("X-User-Id") Long userId
	) {
	    return ResponseEntity.ok(employeeService.getMyProfile(userId));
	}
	
	@GetMapping
	public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployees(
			@RequestHeader("X-User-Role") String role,
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size) throws AccessDeniedException {
		
		if(!"ADMIN".equals(role) ) {
			throw new AccessDeniedException("Only admin can access");
		}
		
		return ResponseEntity.ok(employeeService.getAll(page,size));
		
	}
	
	@PatchMapping
	public ResponseEntity<EmployeeResponseDTO> updateEmployee(
			@RequestHeader("X-User-Id") Long userId,
			@RequestBody EmployeeUpdateRequestDto dto) {
		
		return ResponseEntity.ok(employeeService.updateEmployee(userId, dto));
	}
	
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id,
			@RequestHeader("X-User-Role") String role) throws AccessDeniedException {
		
		if(!"ADMIN".equals(role)) {
			throw new AccessDeniedException("Only admin can delete employee");
		}
		
		employeeService.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PatchMapping("/addresses/{addressId}")
	public ResponseEntity<EmployeeResponseDTO> updateAddress(
	        @RequestHeader("X-User-Id") Long userId,
	        @PathVariable Long addressId,
	        @RequestBody AddressUpdateRequestDto dto) {

	    return ResponseEntity.ok(
	            employeeService.updateAddress(userId, addressId, dto)
	    );
	}

	@DeleteMapping("/addresses/{addressId}")
	public ResponseEntity<Void> deleteAddress(
	        @RequestHeader("X-User-Id") Long userId,
	        @PathVariable Long addressId) {

	    employeeService.deleteAddress(userId, addressId);
	    return ResponseEntity.noContent().build();
	}

	

}
