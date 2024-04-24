package com.kosa.tikitaka.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormLoginFilter extends UsernamePasswordAuthenticationFilter{
	private final ObjectMapper objectMapper;

	public FormLoginFilter(final AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
		objectMapper = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		log.info("FormloginFilter 호출 ==>");
		UsernamePasswordAuthenticationToken authRequest;
		try {
			JsonNode requestBody = objectMapper.readTree(request.getInputStream());
			String userId = requestBody.get("userId").asText();
			String password = requestBody.get("pwd").asText();
			log.info("userId : " + userId);
			log.info("password : " + password);
			authRequest = new UsernamePasswordAuthenticationToken(userId, password);
		} catch (Exception e) {
			throw new RuntimeException("userId, passWord 입력이 필요합니다.");
		}
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}
}
