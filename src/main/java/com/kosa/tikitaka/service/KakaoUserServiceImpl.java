package com.kosa.tikitaka.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoUserServiceImpl implements KakaoUserService {

	private AbstractPasswordEncoder passwordEncoder;
	private UserService userService;

	@Override
	public SocialLoginDTO kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException, NoSuchAlgorithmException {

		// 1. "인가 코드"로 "액세스 토큰" 요청
		String accessToken = getAccessToken(code);

		// 2. 토큰으로 카카오 API 호출
		SocialLoginDTO kakaoUserInfo = getKakaoUserInfo(accessToken);

		// 3. 카카오ID로 회원가입 처리
		UserDTO kakaoUser = registerKakaoUserIfNeed(kakaoUserInfo);

		// 4. 강제 로그인 처리
		Authentication authentication = forceLogin(kakaoUser);

		// 5. response Header에 JWT 토큰 추가
		kakaoUsersAuthorizationInput(authentication, response);
		return kakaoUserInfo;
	}

	private void kakaoUsersAuthorizationInput(Authentication authentication, HttpServletResponse response) {
		// response header에 token 추가
		UserDetailsImpl userDetailsImpl = ((UserDetailsImpl) authentication.getPrincipal());
		String token = JwtTokenUtils.generateJwtToken(userDetailsImpl);
		response.addHeader("Authorization", "BEARER" + " " + token);
	}

	private Authentication forceLogin(UserDTO kakaoUser) {
		UserDetails userDetails = new UserDetailsImpl(kakaoUser, null);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return authentication;
	}

	private UserDTO registerKakaoUserIfNeed(SocialLoginDTO kakaoUserInfo) throws NoSuchAlgorithmException {
		String socialId = kakaoUserInfo.getSocialId();
		UserDTO kakaoUser = null;
		try {
			 kakaoUser = userService.findByUserId(socialId);
		} catch (Exception e) {
			String kakaoUserName = kakaoUserInfo.getUsername();

			String password = UUID.randomUUID().toString();
			MessageDigest md = MessageDigest.getInstance("SHA-512/256");
			md.update(password.getBytes());
			md.update(kakaoUserName.getBytes());
			String encodedPwd = String.format("%64x", new BigInteger(1, md.digest()));

			kakaoUser = new UserDTO(socialId, encodedPwd, kakaoUserName, false);
		}
		
		return kakaoUser;
	}

	private SocialLoginDTO getKakaoUserInfo(String accessToken) throws JsonMappingException, JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HTTP 요청 보내기
		HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> response = rt.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoUserInfoRequest, String.class);

		String responseBody = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);

		String socialId = jsonNode.get("id").asText();
		String username = jsonNode.get("properties").get("nickname").asText();

		return new SocialLoginDTO(username, null, socialId);
	}

	private String getAccessToken(String code) throws JsonProcessingException {
		// HTTP Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// headers.add("code", code);

		// HTTP Body 생성
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", "16545f631df70673eea3b36abfca50a1"); // 리액트

		body.add("redirect_uri", "http://localhost:8080/tikitaka/user/kakao/callback"); // 리액트

		body.add("code", code);

		// HTTP 요청 보내기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class);

		// HTTP 응답 (JSON) -> 액세스 토큰 파싱
		String responseBody = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		return jsonNode.get("access_token").asText();
	}

}
