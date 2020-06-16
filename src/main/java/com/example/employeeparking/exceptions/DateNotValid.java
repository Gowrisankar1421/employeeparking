package com.example.employeeparking.exceptions;

@SuppressWarnings("serial")
public class DateNotValid extends RuntimeException {
	public DateNotValid(String message) {
		super(message);
	}

}
