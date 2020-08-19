package com.aequilibrium.transformers.exception;

import org.springframework.http.HttpStatus;

public class TransformerException extends Exception {
	
	private HttpStatus status;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TransformerException(String message) {
		super(message);
	}
	
	public TransformerException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
	
	public TransformerException(HttpStatus status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}
	
	public TransformerException(HttpStatus status, Throwable cause) {
		super(cause);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
