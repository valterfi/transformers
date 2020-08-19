package com.aequilibrium.transformers.util;

import com.aequilibrium.transformers.dto.BattleDetailDTO;
import com.aequilibrium.transformers.model.BattleResult;

public class BattleResultUtil {
	
	private BattleResultUtil() {
		
	}
	
	public static BattleDetailDTO mapTo(BattleResult battleResult) {
		return BattleDetailDTO.builder().withBattleOrder(battleResult.getBattleOrder())
				.withAutobotOverallRating(battleResult.getAutobot().overallRating())
				.withAutobot(battleResult.getAutobot())
				.withDecepticonOverallRating(battleResult.getDecepticon().overallRating())
				.withDecepticon(battleResult.getDecepticon())
				.withRule(battleResult.getRuleApplied())
				.withHasWinner(battleResult.hasWinner())
				.withWinner(battleResult.getWinner());
	}

}
