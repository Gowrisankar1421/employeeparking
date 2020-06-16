package com.example.employeeparking.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class SpotRequestDto {
	
	@NotNull(message="employeeId is required")
	private Long employeeId;
	
	/* @NotEmpty(message="startDate is must") */
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private LocalDate startDate;
	
	/* @NotEmpty(message="endDate is must") */
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private LocalDate endDate;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	

}
