package com.kosa.tikitaka.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kosa.tikitaka.security.filter.FormLoginFilter;
import com.kosa.tikitaka.security.filter.JwtAuthFilter;
import com.kosa.tikitaka.security.jwt.HeaderTokenExtractor;
import com.kosa.tikitaka.security.provider.FormLoginAuthProvider;
import com.kosa.tikitaka.security.provider.JWTAuthProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final JWTAuthProvider jwtAuthProvider;
	private final HeaderTokenExtractor headerTokenExtractor;
	private final UserDetailsServiceImpl userDetailsServiceImpl;

	public WebSecurityConfig(JWTAuthProvider jwtAuthProvider, HeaderTokenExtractor headerTokenExtractor, UserDetailsServiceImpl userServiceImpl) {
		this.headerTokenExtractor = headerTokenExtractor;
		this.jwtAuthProvider = jwtAuthProvider;
		this.userDetailsServiceImpl = userServiceImpl;
	}

	@Bean
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(formLoginAuthProvider()).authenticationProvider(jwtAuthProvider).userDetailsService(userDetailsServiceImpl);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// CSRF, FrameOptions 무시 (h2-console 등 이용하려면 변경해주어야함)
		//web.ignoring().antMatchers("");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable()
			.csrf().disable()
			.headers().frameOptions();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

		http.formLogin()
			.loginPage("/motion/login")
			.defaultSuccessUrl("/project/list")
			.failureUrl("/motion/login").permitAll();
		
		http.authorizeRequests()
			.antMatchers("/motion/signUp", "/user/**", "/js/**", "/css/**", "/img/**", "/ws-stomp/**")
				.permitAll()
				.antMatchers(HttpMethod.POST, "user").permitAll()
			.and()
				.authorizeRequests()
				.anyRequest()
				.authenticated()
			.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/motion/login")
			.and()
				.exceptionHandling()
				.accessDeniedPage("/forbidden");
	}

	@Bean
	public FormLoginFilter formLoginFilter() throws Exception {
		FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationManager());
		formLoginFilter.setFilterProcessesUrl("/user/login");
		formLoginFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler());
		formLoginFilter.afterPropertiesSet();
		return formLoginFilter;
	}

	@Bean
	public FormLoginSuccessHandler formLoginSuccessHandler() {
		return new FormLoginSuccessHandler();
	}

	@Bean
	public FormLoginAuthProvider formLoginAuthProvider() {
		return new FormLoginAuthProvider(encodePassword());
	}

	private JwtAuthFilter jwtFilter() throws Exception {
		List<String> skipPathList = new ArrayList<>();

		//Static 정보 접근 허용
		//skipPathList.add("GET,/");

		skipPathList.add("GET,/favicon.ico");

		skipPathList.add("GET,/chat/**");
		skipPathList.add("GET,/ws");
		skipPathList.add("GET,/ws/**");
		skipPathList.add("GET,/ws-stomp");
		skipPathList.add("GET,/ws-stomp/**");
		skipPathList.add("GET,/tikitaka/ws-stomp");
		skipPathList.add("GET,/tikitaka/ws-stomp/**");

		FilterSkipMatcher matcher = new FilterSkipMatcher(skipPathList, "/**");

		JwtAuthFilter filter = new JwtAuthFilter(matcher, headerTokenExtractor);

		filter.setAuthenticationManager(super.authenticationManagerBean());
		return filter;
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		//configuration.addAllowedOrigin("http://localhost:3000");
		configuration.addAllowedOrigin("*");
		//configuration.addAllowedOrigin("https://p-m.store");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
