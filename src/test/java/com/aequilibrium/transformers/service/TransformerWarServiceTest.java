package com.aequilibrium.transformers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.aequilibrium.transformers.enums.BattleStatus;
import com.aequilibrium.transformers.enums.TransformerType;
import com.aequilibrium.transformers.model.Battle;
import com.aequilibrium.transformers.model.Transformer;

@RunWith(MockitoJUnitRunner.class)
public class TransformerWarServiceTest {
	
	@InjectMocks
	private TransformerWarService transformerWarService;
	
	@Mock
	private TransformerService transformerService;
	
	@Spy
	private BattleService battleService;
	
	@Mock
	private BattleResultService battleResultService;
	
	private List<Transformer> autobots = new ArrayList<Transformer>();
	
	private List<Transformer> decepticons = new ArrayList<Transformer>();
	
	@Before
	public void setUp() {
		Transformer blueStreak = Transformer.builder().withName("Bluestreak")
				.withTransformerType(TransformerType.AUTOBOT)
				.withStrength(6)
				.withIntelligence(6)
				.withSpeed(7)
				.withEndurance(9)
				.withRank(5)
				.withCourage(2)
				.withFirepower(9)
				.withSkill(7);
		blueStreak.setId(1L);
		autobots.add(blueStreak);
		
		Transformer hubcap = Transformer.builder().withName("Hubcap")
				.withTransformerType(TransformerType.AUTOBOT)
				.withStrength(4)
				.withIntelligence(4)
				.withSpeed(4)
				.withEndurance(4)
				.withRank(4)
				.withCourage(4)
				.withFirepower(4)
				.withSkill(4);
		hubcap.setId(2L);
		autobots.add(hubcap);
		
		Transformer soundwave = Transformer.builder().withName("Soundwave")
				.withTransformerType(TransformerType.DECEPTICON)
				.withStrength(8)
				.withIntelligence(9)
				.withSpeed(2)
				.withEndurance(6)
				.withRank(7)
				.withCourage(5)
				.withFirepower(6)
				.withSkill(10);
		soundwave.setId(2L);
		decepticons.add(soundwave);
	}
	
	@Test
	public void shouldRunTransformerWar() {
		List<Long> ids = new ArrayList<Long>(Arrays.asList(1L, 2L, 3L));
		
		doNothing().when(battleService).archiveBattles();
		
		when(transformerService.findByIds(TransformerType.AUTOBOT, ids)).thenReturn(autobots);
		when(transformerService.findByIds(TransformerType.DECEPTICON, ids)).thenReturn(decepticons);
		
		Battle battle = new Battle();
		battle.setId(1L);
		doReturn(battle).when(battleService).save(any());
		
		battle = transformerWarService.run(ids);
		
		assertEquals(1L, battle.getId());
		assertEquals(BattleStatus.FINISHED, battle.getBattleStatus());
		assertEquals(TransformerType.DECEPTICON, battle.getWinningTransformerType());
		assertEquals("", battle.getWinningAutobots());
		assertEquals(0, battle.getWinningAutobotsSize());
		assertEquals("Hubcap", battle.getSurvivorsAutobots());
		assertEquals("Soundwave", battle.getWinningDecepticons());
		assertEquals(1, battle.getWinningDecepticonsSize());
		assertEquals("Bluestreak, Hubcap", battle.getSurvivorsDecepticons());
		assertEquals(false, battle.getAllDestroyed());
		
	}

}
