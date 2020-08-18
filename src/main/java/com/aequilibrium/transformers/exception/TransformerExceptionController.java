package com.aequilibrium.transformers.exception;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransformerExceptionController {
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorResponse> exception(Exception exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
	    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> payloadException(Exception exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Incorrect request");
	    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = TransformerException.class)
	public ResponseEntity<ErrorResponse> transformerException(TransformerException transformerException) {
		Throwable rootCause = ExceptionUtils.getRootCause(transformerException);
		if (rootCause instanceof ConstraintViolationException) {
			ConstraintViolationException constraintViolationException = (ConstraintViolationException) rootCause;
			Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
			String message = violations.stream().map(v -> v.getMessage()).collect(Collectors.joining(", "));
			ErrorResponse errorResponse = new ErrorResponse(transformerException.getStatus(), message);
		    return new ResponseEntity<ErrorResponse>(errorResponse, transformerException.getStatus());
		}
		ErrorResponse errorResponse = new ErrorResponse(transformerException.getStatus(), transformerException.getMessage());
	    return new ResponseEntity<ErrorResponse>(errorResponse, transformerException.getStatus());
	}

}
