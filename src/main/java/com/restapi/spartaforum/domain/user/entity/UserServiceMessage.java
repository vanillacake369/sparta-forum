package com.restapi.spartaforum.domain.user.entity;

public enum UserServiceMessage {
	ADMIN_FAILED("관리자 권한을 위한 암호가 틀립니다."),
	NOT_FOUND_USER("등록된 사용자가 없습니다."),
	SIGNIN_FAILED("로그인에 실패하였습니다."),
	SIGNIN_SUCCESS("로그인에 성공하였습니다."),
	SIGNUP_FAILED("회원가입에 실패하였습니다."),
	SIGNUP_SUCCESS("회원가입에 성공하였습니다.");

	final String message;

	UserServiceMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
