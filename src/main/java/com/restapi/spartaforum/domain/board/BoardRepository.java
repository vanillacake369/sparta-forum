package com.restapi.spartaforum.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    void deleteById(Long aLong);
}
