package com.example.employeeparking.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.Assert;

import com.example.employeeparking.dto.RequestResponseDto;
import com.example.employeeparking.exceptions.DateNotValid;
import com.example.employeeparking.exceptions.EmployeeNotFoundException;
import com.example.employeeparking.exceptions.EnrollAlreadyDoneException;
import com.example.employeeparking.exceptions.NoFreeSpotsFoundException;
import com.example.employeeparking.exceptions.RequestNotProcessedException;
import com.example.employeeparking.exceptions.SpotRequestNotFoundException;
import com.example.employeeparking.model.Employee;
import com.example.employeeparking.model.FreeSpot;
import com.example.employeeparking.model.ParkingLot;
import com.example.employeeparking.model.SpotRequest;
import com.example.employeeparking.repository.EmployeeRepository;
import com.example.employeeparking.repository.FreeSpotRepository;
import com.example.employeeparking.repository.SpotRequestRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SpotRequestServiceTest {
	@Mock
	SpotRequestRepository spotRequestRepository;

	@Mock
	FreeSpotRepository freeSpotRepository;

	@InjectMocks
	SpotRequestService spotRequestService;
	
	@Mock
	EmployeeRepository employeeRepository;	 

	RequestResponseDto responseDto = new RequestResponseDto();
	SpotRequest spotRequest = new SpotRequest();
	List<SpotRequest> spotrequests = new ArrayList<SpotRequest>();
	List<FreeSpot> freeSpots = new ArrayList<FreeSpot>();
	LocalDate date = LocalDate.now();
	FreeSpot freeSpot = new FreeSpot();
	ParkingLot parkingLot = new ParkingLot();
	Employee employee = new Employee();

	@SuppressWarnings("unused")
	@Test(expected = NoSuchElementException.class)
	public void testCreateUserForPositive() throws Exception {
		employee.setIsVip(true);
		employee.setEmail("@gmail.com");
		employee.setEmployeeId(1l);
		employee.setEmployeeName("kumar");
		employee.setPassword("yerg");
		employee.setPhone("123456789");
		parkingLot.setParkingLotId(1l);
		parkingLot.setDescription("well and good");
		parkingLot.setSpot("11-lk-20");
		freeSpot.setDate(date);
		freeSpot.setFreeSpotId(1l);
		freeSpot.setStatus("not assigned");
		freeSpot.setParkingLot(parkingLot);
		freeSpot.setEmployee(employee);
		freeSpots.add(freeSpot);

		spotRequest.setDate(date);
		spotRequest.setSpotRequesttId(1l);
		spotRequest.setStatus("not assigned");
		spotRequest.setFreeSpot(freeSpot);
		spotRequest.setEmployee(employee);
		spotrequests.add(spotRequest);
		responseDto.setMessage("sucessful");
		responseDto.getMessage();

		Mockito.when(spotRequestRepository.findByDateGreaterThanAndStatus(date, "not assigned"))
				.thenReturn(spotrequests);
		if (spotrequests.isEmpty()) {
			throw new SpotRequestNotFoundException("no requests found for spots");
		}
		List<Long> spotrequestIds = spotrequests.stream().map(m -> m.getSpotRequesttId()).collect(Collectors.toList());
		Long spotRequestId = getRandomElement(spotrequestIds);
		Mockito.when(spotRequestRepository.findById(spotRequestId).get()).thenReturn(spotRequest);

		Mockito.when(freeSpotRepository.findByDateGreaterThanAndStatus(date, "not assigned")).thenReturn(freeSpots);
		if (freeSpots.isEmpty()) {
			throw new NoFreeSpotsFoundException("free spots not available");
		}
		List<Long> freeSpotIds = freeSpots.stream().map(m -> m.getFreeSpotId()).collect(Collectors.toList());
		Long freeSpotId = getRandomElement(freeSpotIds);
		Mockito.when(freeSpotRepository.findById(freeSpotId).get()).thenReturn(freeSpot);
		if (freeSpot.getDate().equals(spotRequest.getDate())) {

			freeSpot.setStatus("assigned");
			spotRequest.setFreeSpot(freeSpot);
			spotRequest.setStatus("assigned");
			freeSpotRepository.save(freeSpot);
			spotRequestRepository.save(spotRequest);

		} else {
			throw new RequestNotProcessedException("request not processed");
		}
		RequestResponseDto responseDto = new RequestResponseDto();
		responseDto.setMessage("requests for parking spots hass been done");
		RequestResponseDto responseDto1 = spotRequestService.requestProcessing();
		Assert.notNull(responseDto1);
	}

	public Long getRandomElement(List<Long> list) {
		Random rand = new Random();
		return list.get(rand.nextInt(list.size()));
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testRaiseRequest() {
		Long employeeId = 1L;
		LocalDate startDate = LocalDate.of(2020, 06, 28);
		LocalDate endDate = LocalDate.of(2020, 06, 29);
		RequestResponseDto response = new RequestResponseDto();
		Optional<Employee> employee =Optional.of(new Employee());
		employee.get().setEmail("gs@gmail.com");
		employee.get().setEmployeeId(1L);
		employee.get().setEmployeeName("gs");
		employee.get().setIsVip(true);
		employee.get().setPassword("hai");
		employee.get().setPhone("9876543210");
		employeeRepository.save(employee.get());
		Mockito.when(employeeRepository.findById(employeeId)).thenReturn(employee);
		LocalDate localDate = LocalDate.now();
		if(employee.get().getIsVip()==false) {
			if(startDate.compareTo(localDate)>0) {
				int startDate1 = startDate.getDayOfMonth();
				int endDate1 = endDate.getDayOfMonth();
				int difference = endDate1-startDate1;
				if(difference<2) {
					SpotRequest spotRequest1=spotRequestRepository.findSpotRequestByEmployeeAndDate(employee.get(),startDate);
					SpotRequest spotRequest2=spotRequestRepository.findSpotRequestByEmployeeAndDate(employee.get(),endDate);
					if((spotRequest1==null)&&(spotRequest2==null)) {
						for(LocalDate date = startDate;date.compareTo(endDate)<=0;date = date.plusDays(1)) {
							SpotRequest spotRequest = new SpotRequest();
							spotRequest.setEmployee(employee.get());
							spotRequest.setDate(date);
							spotRequest.setStatus("not assigned");
							spotRequestRepository.save(spotRequest);
						}
						response.setMessage("successfully raised request");
						Assert.notNull(response.toString());
					}
					else {
						throw new EnrollAlreadyDoneException("already raised request");
					}
				}
				else {
					throw new DateNotValid("you can only request for 2 days");
				}
			}
			else {
				throw new DateNotValid("start date should be greater than todays date");
			}
		}
		else {
			response.setMessage("Raising request is failed as you are not normal employee");
			Assert.notNull(response.toString());
		}
		response = spotRequestService.raiseRequest(employeeId, startDate, endDate);
		Assert.notNull(response);
	}

}
