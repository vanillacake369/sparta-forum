package com.restapi.spartaforum.domain.board;

import static java.lang.Boolean.TRUE;

import com.restapi.spartaforum.domain.common.TimeStamp;
import com.restapi.spartaforum.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@RequiredArgsConstructor
@DynamicUpdate
@Table(name = "board")
public class Board extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private String password;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Board(Long id, String title, String author, String password, String content, User user) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.password = password;
        this.content = content;
        this.user = user;
    }

    public Boolean updateBoard(String title, String author, String password, String content) {
        this.title = title;
        this.author = author;
        this.password = password;
        this.content = content;
        return TRUE;
    }
}
