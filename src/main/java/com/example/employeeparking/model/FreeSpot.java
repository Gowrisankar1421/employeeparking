package com.example.employeeparking.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FreeSpot {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long freeSpotId;
	
	@OneToOne
	private ParkingLot parkingLot;
	
	
	@JsonIgnore
	@OneToOne
	private Employee employee;
	
	private String status;
	
	private LocalDate date;

	public Long getFreeSpotId() {
		return freeSpotId;
	}

	public void setFreeSpotId(Long freeSpotId) {
		this.freeSpotId = freeSpotId;
	}

	public ParkingLot getParkingLot() {
		return parkingLot;
	}

	public void setParkingLot(ParkingLot parkingLot) {
		this.parkingLot = parkingLot;
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
