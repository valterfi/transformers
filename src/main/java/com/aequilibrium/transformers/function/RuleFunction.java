package com.aequilibrium.transformers.function;

import com.aequilibrium.transformers.model.Transformer;
import com.aequilibrium.transformers.vo.BattleResult;

@FunctionalInterface
public interface RuleFunction {
	
	BattleResult check(Transformer transformer1, Transformer transformer2);

}
