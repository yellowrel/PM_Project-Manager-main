package com.kosa.tikitaka.security.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kosa.tikitaka.security.UserDetailsImpl;

import lombok.Getter;

@Getter
public class JwtTokenUtils {

		private static final int SEC = 1;
		private static final int MINUTE = 60 * SEC;
		private static final int HOUR = 60 * MINUTE;
		private static final int DAY = 24 * HOUR;

		//JWT 토큰의 유효기간 : 1일 (단위: seconds)
		private static final int JWT_TOKEN_VALID_SEC = 1 * DAY;
		//JWT 토큰의 유효기간 : 1일 (단위: milliseconds)
		private static final int JWT_TOKEN_VALID_MILLI_SEC = JWT_TOKEN_VALID_SEC * 1000;

		public static final String CLAIM_EXPIRED_DATE = "EXPIRED_DATE";
		public static final String CLAIM_USER_NAME = "USER_NAME";
		public static final String JWT_SECRET = "jwt_secret_!@#$%";

		public static String generateJwtToken(UserDetailsImpl userDetails) {
			String token = null;
			try {
				token = JWT.create()
						.withIssuer("tikitaka")
						.withClaim(CLAIM_USER_NAME, userDetails.getUsername())
						.withClaim(CLAIM_EXPIRED_DATE, new Date())
						.sign(generatedAlgorithm());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return token;
		}

		private static Algorithm generatedAlgorithm() {
			return Algorithm.HMAC256(JWT_SECRET);
		}
}
