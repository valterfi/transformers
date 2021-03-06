package com.aequilibrium.transformers.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.aequilibrium.transformers.dto.BattleDetailDTO;
import com.aequilibrium.transformers.dto.BattleSummaryDTO;
import com.aequilibrium.transformers.enums.BattleStatus;
import com.aequilibrium.transformers.enums.TransformerType;
import com.aequilibrium.transformers.model.Battle;
import com.aequilibrium.transformers.model.BattleResult;
import com.aequilibrium.transformers.model.Transformer;
import com.aequilibrium.transformers.util.BattleResultUtil;
import com.google.common.collect.Lists;

/**
 * @author valterfi
 *
 * Service responsible for taking the result of all the battles of a war and processing to show what the result was.
 */
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
	
	/**
	 * Transform the list of ids into two lists of transformers and make them fight each other
	 */
	public Battle run(List<Long> ids) {
		battleService.archiveBattles();
		
		List<Transformer> autobots = new ArrayList<Transformer>();
		List<Transformer> decepticons = new ArrayList<Transformer>();
		
		int size = Integer.MAX_VALUE;
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
		
		return processBattle(autobots, decepticons);
	}
	
	@Async
	public CompletableFuture<List<Long>> runAsync(List<Long> ids) {
		run(ids);
		return CompletableFuture.completedFuture(new ArrayList<Long>(ids));
	}
	
	/**
	 * Generates the summary generates from the last battle or the battle that has not yet ended
	 */
	public BattleSummaryDTO battleSummary(boolean detailed) throws Exception {
		List<Battle> battles = battleService.findActiveBattles();
		if (battles.size() == 0) {
			throw new Exception("There is no battle running, finished or with error");
		} else if (battles.size() == 1) {
			return createBattleSummaryDTO(battles.get(0), detailed);
		} else {
			throw new Exception("There is more than one battle running or finished");
		}
	}
	
	/**
	 * Returns summary to the user for when there are too many fighters to fight
	 */
	public BattleSummaryDTO asyncBattleSummary() {
		return BattleSummaryDTO.builder().withMessage("Battles will start asynchronously, try to update in a few seconds");
	}
	
	/**
	 * Once the battle is over this method maps all the results for the DTO to show to the end user
	 */
	private BattleSummaryDTO createBattleSummaryDTO(Battle battle, boolean detailed) {
		Set<BattleResult> battleResults = battle.getBattleResults();
		Integer battlesNumber = battleResults.size();
		
		String message = "";
		String losingTeamSurvivors = "";
		String winningTeam = "";
		
		BattleStatus battleStatus = battle.getBattleStatus();
		
		if (battleStatus.equals(BattleStatus.FINISHED)) {
			winningTeam = battle.getWinningTransformerType().name();
			
			if (battle.getAllDestroyed()) {
				message = "All competitors were destroyed";
			} else if (battle.getWinningAutobotsSize() > battle.getWinningDecepticonsSize()) {
				message = "The winning team is autobots";
				losingTeamSurvivors = battle.getSurvivorsDecepticons();
			} else if (battle.getWinningAutobotsSize() < battle.getWinningDecepticonsSize()) {
				message = "The winning team is deceptions";
				losingTeamSurvivors = battle.getSurvivorsAutobots();
			} else {
				message = "Tie";
			}
		}
		
		List<BattleDetailDTO> battleDetailDTO = new ArrayList<BattleDetailDTO>();
		if (detailed) {
			battleDetailDTO = battleResults.stream()
					.map(battleResult -> BattleResultUtil.mapTo(battleResult))
					.collect(Collectors.toList());
		}
		
		return BattleSummaryDTO.builder().withBattlesNumber(battlesNumber)
				.withBattleStatus(battle.getBattleStatus().name())
				.withWinningTeam(winningTeam)
				.withMessage(message)
				.withLosingTeamSurvivors(losingTeamSurvivors)
				.withDetails(battleDetailDTO);
	}

	/**
	 * Processes who are the winners and survivors I record everything in the battle record
	 */
	private Battle processBattle(List<Transformer> autobots, List<Transformer> decepticons) {
		Long battleId = null;
		try {
			int battleCount = 0;
			
			Battle battle = new Battle();
			battle = battleService.save(battle);
			battleId = battle.getId();
			
			List<String> winningAutobots = new ArrayList<String>();
			List<String> winningDecepticons = new ArrayList<String>();
			
			List<Transformer> survivorsAutobots = new ArrayList<Transformer>(autobots);
			List<Transformer> survivorsDecepticons = new ArrayList<Transformer>(autobots);
			
			List<BattleResult> battleResults = new ArrayList<BattleResult>();
			
			battle.setBattleStatus(BattleStatus.RUNNING);
			battle = battleService.save(battle);
			
			for (int i = 0; i < autobots.size() && i < decepticons.size(); i++) {
				Integer order = (i+1);
				BattleResult battleResult = battleService.run(order, battle, autobots.get(i), decepticons.get(i));
				battleResults.add(battleResult);
				if (battleResult.hasWinner()) {
					if (battleResult.getWinner().getTransformerType().equals(TransformerType.AUTOBOT)) {
						winningAutobots.add(battleResult.getWinner().getName());
						survivorsDecepticons.remove(battleResult.getLoser());
					} else {
						winningDecepticons.add(battleResult.getWinner().getName());
						survivorsAutobots.remove(battleResult.getLoser());
					}
				} else if (battleResult.isDestroyAll()) {
					battle.setAllDestroyed(true);
					survivorsAutobots.remove(battleResult.getLoser());
					survivorsDecepticons.remove(battleResult.getLoser());
					break;
				} else {
					survivorsAutobots.remove(battleResult.getLoser());
					survivorsDecepticons.remove(battleResult.getLoser());
				}
				
				battleCount++;
				if (battleCount % 10000 == 0) {
					battleResultService.saveAll(battleResults);
					battleResults = new ArrayList<BattleResult>();
				}
			}
			
			if (!battleResults.isEmpty()) {
				battleResultService.saveAll(battleResults);
				battleResults = new ArrayList<BattleResult>();
			}
			
			battle.setBattleStatus(BattleStatus.FINISHED);
			
			battle.setWinningAutobots(winningAutobots.stream().collect(Collectors.joining(", ")));
			battle.setWinningAutobotsSize(winningAutobots.size());
			battle.setSurvivorsAutobots(survivorsAutobots.stream().map(autobot -> autobot.getName()).collect(Collectors.joining(", ")));
			
			battle.setWinningDecepticons(winningDecepticons.stream().collect(Collectors.joining(", ")));
			battle.setWinningDecepticonsSize(winningDecepticons.size());
			battle.setSurvivorsDecepticons(survivorsDecepticons.stream().map(decepticon -> decepticon.getName()).collect(Collectors.joining(", ")));
			
			if (winningAutobots.size() > winningDecepticons.size()) {
				battle.setWinningTransformerType(TransformerType.AUTOBOT);
			} else {
				battle.setWinningTransformerType(TransformerType.DECEPTICON);
			}
			
			return battleService.save(battle);
		
		} catch (Exception e) {
			if (battleId != null) {
				Battle battle = battleService.findById(battleId);
				battle.setBattleStatus(BattleStatus.ERROR);
				battleService.save(battle);
			}
			throw e;
		}
	}

}
