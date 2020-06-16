package com.example.employeeparking.service;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employeeparking.controller.SpotReleaseController;
import com.example.employeeparking.dto.RequestResponseDto;
import com.example.employeeparking.exceptions.EmployeeNotFoundException;
import com.example.employeeparking.model.Employee;
import com.example.employeeparking.model.EmployeeParkingLot;
import com.example.employeeparking.model.FreeSpot;
import com.example.employeeparking.repository.EmployeeParkingLotRepository;
import com.example.employeeparking.repository.EmployeeRepository;
import com.example.employeeparking.repository.FreeSpotRepository;

/**
 * 
 * @author Divya
 *  this is class for spot release service
 */
@Service
public class SpotReleaseService {
	
	Logger logger = LoggerFactory.getLogger(SpotReleaseService.class);
	
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	FreeSpotRepository freeSpotRepository;
	@Autowired
	EmployeeParkingLotRepository employeeParkingLotRepository;
	
	
	/**
	 * 
	 * @param employeeId
	 * @param startDate
	 * @param endDate
	 * @return RequestResponseDto
	 */
public RequestResponseDto releaseSpot(Long employeeId,LocalDate startDate,LocalDate endDate) {
	logger.info("<-----------------<inside release spot method>------------------->");	
	
		Optional<Employee> optional = employeeRepository.findById(employeeId);
		Employee employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
		}

		else {
			logger.warn("<-------------------<employee not found exception>------------------>");
			throw new EmployeeNotFoundException("employee not found");

		}
		EmployeeParkingLot employeeParkingLot = employeeParkingLotRepository.findByEmployee(employee);
		if (employee.getIsVip()) {
			for (LocalDate dates = startDate; dates
					.isBefore(endDate.plusDays(1)); dates = dates.plusDays(1)) {
				
				FreeSpot freeespot = new FreeSpot();
				freeespot.setEmployee(employee);
				System.out.println(employee);
				freeespot.setParkingLot(employeeParkingLot.getParkingLot());
				freeespot.setStatus("not assigned");
				freeespot.setDate(dates);
				freeSpotRepository.save(freeespot);
			}
			RequestResponseDto requestResponseDto = new RequestResponseDto();
			requestResponseDto.setMessage("spot released successfully");
			return requestResponseDto;

		} else {
			logger.warn("<-------------------<employee not found exception>------------------>");
			throw new EmployeeNotFoundException("employee is not vip ");
		}

	}
}
