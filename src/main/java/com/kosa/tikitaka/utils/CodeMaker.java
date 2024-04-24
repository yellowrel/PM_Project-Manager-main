package com.kosa.tikitaka.utils;

import java.security.SecureRandom;

public class CodeMaker {

	private static SecureRandom random = new SecureRandom();

	private String ENGLISH_LOWER = "abcdefghijklmnopqrstuvwxyz";
	private String ENGLISH_UPPER = ENGLISH_LOWER.toUpperCase();
	private String NUMBER = "1234567890";

	private String DATA_RANDOM_STRING = ENGLISH_LOWER + ENGLISH_UPPER + NUMBER;

	private int length = 10;

	public String generate(String data, int length) {
		if(length < 1) throw new IllegalArgumentException("length must be a positive number.");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(data.charAt(random.nextInt(data.length())));
		}
		return sb.toString();
	}

	public String getCode() {
		return generate(DATA_RANDOM_STRING, length);
	}
}
