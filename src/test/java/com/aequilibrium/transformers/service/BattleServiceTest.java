package com.aequilibrium.transformers.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.aequilibrium.transformers.enums.TransformerType;
import com.aequilibrium.transformers.model.Battle;
import com.aequilibrium.transformers.model.BattleResult;
import com.aequilibrium.transformers.model.Transformer;

public class BattleServiceTest {
	
	private static BattleService battleService;
	
	@BeforeClass
	public static void setUp() {
		battleService = new BattleService();
	}
	
	@Test
	public void shoudWinWithRule1() {
		Transformer autobot = Transformer.builder().withName("autobot")
				.withTransformerType(TransformerType.AUTOBOT)
				.withStrength(3)
				.withIntelligence(6)
				.withSpeed(8)
				.withEndurance(10)
				.withRank(7)
				.withCourage(3)
				.withFirepower(4)
				.withSkill(7);
		
		Transformer decepticon = Transformer.builder().withName("decepticon")
				.withTransformerType(TransformerType.DECEPTICON)
				.withStrength(10)
				.withIntelligence(6)
				.withSpeed(6)
				.withEndurance(5)
				.withRank(5)
				.withCourage(8)
				.withFirepower(2)
				.withSkill(4);
		
		Battle battle = new Battle();
		battle.setId(1L);
		BattleResult battleResult = battleService.run(1, battle, autobot, decepticon);
		
		assertEquals(1, battleResult.getBattleOrder());
		assertEquals(autobot, battleResult.getAutobot());
		assertEquals(decepticon, battleResult.getDecepticon());
		assertEquals(decepticon, battleResult.getWinner());
		assertEquals(autobot, battleResult.getLoser());
		assertEquals(true, battleResult.hasWinner());
		assertEquals(false, battleResult.isDestroyAll());
		assertEquals(1L, battleResult.getBattle().getId());
		assertEquals("Fighter is down 4 or more points of courage and 3 or more points of strength compared to their opponent", battleResult.getRuleApplied());
	}
	
	@Test
	public void shoudWinWithRule2() {
		Transformer autobot = Transformer.builder().withName("autobot")
				.withTransformerType(TransformerType.AUTOBOT)
				.withStrength(2)
				.withIntelligence(4)
				.withSpeed(4)
				.withEndurance(9)
				.withRank(9)
				.withCourage(10)
				.withFirepower(4)
				.withSkill(5);
		
		Transformer decepticon = Transformer.builder().withName("decepticon")
				.withTransformerType(TransformerType.DECEPTICON)
				.withStrength(7)
				.withIntelligence(2)
				.withSpeed(8)
				.withEndurance(7)
				.withRank(9)
				.withCourage(1)
				.withFirepower(10)
				.withSkill(1);
		
		Battle battle = new Battle();
		battle.setId(2L);
		BattleResult battleResult = battleService.run(2, battle, autobot, decepticon);
		
		assertEquals(2, battleResult.getBattleOrder());
		assertEquals(autobot, battleResult.getAutobot());
		assertEquals(decepticon, battleResult.getDecepticon());
		assertEquals(autobot, battleResult.getWinner());
		assertEquals(decepticon, battleResult.getLoser());
		assertEquals(true, battleResult.hasWinner());
		assertEquals(false, battleResult.isDestroyAll());
		assertEquals(2L, battleResult.getBattle().getId());
		assertEquals("Fighter is 3 or more points of skill above their opponent", battleResult.getRuleApplied());
	}
	
	@Test
	public void shoudWinWithSpecialRule1() {
		Transformer autobot = Transformer.builder().withName("Optimus Prime")
				.withTransformerType(TransformerType.AUTOBOT)
				.withStrength(2)
				.withIntelligence(4)
				.withSpeed(4)
				.withEndurance(9)
				.withRank(9)
				.withCourage(10)
				.withFirepower(4)
				.withSkill(5);
		
		Transformer decepticon = Transformer.builder().withName("Predaking")
				.withTransformerType(TransformerType.DECEPTICON)
				.withStrength(7)
				.withIntelligence(2)
				.withSpeed(8)
				.withEndurance(7)
				.withRank(9)
				.withCourage(1)
				.withFirepower(10)
				.withSkill(1);
		
		Battle battle = new Battle();
		battle.setId(3L);
		BattleResult battleResult = battleService.run(3, battle, autobot, decepticon);
		
		assertEquals(3, battleResult.getBattleOrder());
		assertEquals(autobot, battleResult.getAutobot());
		assertEquals(decepticon, battleResult.getDecepticon());
		assertNull(battleResult.getWinner());
		assertNull(battleResult.getLoser());
		assertEquals(false, battleResult.hasWinner());
		assertEquals(true, battleResult.isDestroyAll());
		assertEquals(3L, battleResult.getBattle().getId());
		assertEquals("Transformer named Optimus Prime or Predaking face each other (or a duplicate of each other), the game immediately ends with all competitors destroyed", battleResult.getRuleApplied());
	}
	
