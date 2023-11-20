package com.restapi.spartaforum.domain.user;

import com.restapi.spartaforum.domain.board.Board;
import com.restapi.spartaforum.domain.comment.Comment;
import com.restapi.spartaforum.domain.common.TimeStamp;
import jakarta.persistence.CascadeType;
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

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Board> questions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;


    @Builder
    private User(String name, String password, UserRoleEnum role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public static User of(SignInRequestDTO requestDto) {
        return User.builder()
                .name(requestDto.username())
                .password(requestDto.password())
                .build();
    }

    public static User of(String name, String password, UserRoleEnum role) {
        return User.builder()
                .name(name)
                .password(password)
                .role(role)
                .build();
    }

    static boolean hasNotAdminToken(String token) {
        return !ADMIN_TOKEN.equals(token);
    }
}
