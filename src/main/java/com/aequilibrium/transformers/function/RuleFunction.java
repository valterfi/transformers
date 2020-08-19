package com.aequilibrium.transformers.function;

import com.aequilibrium.transformers.model.BattleResult;
import com.aequilibrium.transformers.model.Transformer;

/**
 * @author valterfi
 *
 */
@FunctionalInterface
public interface RuleFunction {
	
	BattleResult check(Transformer transformer1, Transformer transformer2);

}
