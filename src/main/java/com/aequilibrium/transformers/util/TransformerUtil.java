package com.aequilibrium.transformers.util;

import static com.aequilibrium.transformers.util.Constants.MAX_SPEC_VALUE;
import static com.aequilibrium.transformers.util.Constants.MIN_SPEC_VALUE;

import java.util.ArrayList;
import java.util.List;

import com.aequilibrium.transformers.enums.TransformerType;
import com.aequilibrium.transformers.model.Transformer;

public class TransformerUtil {
	
	private TransformerUtil() {
		
	}
	
	/**
	 * Generate random transformers fighters
	 */
	public static List<Transformer> random(int fightersNumber) {
		List<Transformer> transformers = new ArrayList<Transformer>();
		
		for (int i = 1; i <= fightersNumber; i++) {
			Transformer transformer = Transformer.builder().withName("Transformer-" + i)
					.withTransformerType(TransformerType.random())
					.withStrength(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withIntelligence(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withSpeed(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withEndurance(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withRank(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withCourage(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withFirepower(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1))
					.withSkill(getRandomInteger(MIN_SPEC_VALUE, MAX_SPEC_VALUE + 1));
			transformers.add(transformer);
		}
		
		return transformers;
	}
	
	/**
     * Returns random integer between minimum and maximum range
     */
    private static int getRandomInteger(int minimum, int maximum) {
        return ((int) (Math.random()*(maximum - minimum))) + minimum;
    }

}
