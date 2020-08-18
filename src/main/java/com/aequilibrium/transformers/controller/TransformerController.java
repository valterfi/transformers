package com.aequilibrium.transformers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
			}
		} else {
			throw new TransformerException(HttpStatus.BAD_REQUEST, "It is not possible to add a transform with Id");
		}
	}

}
