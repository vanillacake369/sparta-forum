package com.restapi.spartaforum.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByName(String name);

    User findUserByNameAndPassword(String name, String password);

    User findUserById(Long id);
}
