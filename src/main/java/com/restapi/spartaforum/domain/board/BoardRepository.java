package com.restapi.spartaforum.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

//@PreAuthorize("hasRole('USER')")
public interface BoardRepository extends JpaRepository<Board, Long> {
    @PreAuthorize("hasRole('USER')")
    @Override
    <S extends Board> S save(S board);

    @PreAuthorize("hasRole('USER')")
    void deleteById(Long aLong);
}
