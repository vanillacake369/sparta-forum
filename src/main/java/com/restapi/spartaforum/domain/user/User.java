package com.restapi.spartaforum.domain.user;

import com.restapi.spartaforum.domain.board.Board;
import com.restapi.spartaforum.domain.common.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user")
    private Set<Board> questions;

    @Builder
    private User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public static User of(SignUpRequestDTO requestDto) {
        return User.builder()
                .name(requestDto.name())
                .password(requestDto.password())
                .build();
    }

    static boolean hasNotAdminToken(String token) {
        return !ADMIN_TOKEN.equals(token);
    }
}
