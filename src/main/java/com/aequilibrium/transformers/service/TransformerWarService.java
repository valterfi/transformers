package com.aequilibrium.transformers.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.aequilibrium.transformers.dto.BattleSummaryDTO;
import com.aequilibrium.transformers.enums.BattleStatus;
import com.aequilibrium.transformers.enums.TransformerType;
import com.aequilibrium.transformers.model.Battle;
import com.aequilibrium.transformers.model.BattleResult;
import com.aequilibrium.transformers.model.Transformer;
import com.google.common.collect.Lists;

@Service
public class TransformerWarService {
	
	@Autowired
	private TransformerService transformerService;
	
	@Autowired
	private BattleService battleService;
	
	@Autowired 
	private BattleResultService battleResultService;
	
	public boolean existBattleRunning() {
		List<Battle> battles = battleService.findByBattleStatus(BattleStatus.RUNNING);
		return !battles.isEmpty();
	}
	
	@Async
	public CompletableFuture<List<Long>> run(List<Long> ids) {
		battleService.archiveBattles();
		
		List<Transformer> autobots = new ArrayList<Transformer>();
		List<Transformer> decepticons = new ArrayList<Transformer>();
		
		int size = 1;
		if (ids.size() > 1000) {
			size = ids.size() / 1000;
		}
		List<List<Long>> splitIds = Lists.partition(ids, size);
		
		for (List<Long> subIds: splitIds) {
	         autobots.addAll(transformerService.findByIds(TransformerType.AUTOBOT, subIds));
	         decepticons.addAll(transformerService.findByIds(TransformerType.DECEPTICON, subIds));
		}
		
		Collections.sort(autobots);
		Collections.sort(decepticons);
		
		processBattle(autobots, decepticons);
		
		return CompletableFuture.completedFuture(new ArrayList<Long>(ids));
	}
	
	public BattleSummaryDTO battleSummary() {
		return null;
	}

	private void processBattle(List<Transformer> autobots, List<Transformer> decepticons) {
		int battleCount = 0;
		
		Battle battle = battleService.save(new Battle());
		
		List<Transformer> autobotsLosers = new ArrayList<Transformer>();
		List<Transformer> deceptionsLosers = new ArrayList<Transformer>();
		List<BattleResult> battleResults = new ArrayList<BattleResult>();
		
		battle.setBattleStatus(BattleStatus.RUNNING);
		battle = battleService.save(battle);
		
		for (int i = 0; i < autobots.size() && i < decepticons.size(); i++) {
			System.out.println("Battle #" + (i+1));
			Integer order = (i+1);
			BattleResult battleResult = battleService.run(order, battle, autobots.get(i), decepticons.get(i));
			battleResults.add(battleResult);
			if (battleResult.hasWinner()) {
				if (battleResult.getWinner().getTransformerType().equals(TransformerType.AUTOBOT)) {
					deceptionsLosers.add(battleResult.getLoser());
				} else {
					autobotsLosers.add(battleResult.getLoser());
				}
				System.out.println("The winner is " + battleResult.getWinner());
			} else if (battleResult.isDestroyAll()) {
				autobotsLosers = new ArrayList<Transformer>(autobots);
				deceptionsLosers = new ArrayList<Transformer>(decepticons);
				break;
			} else {
				autobotsLosers.add(autobots.get(i));
				deceptionsLosers.add(decepticons.get(i));
				System.out.println("No winners");
			}
			if (battleCount % 10000 == 0) {
				battleResultService.saveAll(battleResults);
				battleResults = new ArrayList<BattleResult>();
			}
			battleCount++;
			System.out.println();
		}
		
		if (!battleResults.isEmpty()) {
			battleResultService.saveAll(battleResults);
			battleResults = new ArrayList<BattleResult>();
		}
		
		battle.setBattleStatus(BattleStatus.FINISHED);
		battle = battleService.save(battle);
		
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
