package com.example.employeeparking.service;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.example.employeeparking.dto.RequestResponseDto;
import com.example.employeeparking.exceptions.EmployeeNotFoundException;
import com.example.employeeparking.model.Employee;
import com.example.employeeparking.model.EmployeeParkingLot;
import com.example.employeeparking.model.ParkingLot;
import com.example.employeeparking.repository.EmployeeParkingLotRepository;
import com.example.employeeparking.repository.EmployeeRepository;
import com.example.employeeparking.repository.FreeSpotRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SpotReleaseServiceTest {

	private static final LocalDate startDate = null;

	private static final LocalDate endDate = null;

	private static final Long employeeId = null;

	@InjectMocks
	SpotReleaseService spotReleaseService;

	@Mock
	EmployeeRepository employeeRepository;
	@Mock
	FreeSpotRepository freeSpotRepository;
	@Mock
	EmployeeParkingLotRepository employeeParkingLotRepository;

	@Test(expected = EmployeeNotFoundException.class)
	public void TestForSpotRelease() {

		EmployeeParkingLot employeparking = new EmployeeParkingLot();
		Employee employee = new Employee();
		employeparking.setId(123l);
		employeparking.setParkingLot(new ParkingLot());
		employeparking.setStatus("avialbel");
		RequestResponseDto requestResponseDto = new RequestResponseDto();
		requestResponseDto.setMessage("spot released successfully");
		Mockito.when(freeSpotRepository.save(Mockito.any())).thenReturn(employeparking);
		Mockito.when(employeeParkingLotRepository.findByEmployee(employee)).thenReturn(employeparking);

		RequestResponseDto result = spotReleaseService.releaseSpot(employeeId, startDate, endDate);
		assertNotNull(result);

	}
	@Test(expected = EmployeeNotFoundException.class)
	public void TestForSpotReleaseNegtive() {

		EmployeeParkingLot employeparking = new EmployeeParkingLot();
		Employee employee = new Employee();
		employee.setEmployeeId(-1L);
		employeparking.setId(123l);
		employeparking.setParkingLot(new ParkingLot());
		employeparking.setStatus("avialbel");
		RequestResponseDto requestResponseDto = new RequestResponseDto();
		requestResponseDto.setMessage("spot released successfully");
		Mockito.when(freeSpotRepository.save(Mockito.any())).thenReturn(employeparking);
		Mockito.when(employeeParkingLotRepository.findByEmployee(employee)).thenReturn(employeparking);

		RequestResponseDto result = spotReleaseService.releaseSpot(employeeId, startDate, endDate);
		assertNotNull(result);

	}

}
