package com.kosa.tikitaka.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.kosa.tikitaka.model.ProjectDTO;
import com.kosa.tikitaka.model.UserDTO;
import com.kosa.tikitaka.security.UserDetailsImpl;
import com.kosa.tikitaka.security.jwt.JwtDecoder;
import com.kosa.tikitaka.security.jwt.JwtPreProcessingToken;
import com.kosa.tikitaka.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthProvider implements AuthenticationProvider {

	private final JwtDecoder jwtDecoder;

	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = (String) authentication.getPrincipal();
		String username = jwtDecoder.decodeUsername(token);

		UserDTO user = userService.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Can't find" + username));
		ProjectDTO project = null;
		UserDetailsImpl userDetails = new UserDetailsImpl(user, project);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtPreProcessingToken.class.isAssignableFrom(authentication);
	}

}
