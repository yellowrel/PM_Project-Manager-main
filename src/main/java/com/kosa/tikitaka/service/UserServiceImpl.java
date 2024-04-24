package com.kosa.tikitaka.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.kosa.tikitaka.model.UserDTO;
import com.kosa.tikitaka.model.UserInfoDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	private SqlSessionTemplate sqlSession;

	public UserServiceImpl(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
		log.info("UserServiceImpl SqlSession On -> " + this.sqlSession);
	}

	@Override
	public Optional<UserDTO> findByUsername(String username) {
		UserDTO userDTO = sqlSession.selectOne("userService.selectById", username);
		return Optional.ofNullable(userDTO);
	}

	@Override
	public UserInfoDTO findUserInfoByUsername(String username) {
		return sqlSession.selectOne("userService.selectUserInfoById", username);
	}

	@Override
	public boolean isCheckUser(String userId) {
		boolean ischeck = false;
		log.info("isCheckUser 호출 ==>");
		String result = sqlSession.selectOne("userService.idCheck", userId);
		System.out.println(result);
		if (result == null)
			ischeck = true;

		return ischeck;
	}

	@Override
	public boolean register(UserDTO userDTO, UserInfoDTO userInfoDTO) {
		boolean result = true;
		String userId = userDTO.getUserId();

		Optional<UserDTO> foundName = findByUsername(userId);

		if (foundName.isPresent()) {
			result = false;
			return result;
		}

		SecureRandom random = null;
		String salt;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[8];
			random.nextBytes(bytes);
			salt = new String(Base64.getEncoder().encode(bytes));

			userDTO.setSalt(salt);

			MessageDigest md = MessageDigest.getInstance("SHA-512/256");
			md.update(userDTO.getPwd().getBytes());
			md.update(userDTO.getSalt().getBytes());
			String hex = String.format("%64x", new BigInteger(1, md.digest()));

			userDTO.setPwd(hex);

			sqlSession.insert("userService.joinUser", userDTO);
			sqlSession.insert("userService.insertUser", userInfoDTO);
			// sqlSession.insert("userService.insertProfile", userDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public UserDTO findByUserId(String userId) {
		return sqlSession.selectOne("userService.selectById", userId);
	}

	@Override
	public boolean login(String userId, String pwd) {
		boolean result = false;

		Optional<UserDTO> foundName = findByUsername(userId);

		if (!foundName.isPresent()) {
			return result;
		}
		
		String dbSalt = sqlSession.selectOne("userService.selectSalt", userId);

		MessageDigest md;
		String hex = null;
		try {
			md = MessageDigest.getInstance("SHA-512/256");
			md.update(pwd.getBytes());
			md.update(dbSalt.getBytes());
			hex = String.format("%64x", new BigInteger(1, md.digest()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String dbPwd = sqlSession.selectOne("userService.selectPwd", userId);
		
		if(hex.equals(dbPwd)) {
			result = true;
			return result;
		}
		
		return result;
	}
}
