package com.aequilibrium.transformers.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aequilibrium.transformers.enums.TransformerType;
import com.aequilibrium.transformers.model.Transformer;

@Service
public class TransformerWarService {
	
	@Autowired
	private TransformerService transformerService;
	
	private final Comparator<Transformer> RANK_COMPARATOR = (tranformer1, transformer2) -> {
		if (tranformer1.getRank() < transformer2.getRank()) {
			return 1;
		} else if (tranformer1.getRank() > transformer2.getRank()) {
			return -1;
		}
		return 0;
	};
	
	public void execute(List<Integer> ids) {
		List<Transformer> autobots = new ArrayList<Transformer>();
		List<Transformer> decepticons = new ArrayList<Transformer>();
		
		for (Integer id : ids) {
			Transformer transformer = transformerService.get(id);
			
			if (transformer.getTransformerType().equals(TransformerType.AUTOBOT)) {
				autobots.add(transformer);
			} else {
				decepticons.add(transformer);
			}
		}
		
		Collections.sort(autobots, RANK_COMPARATOR);
		Collections.sort(decepticons, RANK_COMPARATOR);
		
		System.out.println(autobots);
		System.out.println(decepticons);
		
	}

}