package com.restapi.spartaforum.domain.user.validator;

public enum UserConstraint {
	NICKNAME_CONSTRAINT("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]+$",
		8,
		16
	),
	PASSWORD_CONSTRAINT(
		"^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+{}|\\[\\]:;<>,.?/~`])[a-zA-Z0-9!@#$%^&*()_+{}|\\[\\]:;<>,.?/~`]+$\n",
		8,
		32
	);
	final String regex;
	final int lengthMin;
	final int lengthMax;

	UserConstraint(String regex, int lengthMin, int lengthMax) {
		this.regex = regex;
		this.lengthMin = lengthMin;
		this.lengthMax = lengthMax;
	}
}
