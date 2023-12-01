package com.restapi.spartaforum.domain.user.entity;

public enum UserRoleEnum {
	USER("RO"
		+ "LE_USER"),
	ADMIN("ROLE_ADMIN");

	private final String role;

	UserRoleEnum(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
