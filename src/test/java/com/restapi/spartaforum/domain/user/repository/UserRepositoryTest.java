package com.restapi.spartaforum.domain.user.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.restapi.spartaforum.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	public void insertFail() {
		// GIVEN
		User jihoon = User.builder()
			.nickName("jihoon").build();

		// WHEN
		DataIntegrityViolationException constraintViolationException = assertThrows(
			DataIntegrityViolationException.class,
			() -> {
				userRepository.save(jihoon);
//				userRepository.saveAndFlush(jihoon);
			});

		// THEN
		assertEquals(constraintViolationException.getClass(), DataIntegrityViolationException.class);
		System.out.println("constraintViolationException.getMessage() = " + constraintViolationException.getMessage());
	}
}