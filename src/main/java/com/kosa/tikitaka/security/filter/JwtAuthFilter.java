package com.kosa.tikitaka.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.kosa.tikitaka.security.jwt.HeaderTokenExtractor;
import com.kosa.tikitaka.security.jwt.JwtPreProcessingToken;

/*
 Token 을 내려주는 Filter가 아닌 client에서 받아지는 Token을 검증하는 클래스
 Token 값의 인증 상태를 보관하고 필요할 때마다 인증 확인 후 권한 상태 확인
 */
public class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {

	private final HeaderTokenExtractor extractor;

	public JwtAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher, HeaderTokenExtractor extractor) {
		super(requiresAuthenticationRequestMatcher);
		this.extractor = extractor;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String tokenPayload = request.getHeader("Authorization");
		if(tokenPayload ==  null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}

		JwtPreProcessingToken jwtToken = new JwtPreProcessingToken(extractor.extract(tokenPayload, request));
		return super.getAuthenticationManager().authenticate(jwtToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		context.setAuthentication(authResult);
		SecurityContextHolder.setContext(context);

		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		/*
		 로그인을 한 상태에서 Token값을 주고받는 상황 중 잘못 된 Token 값이라면
		 인증이 성공하지 못한 단계이기 때문에 잘못된 Token값을 제거함.
		 모든 인증받은 Context 값이 삭제 됨.
		 */
		SecurityContextHolder.clearContext();
		super.unsuccessfulAuthentication(request, response, failed);
	}
}
