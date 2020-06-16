package com.example.employeeparking.controller;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.example.employeeparking.dto.RequestResponseDto;
import com.example.employeeparking.model.Employee;
import com.example.employeeparking.service.SpotReleaseService;
@RunWith(MockitoJUnitRunner.Silent.class)
public class SpotReleaseControllerTest {
	private static final LocalDate endDate = null;

	private static final Long employeeId = null;

	private static final LocalDate startDate = null;

	@InjectMocks
	SpotReleaseController spotReleaseController;
    
    @Mock
    SpotReleaseService sportReleaseservice;
    @Test
    public void TestForSportReleaseForTest() {
        
    	RequestResponseDto response=new RequestResponseDto();
    	response.setMessage("paraking12");
        Employee employee =new Employee();
        Mockito.when(sportReleaseservice.releaseSpot(employeeId, startDate, endDate)).thenReturn(response);
        ResponseEntity<RequestResponseDto> result=spotReleaseController.releaseSpot(employeeId, startDate, endDate);
        assertNotNull(result);
        
    }
    @Test
    public void TestForSportReleaseForTestNegtive() {
        
    	RequestResponseDto response=new RequestResponseDto();
    	response.setMessage("sucess");
        Employee employee =new Employee();
        Mockito.when(sportReleaseservice.releaseSpot(employeeId, startDate, endDate)).thenReturn(response);
        ResponseEntity<RequestResponseDto> result=spotReleaseController.releaseSpot(employeeId, startDate, endDate);
        assertNotNull(result);
        
    }
 

}
