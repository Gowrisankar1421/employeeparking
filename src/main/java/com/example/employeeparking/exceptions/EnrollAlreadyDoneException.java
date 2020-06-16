package com.example.employeeparking.exceptions;

@SuppressWarnings("serial")
public class EnrollAlreadyDoneException extends RuntimeException{
	public EnrollAlreadyDoneException(String message) {
		super(message);
	}

}
