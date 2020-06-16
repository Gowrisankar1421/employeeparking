package com.example.employeeparking.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeeparking.model.Employee;
import com.example.employeeparking.model.SpotRequest;

public interface SpotRequestRepository extends JpaRepository<SpotRequest, Long> {

	List<SpotRequest> findByDateGreaterThanAndStatus(LocalDate date, String string);

	SpotRequest findSpotRequestByEmployeeAndDate(Employee employee, LocalDate startDate);

	List<SpotRequest> findByEmployee(Employee employee);

}
