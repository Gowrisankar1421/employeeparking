package com.example.employeeparking.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeeparking.dto.RequestResponseDto;
import com.example.employeeparking.exceptions.NoFreeSpotsFoundException;
import com.example.employeeparking.exceptions.RequestNotProcessedException;
import com.example.employeeparking.exceptions.SpotRequestNotFoundException;
import com.example.employeeparking.model.SpotRequest;
import com.example.employeeparking.service.SpotRequestService;

/**
 * 
 * @author Gowri sankar
 * @author sai kumar
 * @author sowjanya
 * version:1.0
 * this is class for spot request controller
 *
 */
@RestController
@RequestMapping("/requests")
public class SpotRequestController {
	Logger logger = LoggerFactory.getLogger(SpotRequestController.class);
	
	@Autowired
	SpotRequestService spotRequestService;
	
	
	/**
	 * 
	 * @param employeeId
	 * @param startDate
	 * @param endDate
	 * @return ResponseEntity<RequestResponseDto>
	 */
	@PostMapping("/")
	public ResponseEntity<RequestResponseDto> raiseRequest(@RequestParam Long employeeId,@RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate startDate,
								@RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate endDate) {
		logger.info("<-------------------<inside raise request controller method>---------------->");
		RequestResponseDto requestResponseDto = spotRequestService.raiseRequest(employeeId,startDate,endDate);
		return new ResponseEntity<>(requestResponseDto,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @return String as status message
	 * @throws RequestNotProcessedException
	 * @throws SpotRequestNotFoundException
	 * @throws NoFreeSpotsFoundException
	 */
	
	@GetMapping("/LotterySystem")
	public ResponseEntity<RequestResponseDto> requestProcessingByLotterySystem() throws RequestNotProcessedException, SpotRequestNotFoundException, NoFreeSpotsFoundException {
		RequestResponseDto message =spotRequestService.requestProcessing();
		logger.info("requestProcessingByLotterySystem in Spot request controller");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param employeeId
	 * @return
	 * @throws SpotRequestNotFoundException
	 */
	@GetMapping("/search")
	public ResponseEntity<List<SpotRequest>> getEmployeeById(@RequestParam long  employeeId) throws SpotRequestNotFoundException {
		logger.info("inside get employee by id with spot controller method");
		List<SpotRequest> EmployeeParkingLot = spotRequestService.getByEmployeeId(employeeId);
		return new ResponseEntity<>(EmployeeParkingLot, HttpStatus.OK);
	}

}
