package com.aequilibrium.transformers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aequilibrium.transformers.model.BattleResult;
import com.aequilibrium.transformers.repository.BattleResultRepository;

@Service
public class BattleResultService {
	
	@Autowired
	private BattleResultRepository battleResultRepository;
	
	public void saveAll(List<BattleResult> battleResults) {
		battleResultRepository.saveAll(battleResults);
	}

}
