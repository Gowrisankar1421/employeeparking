package com.example.employeeparking.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SpotRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long spotRequesttId;
	
	@OneToOne
	private FreeSpot freeSpot;
	
	@JsonIgnore
	@OneToOne
	private Employee employee;
	
	private String status;
	
	private LocalDate date;

	public Long getSpotRequesttId() {
		return spotRequesttId;
	}

	public void setSpotRequesttId(Long spotRequesttId) {
		this.spotRequesttId = spotRequesttId;
	}

	public FreeSpot getFreeSpot() {
		return freeSpot;
	}

	public void setFreeSpot(FreeSpot freeSpot) {
		this.freeSpot = freeSpot;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
}
