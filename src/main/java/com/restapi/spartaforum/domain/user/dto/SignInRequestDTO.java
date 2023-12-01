package com.restapi.spartaforum.domain.user.dto;

import com.restapi.spartaforum.domain.user.validator.ValidPassword;
import com.restapi.spartaforum.domain.user.validator.ValidUserName;

public record SignInRequestDTO(
	@ValidUserName
	String nickName,
	@ValidPassword
	String password) {

}
