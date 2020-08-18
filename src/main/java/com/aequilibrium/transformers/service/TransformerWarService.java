package com.aequilibrium.transformers.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aequilibrium.transformers.enums.TransformerType;
import com.aequilibrium.transformers.model.Transformer;
import com.aequilibrium.transformers.vo.BattleResult;

@Service
public class TransformerWarService {
	
	@Autowired
	private TransformerService transformerService;
	
	@Autowired
	private BattleService battleService;
	
	public void run(List<Long> ids) {
		List<Transformer> autobots = new ArrayList<Transformer>();
		List<Transformer> decepticons = new ArrayList<Transformer>();
		
		for (Long id : ids) {
			Transformer transformer = transformerService.get(id);
			
			if (transformer != null) {
				if (transformer.getTransformerType().equals(TransformerType.AUTOBOT)) {
					autobots.add(transformer);
				} else {
					decepticons.add(transformer);
				}
			}
		}
		
		Collections.sort(autobots);
		Collections.sort(decepticons);
		
		processBattle(autobots, decepticons);
		
	}

	private void processBattle(List<Transformer> autobots, List<Transformer> decepticons) {
		int battleCount = 0;
		
		List<Transformer> autobotsLosers = new ArrayList<Transformer>();
		List<Transformer> deceptionsLosers = new ArrayList<Transformer>();
		
		for (int i = 0; i < autobots.size() && i < decepticons.size(); i++) {
			System.out.println("Battle #" + (i+1));
			BattleResult result = battleService.run(autobots.get(i), decepticons.get(i));
			if (result.hasWinner()) {
				if (result.getWinner().getTransformerType().equals(TransformerType.AUTOBOT)) {
					deceptionsLosers.add(result.getLoser());
				} else {
					autobotsLosers.add(result.getLoser());
				}
				System.out.println("The winner is " + result.getWinner());
			} else if (result.isDestroyAll()) {
				autobotsLosers = new ArrayList<Transformer>(autobots);
				deceptionsLosers = new ArrayList<Transformer>(decepticons);
				break;
			} else {
				autobotsLosers.add(autobots.get(i));
				deceptionsLosers.add(decepticons.get(i));
				System.out.println("No winners");
			}
			battleCount++;
			System.out.println();
		}
		
		System.out.println("------------------------");
		System.out.println("The number of battles: " + battleCount);
		
		if (autobotsLosers.size() == autobots.size() && deceptionsLosers.size() == decepticons.size()) {
			System.out.println("All competitors were destroyed");
		} else if (deceptionsLosers.size() > autobotsLosers.size()) {
			System.out.println("The winning team is autobots");
			decepticons.removeAll(deceptionsLosers);
			System.out.println("The surviving members of the losing team:");
			printTransformers(decepticons);
		} else if (deceptionsLosers.size() < autobotsLosers.size()) {
			System.out.println("The winning team is deceptions");
			autobots.removeAll(autobotsLosers);
			System.out.println("The surviving members of the losing team:");
			printTransformers(autobots);
		} else {
			System.out.println("Tie");
		}
	}
	
	public void printTransformers(List<Transformer> transformers) {
		for (Transformer transformer : transformers) {
			System.out.println(transformer);
		}
	}

}
