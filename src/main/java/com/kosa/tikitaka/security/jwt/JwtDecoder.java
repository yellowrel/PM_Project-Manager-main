package com.kosa.tikitaka.security.jwt;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JwtDecoder {
	public String decodeUsername(String token) {
		DecodedJWT decodedJWT = isValidToken(token).orElseThrow(() -> new IllegalArgumentException("유효한 토큰이 아닙니다."));

		Date expiredDate = decodedJWT
				.getClaim(JwtTokenUtils.CLAIM_EXPIRED_DATE)
				.asDate();

		Date now = new Date();
		if(expiredDate.before(now)) {
			throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
		}

		String userId = decodedJWT
				.getClaim(JwtTokenUtils.CLAIM_USER_NAME)
				.asString();

		return userId;
	}

	private Optional<DecodedJWT> isValidToken(String token) {
		DecodedJWT jwt = null;

		try {
			Algorithm algorithm = Algorithm.HMAC256(JwtTokenUtils.JWT_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();

			jwt = verifier.verify(token);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.ofNullable(jwt);
	}
}
