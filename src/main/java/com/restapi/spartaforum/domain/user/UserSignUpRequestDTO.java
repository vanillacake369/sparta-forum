package com.restapi.spartaforum.domain.user;

import com.restapi.spartaforum.domain.validator.ValidPassword;
import com.restapi.spartaforum.domain.validator.ValidUserName;

public record UserSignUpRequestDTO(
        @ValidUserName
        String name,
        @ValidPassword
        String password) {
}