	@Test
	public void shoudWinWithSpecialRule2() {
		Transformer autobot = Transformer.builder().withName("autobot")
				.withTransformerType(TransformerType.AUTOBOT)
				.withStrength(2)
				.withIntelligence(4)
				.withSpeed(4)
				.withEndurance(9)
				.withRank(9)
				.withCourage(10)
				.withFirepower(4)
				.withSkill(5);
		
		Transformer decepticon = Transformer.builder().withName("Predaking")
				.withTransformerType(TransformerType.DECEPTICON)
				.withStrength(7)
				.withIntelligence(2)
				.withSpeed(8)
				.withEndurance(7)
				.withRank(9)
				.withCourage(1)
				.withFirepower(10)
				.withSkill(1);
		
		Battle battle = new Battle();
		battle.setId(4L);
		BattleResult battleResult = battleService.run(4, battle, autobot, decepticon);
		
		assertEquals(4, battleResult.getBattleOrder());
		assertEquals(autobot, battleResult.getAutobot());
		assertEquals(decepticon, battleResult.getDecepticon());
		assertEquals(decepticon, battleResult.getWinner());
		assertEquals(autobot, battleResult.getLoser());
		assertEquals(true, battleResult.hasWinner());
		assertEquals(false, battleResult.isDestroyAll());
		assertEquals(4L, battleResult.getBattle().getId());
		assertEquals("Transformer named Optimus Prime or Predaking wins his fight automatically", battleResult.getRuleApplied());
	}
	
	@Test
	public void shoudWinWithDefaultRule() {
		Transformer autobot = Transformer.builder().withName("autobot")
				.withTransformerType(TransformerType.AUTOBOT)
				.withStrength(5)
				.withIntelligence(2)
				.withSpeed(7)
				.withEndurance(5)
				.withRank(3)
				.withCourage(4)
				.withFirepower(9)
				.withSkill(9);
		
		Transformer decepticon = Transformer.builder().withName("decepticon")
				.withTransformerType(TransformerType.DECEPTICON)
				.withStrength(5)
				.withIntelligence(7)
				.withSpeed(7)
				.withEndurance(6)
				.withRank(1)
				.withCourage(6)
				.withFirepower(7)
				.withSkill(9);
		
		Battle battle = new Battle();
		battle.setId(5L);
		BattleResult battleResult = battleService.run(5, battle, autobot, decepticon);
		
		assertEquals(5, battleResult.getBattleOrder());
		assertEquals(autobot, battleResult.getAutobot());
		assertEquals(decepticon, battleResult.getDecepticon());
		assertEquals(decepticon, battleResult.getWinner());
		assertEquals(autobot, battleResult.getLoser());
		assertEquals(true, battleResult.hasWinner());
		assertEquals(false, battleResult.isDestroyAll());
		assertEquals(5L, battleResult.getBattle().getId());
		assertEquals("Default rule", battleResult.getRuleApplied());
	}
	
	@Test
	public void shoudTieWithDefaultRule() {
		Transformer autobot = Transformer.builder().withName("autobot")
				.withTransformerType(TransformerType.AUTOBOT)
				.withStrength(5)
				.withIntelligence(9)
				.withSpeed(8)
				.withEndurance(4)
				.withRank(3)
				.withCourage(3)
				.withFirepower(3)
				.withSkill(8);
		
		Transformer decepticon = Transformer.builder().withName("decepticon")
				.withTransformerType(TransformerType.DECEPTICON)
				.withStrength(5)
				.withIntelligence(7)
				.withSpeed(3)
				.withEndurance(4)
				.withRank(1)
				.withCourage(5)
				.withFirepower(10)
				.withSkill(9);
		
		Battle battle = new Battle();
		battle.setId(6L);
		BattleResult battleResult = battleService.run(6, battle, autobot, decepticon);
		
		assertEquals(autobot.overallRating(), decepticon.overallRating());
		assertEquals(6, battleResult.getBattleOrder());
		assertEquals(autobot, battleResult.getAutobot());
		assertEquals(decepticon, battleResult.getDecepticon());
		assertNull(battleResult.getWinner());
		assertNull(battleResult.getLoser());
		assertEquals(false, battleResult.hasWinner());
		assertEquals(false, battleResult.isDestroyAll());
		assertEquals(6L, battleResult.getBattle().getId());
		assertEquals("Default rule", battleResult.getRuleApplied());
	}

}
