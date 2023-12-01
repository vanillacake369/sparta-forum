package com.restapi.spartaforum.domain.answer.entity;

public enum IsAdopted {
	YES("Y", "채택되었습니다"), NO("N", "채택되지 않았습니다.");

	private final String yesOrNo;

	private final String adoptedKr;

	IsAdopted(String yesOrNo, String adoptedKr) {
		this.yesOrNo = yesOrNo;
		this.adoptedKr = adoptedKr;
	}
}
