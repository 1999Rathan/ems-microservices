package com.EMS.Department_Service1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.Department_Service1.Entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
