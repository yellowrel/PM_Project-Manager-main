package com.kosa.tikitaka.security.jwt;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HeaderTokenExtractor {
	/*
	 HEADER_PREFIX
	 header Authoriztion token 값의 표준이 되는 변수
	 */

	public final String HEADER_PREFIX = "Bearer ";

	public String extract(String header, HttpServletRequest req) {
		/*
		 Token 값이 올바르지 않는 경우
		 header 값이 비어있거나 또는 HEADER_PREFIX 값보다 짧은 경우
		 exception을 던저줌
		 */
		if(header == null || header.equals("") || header.length() <HEADER_PREFIX.length()) {
			log.error("error request : " + req.getRequestURI());
			throw new NoSuchElementException("올바른 JWT 정보가 아닙니다.");
		}
		/*
		 Token 값이 존재하는 경우
		 (bearer ) 부분만 제거 후 token 값 반환
		 */
		return header.substring(HEADER_PREFIX.length(), header.length());
	}
}
