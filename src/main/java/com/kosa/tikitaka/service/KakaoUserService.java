package com.kosa.tikitaka.service;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kosa.tikitaka.model.SocialLoginDTO;

public interface KakaoUserService {
	SocialLoginDTO kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException, NoSuchAlgorithmException;
}
