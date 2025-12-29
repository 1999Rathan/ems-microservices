package com.EMS.Employee_Service1.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.Employee_Service1.Entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findByUserId(Long userId);
}
