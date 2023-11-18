package com.restapi.spartaforum.domain.validator;

public enum UserSignUpConstraint {
    USERNAME_CONSTRAINT("^[a-z0-9]*$", 4, 10),
    PASSWORD_CONSTRAINT("^[a-zA-Z0-9]*$", 8, 15);
    final String regex;
    final int lengthMin;
    final int lengthMax;

    UserSignUpConstraint(String regex, int lengthMin, int lengthMax) {
        this.regex = regex;
        this.lengthMin = lengthMin;
        this.lengthMax = lengthMax;
    }
}
