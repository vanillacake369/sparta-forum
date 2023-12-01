package com.restapi.spartaforum.domain.question.repository;

import com.restapi.spartaforum.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

//@PreAuthorize("hasRole('USER')")
public interface QuestionRepository extends JpaRepository<Question, Long> {

	//    @PreAuthorize("hasRole('USER')")
	@Override
	<S extends Question> S save(S board);

	//    @PreAuthorize("hasRole('USER')")
	void deleteById(Long aLong);
}
