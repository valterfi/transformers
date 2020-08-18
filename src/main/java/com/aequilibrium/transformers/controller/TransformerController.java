package com.aequilibrium.transformers.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.aequilibrium.transformers.exception.ErrorResponse;
import com.aequilibrium.transformers.exception.TransformerException;
import com.aequilibrium.transformers.model.Transformer;
import com.aequilibrium.transformers.service.TransformerService;

@RestController
public class TransformerController {
	
	@Autowired
	private TransformerService transformerService;
	
	@GetMapping("/transformers")
	public List<Transformer> findAll() {
		return transformerService.findAll();
	}
	
	@PostMapping("/transformers")
	public ResponseEntity<Transformer> add(@RequestBody Transformer transformer) throws TransformerException {
		if (transformer.getId() == null) {
			try {
				transformerService.save(transformer);
				return new ResponseEntity<Transformer>(transformer, HttpStatus.CREATED);
			} catch (Exception e) {
				throw new TransformerException(HttpStatus.INTERNAL_SERVER_ERROR, e);
				
//				Throwable rootCause = ExceptionUtils.getRootCause(e);
//				if (rootCause instanceof ConstraintViolationException) {
//					ConstraintViolationException constraintViolationException = (ConstraintViolationException) rootCause;
//					Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
//					String message = violations.stream().map(v -> v.getMessage()).collect(Collectors.joining(","));
//					throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message);
//				}
//				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
				
			}
		} else {
			throw new TransformerException(HttpStatus.BAD_REQUEST, "It is not possible to add a transform with Id");
			//throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "It is not possible to add a transform with Id");
		}
	}

}
