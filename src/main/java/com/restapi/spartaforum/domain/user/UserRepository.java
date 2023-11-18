package com.restapi.spartaforum.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByName(String name);

    Optional<User> findUserByNameAndPassword(String name, String password);

    Optional<User> findUserById(Long id);
}
