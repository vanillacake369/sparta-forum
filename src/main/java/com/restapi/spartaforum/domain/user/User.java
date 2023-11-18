package com.restapi.spartaforum.domain.user;

import com.restapi.spartaforum.domain.board.Board;
import com.restapi.spartaforum.domain.common.TimeStamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "user")
public class User extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Board> questions;

    @Builder
    private User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public static User of(UserSignUpRequestDTO requestDto) {
        return User.builder()
                .name(requestDto.name())
                .password(requestDto.password())
                .build();
    }
}
