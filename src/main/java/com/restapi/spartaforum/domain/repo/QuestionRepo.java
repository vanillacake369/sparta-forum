package com.restapi.spartaforum.domain.repo;

import com.restapi.spartaforum.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Question, Long> {
    void deleteById(Long aLong);
}
