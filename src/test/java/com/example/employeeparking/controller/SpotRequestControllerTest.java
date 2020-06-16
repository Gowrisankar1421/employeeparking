package com.example.employeeparking.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import com.example.employeeparking.dto.RequestResponseDto;
import com.example.employeeparking.exceptions.SpotRequestNotFoundException;
import com.example.employeeparking.model.SpotRequest;
import com.example.employeeparking.service.SpotRequestService;

@RunWith(MockitoJUnitRunner.Silent.class)
	public class SpotRequestControllerTest {

	    @InjectMocks
	    SpotRequestController spotRequestController;
	    @Mock
	    SpotRequestService spotRequestService;
	    
	    RequestResponseDto responseDto = new RequestResponseDto();
	    
	    @SuppressWarnings("deprecation")
		@Test
	    public void testRaiseRequest() {
	    	RequestResponseDto requestResponseDto = new RequestResponseDto();
	    	LocalDate startDate=LocalDate.of(2020, 06, 20);
	    	LocalDate endDate = LocalDate.of(2020, 06, 19);
	    	Mockito.when(spotRequestService.raiseRequest(1L,startDate,endDate)).thenReturn(requestResponseDto);
	    	Assert.notNull(requestResponseDto);
	    	ResponseEntity<RequestResponseDto> response = spotRequestController.raiseRequest(1L, startDate, endDate);
	    	Assert.notNull(response.getBody());
	    }
	    
	    @SuppressWarnings("deprecation")
		@Test
	    public void testCreateUserForPositive() throws Exception {
	       
	    	responseDto.setMessage("sucessful");
	    	responseDto.getMessage();
	        Mockito.when(spotRequestService.requestProcessing()).thenReturn(responseDto);
	        ResponseEntity<RequestResponseDto> responseDto1 = spotRequestController.requestProcessingByLotterySystem();
	        Assert.notNull(responseDto1);
	    }
	    @SuppressWarnings("deprecation")
		@Test(expected = NullPointerException.class)
	    public void testCreateUserForExce() throws Exception {
	    	 Mockito.when(spotRequestService.requestProcessing()).thenThrow(NullPointerException.class);
		    ResponseEntity<RequestResponseDto> responseDto1 = spotRequestController.requestProcessingByLotterySystem();
		    Assert.notNull(responseDto1);
	    }	  
	    
	    @SuppressWarnings("deprecation")
	    @Test
	    public void testGetEmployeeById() throws SpotRequestNotFoundException {
	    	List<SpotRequest> employeeParkingLot = new ArrayList<>();
	    	Mockito.when(spotRequestService.getByEmployeeId(1L)).thenReturn(employeeParkingLot);
	    	Assert.notNull(employeeParkingLot);
	    	ResponseEntity<List<SpotRequest>> response = spotRequestController.getEmployeeById(1L);
	    	Assert.notNull(response.getBody());
	    }
	    
	}
