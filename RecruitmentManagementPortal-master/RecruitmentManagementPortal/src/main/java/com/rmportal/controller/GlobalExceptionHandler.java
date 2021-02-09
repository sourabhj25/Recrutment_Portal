package com.rmportal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rmportal.responseModel.ExceptionResponse;
import com.rmportal.utility.CustomException;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointer(NullPointerException e) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode(500);
		response.setErrorMessage(e.getMessage());
		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleAnyException(Exception e) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode(204);
		response.setErrorMessage(e.getMessage());
		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException e) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode(e.getId());
		response.setErrorMessage(e.getMessage());
		return new ResponseEntity(response, HttpStatus.BAD_GATEWAY);
	}

}
