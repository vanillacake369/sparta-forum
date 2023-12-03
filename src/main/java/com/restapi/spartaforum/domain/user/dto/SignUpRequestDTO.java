package com.restapi.spartaforum.domain.user.dto;

import com.restapi.spartaforum.domain.user.validator.ValidNickName;
import com.restapi.spartaforum.domain.user.validator.ValidPassword;

public record SignUpRequestDTO(
	String nickName,
	@ValidNickName
	String email,
	@ValidPassword
	String password,
	boolean isAdmin,
	String adminToken) {

}
