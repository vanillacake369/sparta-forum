package com.restapi.spartaforum.global.exception;

public enum ErrorCase {
	/* DATA */
	ARGUMENT_NOT_VALID("dataError"),
	/* USER */
	ACCESS_DENIED("accessDeniedError"),
	EXISTING_USER("existingUser"),
	NOT_SIGNED_IN("requiresLogin"),
	/* QUESTION */
	DUPLICATED_QUESTION("duplicatedQuestion"),
	NOT_AUTHOR_QUESTION("notAuthorOfQuestion"),
	/* ANSWER */
	DUPLICATED_ANSWER("duplicatedAnswer"),
	UNMATCHED_PARENT_QUESTION("unmatchedParentQuestion"),
	NOT_AUTHOR_ANSWER("notAuthorOfAnswer"),
	/* COMMENT */
	UNMATCHED_PARENT_POST("unmatchedParentPost"),
	DUPLICATED_COMMENT("duplicatedComment"),
	NOT_AUTHOR_COMMENT("notAuthorOfComment");
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