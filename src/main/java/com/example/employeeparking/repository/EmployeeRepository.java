package com.example.employeeparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeeparking.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
