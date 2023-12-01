package com.restapi.spartaforum.domain.user.repository;

import com.restapi.spartaforum.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByName(String name);

	Optional<User> findUserByNameAndPassword(String name, String password);

	Optional<User> findUserById(Long id);
}
