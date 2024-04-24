package com.kosa.tikitaka.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kosa.tikitaka.model.ProjectDTO;
import com.kosa.tikitaka.model.UserDTO;
import com.kosa.tikitaka.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserService userService;

	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("UserDetailsServiceImple 호출 ==>");
		UserDTO userDto = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));
		ProjectDTO projectDTO = null;
		return new UserDetailsImpl(userDto, projectDTO);
	}

}
