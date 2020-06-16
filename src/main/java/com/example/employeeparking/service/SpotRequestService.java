package com.example.employeeparking.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employeeparking.dto.RequestResponseDto;
import com.example.employeeparking.exceptions.DateNotValid;
import com.example.employeeparking.exceptions.EmployeeNotFoundException;
import com.example.employeeparking.exceptions.EnrollAlreadyDoneException;
import com.example.employeeparking.exceptions.NoFreeSpotsFoundException;
import com.example.employeeparking.exceptions.RequestNotProcessedException;
import com.example.employeeparking.exceptions.SpotRequestNotFoundException;
import com.example.employeeparking.model.Employee;
import com.example.employeeparking.model.FreeSpot;
import com.example.employeeparking.model.SpotRequest;
import com.example.employeeparking.repository.EmployeeRepository;
import com.example.employeeparking.repository.FreeSpotRepository;
import com.example.employeeparking.repository.SpotRequestRepository;


/**
 * 
 * @author Gowri sankar
 * @author Sai kumar
 * @author Sowjanya
 * this is class for spot request service
 *
 */
@Service
public class SpotRequestService {
	
	Logger logger = LoggerFactory.getLogger(SpotRequestService.class);
	
	@Autowired
	SpotRequestRepository spotRequestRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;	 
	
	@Autowired
	FreeSpotRepository freeSpotRepository;

	/**
	 * 
	 * @param employeeId
	 * @param startDate
	 * @param endDate
	 * @return RequestResponseDto
	 */
	public RequestResponseDto raiseRequest(Long employeeId,LocalDate startDate,LocalDate endDate) {
		logger.info("<---------------------<inside raise request service method>-------------------->");
		RequestResponseDto response = new RequestResponseDto();
		logger.warn("<---------------------------<checking for employee is found else return employee not found exception>------------------->");
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->new EmployeeNotFoundException("employee with given id not found"));
		LocalDate localDate = LocalDate.now();
		if(employee.getIsVip()==false) {
			if(startDate.compareTo(localDate)>0) {
				int startDate1 = startDate.getDayOfMonth();
				int endDate1 = endDate.getDayOfMonth();
				int difference = endDate1-startDate1;
				if(difference<2) {
					SpotRequest spotRequest1=spotRequestRepository.findSpotRequestByEmployeeAndDate(employee,startDate);
					SpotRequest spotRequest2=spotRequestRepository.findSpotRequestByEmployeeAndDate(employee,endDate);
					if((spotRequest1==null)&&(spotRequest2==null)) {
						for(LocalDate date = startDate;date.compareTo(endDate)<=0;date = date.plusDays(1)) {
							SpotRequest spotRequest = new SpotRequest();
							spotRequest.setEmployee(employee);
							spotRequest.setDate(date);
							spotRequest.setStatus("not assigned");
							spotRequestRepository.save(spotRequest);
						}
						logger.info("<-----------------<raising request response is successfull>---------------->");
						response.setMessage("successfully raised request");
					}
					else {
						logger.warn("<---------------<request already raised exception>--------------->");
						throw new EnrollAlreadyDoneException("already raised request");
					}
				}
				else {
					logger.warn("<-----------------<date cannot extend 2 days exception>---------------->");
					throw new DateNotValid("you can only request for 2 days");
				}
			}
			else {
				logger.warn("<-----------------<start date should be greater than todays date to raise request exception>---------------->");
				throw new DateNotValid("start date should be greater than todays date");
			}
		}
		else {
			logger.info("<-----------------<employee is vip so raise request is failed>---------------->");
			response.setMessage("Raising request is failed as you are not normal employee");
		}
		return response;
	}
	
	/**
	 * 
	 * @return ResponseDto as status msg
	 * @throws RequestNotProcessedException
	 * @throws SpotRequestNotFoundException
	 * @throws NoFreeSpotsFoundException
	 */
		public RequestResponseDto requestProcessing() throws RequestNotProcessedException, SpotRequestNotFoundException, NoFreeSpotsFoundException {
			logger.info("<----------<inside lottery request processing method>----------------->");
			LocalDate date = LocalDate.now();
			List<SpotRequest> employeeReq = spotRequestRepository.findByDateGreaterThanAndStatus(date, "not assigned");
			if (employeeReq.isEmpty()) {
				logger.warn("<--------------<spot request not found exception>--------------->");
				throw new SpotRequestNotFoundException("no requests found for spots");
			}
			List<Long> spotrequestIds = employeeReq.stream().map(m -> m.getSpotRequesttId()).collect(Collectors.toList());

			List<FreeSpot> freeSpots = freeSpotRepository.findByDateGreaterThanAndStatus(date, "not assigned");
			if (freeSpots.isEmpty()) {
				logger.warn("<--------------<no free spot found exception>--------------->");
				throw new NoFreeSpotsFoundException("free spots not available");
			}
			List<Long> freeSpotIds = freeSpots.stream().map(m -> m.getFreeSpotId()).collect(Collectors.toList());

			for (int i = 0; i < freeSpotIds.size(); i++) {
				FreeSpot freeSpot = freeSpotRepository.findById(freeSpotIds.get(i)).get();

				for (int j = 0; j < spotrequestIds.size(); j++) {
					SpotRequest spotRequest = spotRequestRepository.findById(getRandomElement(spotrequestIds)).get();

					logger.info("checking the dates");
					if (freeSpot.getDate().equals(spotRequest.getDate())&&spotRequest.getStatus().equalsIgnoreCase("not assigned")) {

						freeSpot.setStatus("assigned");
						spotRequest.setFreeSpot(freeSpot);
						spotRequest.setStatus("assigned");
						freeSpotRepository.save(freeSpot);

						spotRequestRepository.save(spotRequest);
						break;
					}

				}

			}
			RequestResponseDto responseDto = new RequestResponseDto();
			logger.info("<-----------------<lottery request raise is successfull>---------------->");
			responseDto.setMessage("requests for parking spots hass been done");
			return responseDto;

		}
		
		public Long getRandomElement(List<Long> list) 
	    { 
	        Random rand = new Random(); 
	        return list.get(rand.nextInt(list.size())); 
	    }

		public List<SpotRequest> getByEmployeeId(long employeeId) throws SpotRequestNotFoundException {
			logger.info("<------------<inside getEmployeeById service method>---------------->"); 
			logger.warn("<---------------------------<checking for employee is found else return employee not found exception>------------------->");
			Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->new EmployeeNotFoundException("Employee with give id not found"));
				List<SpotRequest> spotRequests = spotRequestRepository.findByEmployee(employee);
				if(spotRequests.isEmpty()) {
					logger.warn("<--------------<spot request not found exception>--------------->");
					throw new SpotRequestNotFoundException("request not found");
				}
				else {
					return spotRequests;
				}
				
				
		}
}
