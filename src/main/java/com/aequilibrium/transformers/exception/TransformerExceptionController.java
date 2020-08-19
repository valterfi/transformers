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

/**
 * @author valterfi
 *
 */
@ControllerAdvice
public class TransformerExceptionController {
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<TransformerExceptionResponse> exception(Exception exception) {
		TransformerExceptionResponse errorResponse = new TransformerExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
	    return new ResponseEntity<TransformerExceptionResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public ResponseEntity<TransformerExceptionResponse> payloadException(Exception exception) {
		TransformerExceptionResponse errorResponse = new TransformerExceptionResponse(HttpStatus.BAD_REQUEST, "Incorrect request");
	    return new ResponseEntity<TransformerExceptionResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = TransformerException.class)
	public ResponseEntity<TransformerExceptionResponse> transformerException(TransformerException transformerException) {
		Throwable rootCause = ExceptionUtils.getRootCause(transformerException);
		if (rootCause instanceof ConstraintViolationException) {
			ConstraintViolationException constraintViolationException = (ConstraintViolationException) rootCause;
			Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
			String message = violations.stream().map(v -> v.getMessage()).collect(Collectors.joining(", "));
			TransformerExceptionResponse errorResponse = new TransformerExceptionResponse(transformerException.getStatus(), message);
		    return new ResponseEntity<TransformerExceptionResponse>(errorResponse, transformerException.getStatus());
		}
		TransformerExceptionResponse errorResponse = new TransformerExceptionResponse(transformerException.getStatus(), transformerException.getMessage());
	    return new ResponseEntity<TransformerExceptionResponse>(errorResponse, transformerException.getStatus());
	}

}
