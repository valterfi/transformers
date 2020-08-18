package com.aequilibrium.transformers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
		return (List<Transformer>) transformerRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}
	
	public List<Transformer> findAll(Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
		Page<Transformer> pagedResult = transformerRepository.findAll(paging);
		
		if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Transformer>();
        }
	}
	
	public Transformer save(Transformer transformer) {
		return transformerRepository.save(transformer);
	}
	
	public Transformer get(Long id) {
		return transformerRepository.findById(id).orElse(null);
	}
	
	public void delete(Long id) {
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
			  transformer = transformerRepository.save(transformer);
			  System.out.println("Criando: " + transformer);
		  });
		return CompletableFuture.completedFuture(new ArrayList<Transformer>());
	}

}
