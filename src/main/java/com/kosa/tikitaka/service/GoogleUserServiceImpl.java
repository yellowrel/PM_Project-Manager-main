package com.kosa.tikitaka.service;

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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosa.tikitaka.model.SocialLoginDTO;
import com.kosa.tikitaka.model.UserDTO;
import com.kosa.tikitaka.security.UserDetailsImpl;
import com.kosa.tikitaka.security.jwt.JwtTokenUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleUserServiceImpl implements GoogleUserService {
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void googleLogin(String code, HttpServletResponse response) {

	}

	private String getAccessToken(String code) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 바디에 필요한 정보 담기
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id" , "418085046130-194eqk6jjjmn32gnfiv6g331ooh985de.apps.googleusercontent.com");
        body.add("client_secret", "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");
        body.add("code", code);
        body.add("redirect_uri", "https://p-m.store/user/google/callback");
//        body.add("redirect_uri", "http://localhost:3000/user/kakao/callback"); // 리액트
        body.add("grant_type", "authorization_code");

        // POST 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleToken = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST, googleToken,
                String.class
        );

        // response에서 엑세스토큰 가져오기
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseToken = objectMapper.readTree(responseBody);
        String accessToken = responseToken.get("access_token").asText();
        return accessToken;
	}
	
	private UserDTO getUser(SocialLoginDTO googleUserInfo) {

        String socialId = googleUserInfo.getSocialId();
        UserDTO googleUser = null;

        if (googleUser == null) {
            String googlename = googleUserInfo.getUsername();
            String nickname = googleUserInfo.getNickname();
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);
            
            String imagePath="https://i.esdrop.com/d/f/JdarL6WQ6C/Myq4zDYYpm.jpg";

			/*
			 * googleUser = new UserDTO (googlename,socialId,encodedPassword,
			 * nickname,userImageUrl,userTitle,userTitleImgUrl);
			 * userRepository.save(googleUser);
			 */
        }

        return googleUser;
    }
	
	 // 4. 시큐리티 강제 로그인
    private Authentication securityLogin(UserDTO findUser) {
        UserDetails userDetails = new UserDetailsImpl(findUser, null);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    // 5. jwt 토큰 발급
    private void jwtToken(Authentication authentication,HttpServletResponse response) {
        UserDetailsImpl userDetailsImpl = ((UserDetailsImpl) authentication.getPrincipal());
        String token = JwtTokenUtils.generateJwtToken(userDetailsImpl);
        response.addHeader("Authorization", "BEARER" + " " + token);

    }
}
