package com.kosa.tikitaka.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosa.tikitaka.model.SocialLoginDTO;
import com.kosa.tikitaka.model.UserDTO;
import com.kosa.tikitaka.security.UserDetailsImpl;
import com.kosa.tikitaka.security.jwt.JwtTokenUtils;

@Service
public class NaverUserServiceImpl implements NaverUserService {

	private BCryptPasswordEncoder passwordEncoder;
	private UserService userService;

	@Override
	public void naverLogin(String code, String state, HttpServletResponse response) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException {
		// 1. 인가코드로 엑세스토큰 가져오기
		String accessToken = getAccessToken(code, state);

		// 2. 엑세스토큰으로 유저정보 가져오기
		SocialLoginDTO naverUserInfo = getNaverUserInfo(accessToken);

		// 3. 유저확인 & 회원가입
		UserDTO naverUser = getUser(naverUserInfo);

		// 4. 시큐리티 강제 로그인
		Authentication authentication = securityLogin(naverUser);

		// 5. jwt 토큰 발급
		jwtToken(authentication, response);
	}

	private void jwtToken(Authentication authentication, HttpServletResponse response) {
		UserDetailsImpl userDetailsImpl = ((UserDetailsImpl) authentication.getPrincipal());
        String token = JwtTokenUtils.generateJwtToken(userDetailsImpl);
        response.addHeader("Authorization", "BEARER" + " " + token);
	}

	private Authentication securityLogin(UserDTO naverUser) {
		UserDetails userDetails = new UserDetailsImpl(naverUser, null);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return authentication;
	}

	private UserDTO getUser(SocialLoginDTO naverUserInfo) throws NoSuchAlgorithmException {
		String socialId = naverUserInfo.getSocialId();
		UserDTO naverUser = null;
		try {
			naverUser = userService.findByUserId(socialId);
		} catch (Exception e) {
			String kakaoUserName = naverUserInfo.getUsername();

			String password = UUID.randomUUID().toString();
			MessageDigest md = MessageDigest.getInstance("SHA-512/256");
			md.update(password.getBytes());
			md.update(kakaoUserName.getBytes());
			String encodedPwd = String.format("%64x", new BigInteger(1, md.digest()));

			naverUser = new UserDTO(socialId, encodedPwd, kakaoUserName, false);
		}

		return naverUser;
	}

	private SocialLoginDTO getNaverUserInfo(String accessToken) throws JsonMappingException, JsonProcessingException {
		// 헤더에 엑세스토큰 담기, Content-type 지정
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// POST 요청 보내기
		HttpEntity<MultiValueMap<String, String>> naverUser = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.POST,
				naverUser, String.class);

		// response에서 유저정보 가져오기
		String responseBody = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);

		String socialId = String.valueOf(jsonNode.get("response").get("id").asText());
		String username = jsonNode.get("response").get("nickname").asText() + "_" + socialId;
		Random rnd = new Random();
		String s = "";
		for (int i = 0; i < 8; i++) {
			s += String.valueOf(rnd.nextInt(10));
		}
		String nickname = "N" + "_" + s;

		return new SocialLoginDTO(username, nickname, socialId);
	}

	private String getAccessToken(String code, String state) throws JsonMappingException, JsonProcessingException {
		// 헤더에 Content-type 지정
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// 바디에 필요한 정보 담기
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", "t4OWgJ98r5KZ2jo9p0sO");
		body.add("client_secret", "8h45LLW9XA");
		body.add("code", code);
		body.add("state", state);

		// POST 요청 보내기
		HttpEntity<MultiValueMap<String, String>> naverToken = new HttpEntity<>(body, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange("https://nid.naver.com/oauth2.0/token", HttpMethod.POST,
				naverToken, String.class);

		// response에서 엑세스토큰 가져오기
		String responseBody = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode responseToken = objectMapper.readTree(responseBody);
		String accessToken = responseToken.get("access_token").asText();
		return accessToken;
	}
}
