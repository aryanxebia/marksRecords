package com.student.marks.exception;

import org.springframework.http.HttpStatus;

public class MarkServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4668262658884490566L;
	private HttpStatus httpStatus;
	private String message;

	public MarkServiceException(HttpStatus httpStatus, String message) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
