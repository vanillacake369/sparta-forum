package com.restapi.spartaforum.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByName(String name);

    User findUserById(Long id);
}
