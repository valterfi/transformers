package com.aequilibrium.transformers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aequilibrium.transformers.exception.TransformerException;
import com.aequilibrium.transformers.model.Transformer;
import com.aequilibrium.transformers.service.TransformerService;

@RestController
@RequestMapping("/api/transformers")
public class TransformerController {
	
	@Autowired
	private TransformerService transformerService;
	
	@GetMapping
	public List<Transformer> findAll() {
		return transformerService.findAll();
	}
	
	@PostMapping
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
	
	@PutMapping("/{id}")
	public ResponseEntity<Transformer> update(@RequestBody Transformer transformer, @PathVariable Integer id) throws TransformerException {
		Transformer existTransformer = transformerService.get(id);
		if (existTransformer != null) {
			existTransformer.setName(transformer.getName());
			existTransformer.setStrength(transformer.getStrength());
			existTransformer.setIntelligence(transformer.getIntelligence());
			existTransformer.setSpeed(transformer.getSpeed());
			existTransformer.setEndurance(transformer.getEndurance());
			existTransformer.setRank(transformer.getRank());
			existTransformer.setCourage(transformer.getCourage());
			existTransformer.setFirepower(transformer.getFirepower());
			existTransformer.setSkill(transformer.getSkill());
			try {
				transformerService.save(existTransformer);
				return new ResponseEntity<Transformer>(HttpStatus.OK);
			} catch (Exception e) {
				throw new TransformerException(HttpStatus.INTERNAL_SERVER_ERROR, e);
			}
		} else { 
			throw new TransformerException(HttpStatus.NOT_FOUND, "Transformer not found");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Transformer> delete(@PathVariable Integer id) throws TransformerException {
		Transformer existTransformer = transformerService.get(id);
		if (existTransformer != null) {
			transformerService.delete(id);
			return new ResponseEntity<Transformer>(HttpStatus.OK);
		} else { 
			throw new TransformerException(HttpStatus.NOT_FOUND, "Transformer not found");
		}
	}

}