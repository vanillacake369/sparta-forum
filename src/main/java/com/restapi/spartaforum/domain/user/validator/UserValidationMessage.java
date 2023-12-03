package com.restapi.spartaforum.domain.user.validator;

import static com.restapi.spartaforum.domain.user.validator.UserConstraint.NICKNAME_CONSTRAINT;
import static com.restapi.spartaforum.domain.user.validator.UserConstraint.PASSWORD_CONSTRAINT;

public enum UserValidationMessage {
	NULL_ERROR("공백 입력은 불가합니다."),
	TYPE_ERROR("문자열을 입력해주세요."),
	NICK_NAME_LENGTH_ERROR("최소 " + NICKNAME_CONSTRAINT.lengthMin + "자 이상, "
		+ NICKNAME_CONSTRAINT.lengthMax + "자 이하이여야합니다."),
	NICK_NAME_FORMAT_ERROR("알파벳 소문자(a~z) 그리고 숫자(0~9)의 조합으로 구성해주세요."),
	PASSWORD_LENGTH_ERROR(
		"최소 " + PASSWORD_CONSTRAINT.lengthMin + "자 이상, " + PASSWORD_CONSTRAINT.lengthMax + "자 이하이여야 합니다."),
	PASSWORD_FORMAT_ERROR("알파벳 대소문자(a~z,A~Z), 숫자(0~9) 그리고 특수문자의 조합으로 구성해주세요."),
	SIGNUP_SUCCESS("회원가입에 성공하였습니다.");

	final String errorMessage;

	UserValidationMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
