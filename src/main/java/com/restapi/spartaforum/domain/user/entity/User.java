package com.restapi.spartaforum.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restapi.spartaforum.domain.comment.Comment;
import com.restapi.spartaforum.domain.common.TimeStamp;
import com.restapi.spartaforum.domain.question.entity.Question;
import com.restapi.spartaforum.domain.user.dto.SignInRequestDTO;
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
import java.util.ArrayList;
import java.util.List;
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
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserRoleEnum role;

	@JsonIgnore
	@OneToMany(targetEntity = Question.class, mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Question> questions = new ArrayList<>();

	@JsonIgnore
	@OneToMany(targetEntity = Comment.class, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Comment> comments = new ArrayList<>();


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

	public static boolean hasNotAdminToken(String token) {
		return !ADMIN_TOKEN.equals(token);
	}
}
