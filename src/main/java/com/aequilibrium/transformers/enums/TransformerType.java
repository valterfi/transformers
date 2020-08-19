package com.aequilibrium.transformers.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author valterfi
 *
 */
public enum TransformerType {

	AUTOBOT, DECEPTICON;

	private static final List<TransformerType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static TransformerType random() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

}
