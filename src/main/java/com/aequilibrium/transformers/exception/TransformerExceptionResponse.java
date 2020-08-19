package com.aequilibrium.transformers.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author valterfi
 *
 */
public class TransformerExceptionResponse {
	
	private int status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date timestamp;
	
	private String error;
	
	private String message;
	
	public TransformerExceptionResponse() {
		
	}

	public TransformerExceptionResponse(int status, Date timestamp, String error, String message) {
		this.status = status;
		this.timestamp = timestamp;
		this.error = error;
		this.message = message;
	}

	public TransformerExceptionResponse(HttpStatus httpStatus, String message) {
		this.status = httpStatus.value();
		this.error = httpStatus.name();
		timestamp = new Date();
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
