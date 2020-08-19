package com.aequilibrium.transformers.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aequilibrium.transformers.enums.BattleStatus;
import com.aequilibrium.transformers.function.RuleFunction;
import com.aequilibrium.transformers.model.Battle;
import com.aequilibrium.transformers.model.BattleResult;
import com.aequilibrium.transformers.model.Transformer;
import com.aequilibrium.transformers.repository.BattleRepository;

@Service
public class BattleService {
	
	@Autowired
	private BattleRepository battleRepository;

	private List<RuleFunction> rules = new ArrayList<RuleFunction>();
	
	public BattleService() {
		basicRulesFactory();
	}
	
	public Battle save(Battle battle) {
		return battleRepository.save(battle);
	}
	
	public void archiveBattles() {
		List<Battle> battles = battleRepository.findByBattleStatusNot(BattleStatus.ARCHIVED);
		for (Battle battle : battles) {
			battle.setBattleStatus(BattleStatus.ARCHIVED);
			battleRepository.save(battle);
		}
	}
	
	public List<Battle> findByBattleStatus(BattleStatus battleStatus) {
		return battleRepository.findByBattleStatus(battleStatus);
	}
	
	private void basicRulesFactory() {
		rules.add(createSpecialRule1());
		rules.add(createSpecialRule2());
		rules.add(createRule1());
		rules.add(createRule2());
	}
	
	/**
	 * If any fighter is down 4 or more points of courage and 3 or more points of
	 * strength compared to their opponent, the opponent automatically wins the
	 * face-off regardless of overall rating (opponent has ran away)
	 */
	private RuleFunction createRule1() {
		return (transformer1, transformer2) -> {
			
			BattleResult battleResult = null;

			int courage1 = transformer1.getCourage();
			int courage2 = transformer2.getCourage();

			int strength1 = transformer1.getStrength();
			int strength2 = transformer2.getStrength();

			if ((courage1 - courage2) >= 4 && (strength1 - strength2) >= 3) {
				System.out.println("[RULE1] applied");
				battleResult = BattleResult.builder().withWinner(transformer1).withLoser(transformer2);
			}

			if ((courage2 - courage1) >= 4 && (strength2 - strength1) >= 3) {
				System.out.println("[RULE1] applied");
				battleResult = BattleResult.builder().withWinner(transformer2).withLoser(transformer1);
			}

			return battleResult;
		};

	}

	/**
	 * if one of the fighters is 3 or more points of skill above their opponent,
	 * they win the fight regardless of overall rating
	 */
	private RuleFunction createRule2() {
		return (transformer1, transformer2) -> {
			
			BattleResult battleResult = null;

			int skill1 = transformer1.getSkill();
			int skill2 = transformer2.getSkill();

			if (skill1 - skill2 >= 3) {
				System.out.println("[RULE2] applied");
				battleResult = BattleResult.builder().withWinner(transformer1).withLoser(transformer2);
			}

			if (skill2 - skill1 >= 3) {
				System.out.println("[RULE2] applied");
				battleResult = BattleResult.builder().withWinner(transformer2).withLoser(transformer1);
			}

			return battleResult;
		};
	}

	/**
	 * If Transformer named Optimus Prime or Predaking face each other (or a duplicate of each other), the game 
	 * immediately ends with all competitors destroyed
	 */
	private RuleFunction createSpecialRule1() {
		return (transformer1, transformer2) -> {
			
			BattleResult battleResult = null;

			String name1 = transformer1.getName().trim();
			String name2 = transformer2.getName().trim();

			if (name1.equalsIgnoreCase("Optimus Prime") && name2.equalsIgnoreCase("Optimus Prime")) {
				System.out.println("[SPECIAL RULE1] applied");
				battleResult = BattleResult.builder().shouldDestroyAll();
			}

			if (name1.equalsIgnoreCase("Predaking") && name2.equalsIgnoreCase("Predaking")) {
				System.out.println("[SPECIAL RULE1] applied");
				battleResult = BattleResult.builder().shouldDestroyAll();
			}
			
			if (name1.equalsIgnoreCase("Optimus Prime") && name2.equalsIgnoreCase("Predaking")) {
				System.out.println("[SPECIAL RULE1] applied");
				battleResult = BattleResult.builder().shouldDestroyAll();
			}
			
			if (name1.equalsIgnoreCase("Predaking") && name2.equalsIgnoreCase("Optimus Prime")) {
				System.out.println("[SPECIAL RULE1] applied");
				battleResult = BattleResult.builder().shouldDestroyAll();
			}

			return battleResult;
		};
	}

	/**
	 * If Transformer named Optimus Prime or Predaking wins his fight automatically 
	 * regardless of any other criteria
	 */
	private RuleFunction createSpecialRule2() {
		return (transformer1, transformer2) -> {
			
			BattleResult battleResult = null;

			String name1 = transformer1.getName().trim();
			String name2 = transformer2.getName().trim();

			if (name1.equalsIgnoreCase("Optimus Prime") || name1.equalsIgnoreCase("Predaking")) {
				System.out.println("[SPECIAL RULE2] applied");
				battleResult = BattleResult.builder().withWinner(transformer1).withLoser(transformer2);
			}

			if (name2.equalsIgnoreCase("Optimus Prime") || name2.equalsIgnoreCase("Predaking")) {
				System.out.println("[SPECIAL RULE2] applied");
				battleResult = BattleResult.builder().withWinner(transformer2).withLoser(transformer1);
			}

			return battleResult;
		};
	}

	/**
	 * Run the battle
	 * 
	 * @param transformer1
	 * @param transformer2
	 * @return The winner is the Transformer with the highest overall rating
	 */
	public BattleResult run(Integer battleOrder, Battle battle, Transformer transformer1, Transformer transformer2) {
		System.out.println(transformer1);
		System.out.println(transformer2);
		
		BattleResult battleResult = null;

		for (RuleFunction ruleFunction : this.rules) {
			battleResult = ruleFunction.check(transformer1, transformer2);
			if (battleResult != null) {
				return battleResult.withBattleOrder(battleOrder).withBattle(battle);
			}
		}

		int overallRating1 = transformer1.overallRating();
		int overallRating2 = transformer2.overallRating();
		
		battleResult = BattleResult.builder().withBattleOrder(battleOrder).withBattle(battle);

		if (overallRating1 > overallRating2) {
			battleResult = battleResult.withWinner(transformer1).withLoser(transformer2);
		} else if (overallRating1 < overallRating2) {
			battleResult = battleResult.withWinner(transformer2).withLoser(transformer1);
		}
		
		System.out.println("[DEFAULT RULE] applied");
		return battleResult;

	}
	
}