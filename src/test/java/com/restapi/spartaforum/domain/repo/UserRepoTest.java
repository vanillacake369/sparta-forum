package com.restapi.spartaforum.domain.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.restapi.spartaforum.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepoTest {

    @Autowired
    UserRepo userRepo;

    @Test
    @DisplayName("새로운 유저를 생성합니다.")
    public void 유저_생성() throws Exception {
        // GIVEN
        User ji = User.builder()
                .name("ji")
                .password("123").build();

        // WHEN
        User save = userRepo.save(ji);

        // THEN
        assertEquals(ji, save);

    }
}