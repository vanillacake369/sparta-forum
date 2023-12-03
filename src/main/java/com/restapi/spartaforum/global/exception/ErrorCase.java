package com.restapi.spartaforum.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCase {
	ARGUMENT_NOT_VALID("dataError"),
	EXISTING_USER("existingUser");

	private final String key;
	private int code;
	private String msg;
	private HttpStatus status;

	ErrorCase(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public String getCode() {
		return this.key + ".code";
	}

	public String getMsg() {
		return this.key + ".msg";
	}

	public String getStatus() {
		return this.key + ".status";
	}
}