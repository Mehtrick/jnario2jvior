package de.mehtrick.jnario2jvior;

import java.util.Arrays;


enum BDDKeywords {
	Given, When, Then, And, But, Scenario, Feature, Background;

	public static String[] convertValuesToString() {
		return Arrays.asList(BDDKeywords.values()).stream().map(k -> k.toString()).toArray(String[]::new);
	}

}
