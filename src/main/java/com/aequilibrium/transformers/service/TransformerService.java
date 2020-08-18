package com.aequilibrium.transformers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.aequilibrium.transformers.model.Transformer;
import com.aequilibrium.transformers.repository.TransformerRepository;
import com.aequilibrium.transformers.util.TransformerUtil;

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
	
	public void deleteAll() {
		transformerRepository.deleteAll();
	}
	
	@Async
	public CompletableFuture<List<Transformer>> generateRandom(Long fightersNumber) throws InterruptedException {
		LongStream.rangeClosed(1, fightersNumber)
		  .forEach(index -> {
			  Transformer transformer = TransformerUtil.random(index, fightersNumber);
			  System.out.println("Criando: " + transformer);
			  transformerRepository.save(transformer);
		  });
		return CompletableFuture.completedFuture(new ArrayList<Transformer>());
	}

}
