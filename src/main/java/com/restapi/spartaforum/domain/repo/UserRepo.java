package com.restapi.spartaforum.domain.repo;

import com.restapi.spartaforum.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
