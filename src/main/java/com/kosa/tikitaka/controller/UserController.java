package com.kosa.tikitaka.controller;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kosa.tikitaka.model.SignUpRequestDTO;
import com.kosa.tikitaka.model.UserDTO;
import com.kosa.tikitaka.model.UserInfoDTO;
import com.kosa.tikitaka.security.UserDetailsImpl;
import com.kosa.tikitaka.security.UserDetailsServiceImpl;
import com.kosa.tikitaka.security.provider.FormLoginAuthProvider;
import com.kosa.tikitaka.service.GoogleUserService;
import com.kosa.tikitaka.service.KakaoUserService;
import com.kosa.tikitaka.service.NaverUserService;
import com.kosa.tikitaka.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final KakaoUserService kakaoService;
	private final NaverUserService naverService;
	private final GoogleUserService googleService;
	private final UserService userService;
	private final FormLoginAuthProvider formLoginAuthProvider;
	
	@GetMapping("/kakao/callback")
	public RedirectView kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException, NoSuchAlgorithmException {
		if(kakaoService.kakaoLogin(code, response) != null) {
			return new RedirectView("/tikitaka/project/list");
		}
		return new RedirectView("/tikitaka/user/login"); 
	}
	
	@PostMapping("/signUp")
	public boolean signup(@RequestBody SignUpRequestDTO signUpRequestDTO) {
		UserDTO userDto = signUpRequestDTO.getUserDTO();
		UserInfoDTO userInfoDto = signUpRequestDTO.getUserInfoDTO();
		
		return userService.register(userDto, userInfoDto);
	}
	
	@GetMapping("/idCheck")
	public boolean idCheck(@RequestParam String userId) {
		return userService.isCheckUser(userId);
	}
	
	@PostMapping("/login")
	public boolean login(@RequestBody UserDTO userData, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		String userId = userData.getUserId();
		String pwd = userData.getPwd();
		
		return userService.login(userId, pwd);
	}
	
	@GetMapping("/google/callback")
	public void googleLogin(@RequestParam String code, HttpServletResponse response) {
		googleService.googleLogin(code, response);
	}
	
	@GetMapping("/naver/callback")
	public void naverLogin(@RequestParam String code, @RequestParam String state, HttpServletResponse response) throws JsonMappingException, JsonProcessingException, NoSuchAlgorithmException {
		naverService.naverLogin(code, state, response);
	}
}
