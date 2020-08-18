package com.aequilibrium.transformers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aequilibrium.transformers.model.Transformer;
import com.aequilibrium.transformers.repository.TransformerRepository;

@Service
public class TransformerService {
	
	@Autowired
	private TransformerRepository transformerRepository;
	
	public List<Transformer> findAll() {
		return (List<Transformer>) transformerRepository.findAll();
	}
	
	public void save(Transformer transformer) {
		transformerRepository.save(transformer);
	}
	
	public Transformer get(Integer id) {
		return transformerRepository.findById(id).orElse(null);
	}
	
	public void delete(Integer id) {
		transformerRepository.deleteById(id);
	}

}
