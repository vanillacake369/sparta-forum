package com.restapi.spartaforum.domain.user.dto;

public record KakaoUserInfoDto(Long id, String nickname, String email) {

	@Override
	public String toString() {
		return "KakaoUserInfoDto{" +
			"id=" + id +
			", nickname='" + nickname + '\'' +
			", email='" + email + '\'' +
			'}';
	}
}
