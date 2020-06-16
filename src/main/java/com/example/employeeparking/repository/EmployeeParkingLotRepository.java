package com.example.employeeparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeeparking.model.Employee;
import com.example.employeeparking.model.EmployeeParkingLot;

public interface EmployeeParkingLotRepository extends JpaRepository<EmployeeParkingLot, Long> {

	EmployeeParkingLot findByEmployee(Employee employee);

}
