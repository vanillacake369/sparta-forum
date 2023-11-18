package com.restapi.spartaforum.domain.validator;

import static com.restapi.spartaforum.domain.validator.UserSignUpConstraint.PASSWORD_CONSTRAINT;
import static com.restapi.spartaforum.domain.validator.UserSignUpConstraint.USERNAME_CONSTRAINT;

public enum UserStatusMessage {
    NULL_ERROR("공백 입력은 불가합니다."),
    TYPE_ERROR("문자열을 입력해주세요."),
    USER_NAME_LENGTH_ERROR("최소 " + USERNAME_CONSTRAINT.lengthMin + "자 이상, "
            + USERNAME_CONSTRAINT.lengthMax + "자 이하이여야합니다."),
    USER_NAME_FORMAT_ERROR("알파벳 소문자(a~z), 숫자(0~9) 로 구성해주세요."),
    PASSWORD_LENGTH_ERROR(
            "최소 " + PASSWORD_CONSTRAINT.lengthMin + "자 이상, " + PASSWORD_CONSTRAINT.lengthMax + "자 이하이여야 합니다."),
    PASSWORD_FORMAT_ERROR("알파벳 대소문자(a~z,A~Z), 숫자(0~9) 로 구성해주세요."),
    SIGNUP_SUCCESS("회원가입에 성공하였습니다.");

    final String errorMessage;

    UserStatusMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return errorMessage;
    }
}
