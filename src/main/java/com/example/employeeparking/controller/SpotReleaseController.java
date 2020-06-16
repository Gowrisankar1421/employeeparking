package com.example.employeeparking.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeeparking.dto.RequestResponseDto;
import com.example.employeeparking.service.SpotReleaseService;
/**
 * 
 * @author Divya
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/responses")
public class SpotReleaseController {
	
	Logger logger = LoggerFactory.getLogger(SpotReleaseController.class);
	
	@Autowired
	SpotReleaseService spotReleaseService;
	
	
	/**
	 * 
	 * @param employeeId
	 * @param startDate
	 * @param endDate
	 * @return ResponseEntity<RequestResponseDto>
	 */
	@PostMapping("/")
	public ResponseEntity<RequestResponseDto> releaseSpot(@RequestParam Long employeeId,@RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate startDate,
								@RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate endDate) {
		logger.info("<-----------------<inside release spot controller>------------------>");
		RequestResponseDto requestResponseDto = new RequestResponseDto();
		requestResponseDto = spotReleaseService.releaseSpot(employeeId,startDate,endDate);
		return new ResponseEntity<>(requestResponseDto,HttpStatus.OK);
	}

}
