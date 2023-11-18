package com.restapi.spartaforum.domain.user;

import com.restapi.spartaforum.domain.validator.ValidPassword;
import com.restapi.spartaforum.domain.validator.ValidUserName;

public record SignUpRequestDTO(
        @ValidUserName
        String name,
        @ValidPassword
        String password,
        boolean isAdmin,
        String adminToken) {
}
