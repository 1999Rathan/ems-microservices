package com.EMS.Employee_Service1.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.EMS.Employee_Service1.Client.DepartmentClient;
import com.EMS.Employee_Service1.DTO.AddressUpdateRequestDto;
import com.EMS.Employee_Service1.DTO.DepartmentResponseDto;
import com.EMS.Employee_Service1.DTO.EmployeeRequestDTO;
import com.EMS.Employee_Service1.DTO.EmployeeResponseDTO;
import com.EMS.Employee_Service1.DTO.EmployeeUpdateRequestDto;
import com.EMS.Employee_Service1.Entity.Address;
import com.EMS.Employee_Service1.Entity.Employee;
import com.EMS.Employee_Service1.Exception.ResourceNotFoundException;
import com.EMS.Employee_Service1.Mapper.EmployeeMapper;
import com.EMS.Employee_Service1.Repository.EmployeeRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeRepository repository;
	
	private final DepartmentGatewayService departmentGatewayService;

	public EmployeeResponseDTO createEmployee(
	        EmployeeRequestDTO request,
	        Long userId,
	        String role
	) {
	    // role check (ADMIN)
	    if (!"ADMIN".equals(role)) {
	        throw new RuntimeException("Only ADMIN can create employee");
	    }

	    // business logic
	    Employee employee = Employee.builder()
	            .userId(userId)
	            .firstName(request.getFirstName())
	            .lastName(request.getLastName())
	            .email(request.getEmail())
	            .phone(request.getPhone())
	            .departmentId(request.getDepartmentId())
	            .active(true)
	            .build();

	    if (request.getAddresses() != null) {
	        request.getAddresses().forEach(addr -> {
	            Address address = Address.builder()
	                    .type(addr.getType())
	                    .city(addr.getCity())
	                    .state(addr.getState())
	                    .employee(employee)
	                    .build();
	            employee.getAddresses().add(address);
	        });
	    }

	    Employee saved = repository.save(employee);
	    return EmployeeMapper.toDto(saved);
	}
	
//	@CircuitBreaker(
//		name = "departmentService",
//		fallbackMethod = "departmentFallback"
//	)
//	public DepartmentResponseDto fetchDepartment(Long departmentId) {
//		return departmentClient.getDertment(departmentId);	
//	}
	
//	public DepartmentResponseDto departmentFallback(Long departmentId,Throwable ex) {
//		
//		return DepartmentResponseDto.builder()
//					.id(departmentId)
//					.name("Department Service Temporarily unavailable")
//					.build();
//		
//	}

	
	public EmployeeResponseDTO getMyProfile(Long loggedUserId) {
		
		Employee employee = repository.findByUserId(loggedUserId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not found"));
		
		DepartmentResponseDto department = 
				departmentGatewayService.fetchDepartment(employee.getDepartmentId());
		
		EmployeeResponseDTO dto = EmployeeMapper.toDto(employee);
				dto.setDepartment(department);
		
		return dto;
		
	}
	
	public Page<EmployeeResponseDTO> getAll(int page,int size) {
		
		Pageable pageable = PageRequest.of(page,size);
		
		return repository.findAll(pageable)
					.map(EmployeeMapper::toDto);
		
	}
	
	public EmployeeResponseDTO updateEmployee(Long userId, EmployeeUpdateRequestDto dto) {
		
		Employee employee = repository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not found"));
		
		if(dto.getFirstName() != null) {
			employee.setFirstName(dto.getFirstName());	
		}
		
		if(dto.getLastName() != null) {
			employee.setLastName(dto.getLastName());	
		}
		
		if(dto.getPhone() != null) {
			employee.setPhone(dto.getPhone());	
		}
		
		if(dto.getEmail() != null) {
			employee.setEmail(dto.getEmail());	
		}
		
		return EmployeeMapper.toDto(repository.save(employee));
				
	}
	
	public void deleteById(Long id) {
		
		Employee employee = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Found"));
		
		repository.delete(employee);;
		
	}
	
	public EmployeeResponseDTO updateAddress(
			Long userId,
			Long addressId,
			AddressUpdateRequestDto dto) {
		
		Employee employee = repository.findByUserId(userId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee Not Found"));
		
		Address address = employee.getAddresses().stream()
				.filter(e -> e.getId().equals(addressId))
				.findFirst()
				.orElseThrow(()-> new ResourceNotFoundException("Address Not Found"));
		
		if(dto.getType() != null) address.setType(dto.getType());
		if(dto.getCity() != null) address.setCity(dto.getCity());
		if(dto.getState() != null) address.setState(dto.getState());
		
		repository.save(employee);
		
		return EmployeeMapper.toDto(employee);
				
	}
	
	public void deleteAddress(Long userId, Long addressId) {

	    Employee employee = repository.findByUserId(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

	    boolean removed = employee.getAddresses()
	            .removeIf(a -> a.getId().equals(addressId));

	    if (!removed) {
	        throw new ResourceNotFoundException("Address not found");
	    }

	    repository.save(employee); // orphanRemoval deletes address
	}




}
