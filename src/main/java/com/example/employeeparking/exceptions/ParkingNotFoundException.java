package com.example.employeeparking.exceptions;

@SuppressWarnings("serial")
public class ParkingNotFoundException extends Exception {

	public ParkingNotFoundException(String string) {
		super(string);
	}

}
