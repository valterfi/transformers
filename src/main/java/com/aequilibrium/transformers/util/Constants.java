package com.aequilibrium.transformers.util;

public class Constants {
	
	public static final String TRANSFORMER_TYPE_COLUMN = "VARCHAR(60) CHECK (TRANSFORMER_TYPE IN (" + TransformerTypeUtil.listAll() + "))";

}
