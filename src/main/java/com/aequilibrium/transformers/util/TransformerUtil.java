package com.aequilibrium.transformers.util;

import static com.aequilibrium.transformers.util.Constants.MAX_SPEC_VALUE;
import static com.aequilibrium.transformers.util.Constants.MIN_SPEC_VALUE;

import com.aequilibrium.transformers.enums.TransformerType;
import com.aequilibrium.transformers.model.Transformer;

/**
 * @author valterfi
 *
 */
public class TransformerUtil {
	
	private TransformerUtil() {
		
	}
	
	/**
	 * Generate random transformer fighter
	 */
	public static Transformer random(Integer index, Integer fightersNumber) {
			return Transformer.builder().withName("Transformer" + index)
					.withTransformerType(TransformerType.random())
					.withStrength(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withIntelligence(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withSpeed(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withEndurance(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withRank(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withCourage(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withFirepower(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withSkill(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1));
	}
	
	/**
     * Returns random integer between minimum and maximum range
     */
    private static int getRandomInteger(int minimum, int maximum) {
        return ((int) (Math.random()*(maximum - minimum))) + minimum;
    }

}
