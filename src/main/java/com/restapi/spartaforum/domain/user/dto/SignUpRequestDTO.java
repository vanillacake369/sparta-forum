package com.restapi.spartaforum.domain.user.dto;

import com.restapi.spartaforum.domain.user.validator.ValidPassword;
import com.restapi.spartaforum.domain.user.validator.ValidUserName;

public record SignUpRequestDTO(
	String nickName,
	@ValidUserName
	String email,
	@ValidPassword
	String password,
	boolean isAdmin,
	String adminToken) {

}
