package com.restapi.spartaforum.global.exception;

public enum ErrorCase {
	ARGUMENT_NOT_VALID("dataError"),
	EXISTING_USER("existingUser");

	private final String key;

	ErrorCase(String key) {
		this.key = key;
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