package com.restapi.spartaforum.domain.user.dto;

import com.restapi.spartaforum.domain.user.validator.ValidNickName;
import com.restapi.spartaforum.domain.user.validator.ValidPassword;

public record SignInRequestDTO(
	@ValidNickName
	String nickName,
	@ValidPassword
	String password) {

}
