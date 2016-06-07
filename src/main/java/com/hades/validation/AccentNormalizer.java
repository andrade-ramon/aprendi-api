package com.hades.validation;

import java.text.Normalizer;

public class AccentNormalizer {

	public static String removeAccents(String text) {
		String normalized = Normalizer.normalize(text, Normalizer.Form.NFKD);
		normalized.replaceAll("[^\\p{ASCII}]", "");
		return normalized;
	}

}
