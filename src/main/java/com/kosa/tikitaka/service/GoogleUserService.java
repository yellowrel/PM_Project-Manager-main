package com.kosa.tikitaka.service;

import javax.servlet.http.HttpServletResponse;

public interface GoogleUserService {

	public void googleLogin(String code, HttpServletResponse response);

}
