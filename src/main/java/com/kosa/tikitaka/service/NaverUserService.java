package com.kosa.tikitaka.service;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface NaverUserService {
	void naverLogin(String code, String state, HttpServletResponse response) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException;
}
