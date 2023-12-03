package com.restapi.spartaforum.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restapi.spartaforum.domain.answer.entity.Answer;
import com.restapi.spartaforum.domain.comment.entity.Comment;
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

	@JsonIgnore
	private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

	@JsonIgnore
	@OneToMany(targetEntity = Question.class, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Question> questions = new ArrayList<>();

	@JsonIgnore
	@OneToMany(targetEntity = Answer.class, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Answer> answers = new ArrayList<>();

	@JsonIgnore
	@OneToMany(targetEntity = Comment.class, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Comment> comments = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Long kakaoId;

	@Column(nullable = false)
	private String nickName;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserRoleEnum role;


	@Builder
	public User(String nickName, String password, String email, UserRoleEnum role, Long kakaoId) {
		this.nickName = nickName;
		this.password = password;
		this.email = email;
		this.role = role;
		this.kakaoId = kakaoId;
	}

	public static User of(SignInRequestDTO requestDto) {
		return User.builder()
			.nickName(requestDto.nickName())
			.password(requestDto.password())
			.build();
	}

	public static User of(String name, String password, UserRoleEnum role) {
		return User.builder()
			.nickName(name)
			.password(password)
			.role(role)
			.build();
	}

	public static boolean hasNotAdminToken(String token) {
		return !ADMIN_TOKEN.equals(token);
	}

	public User kakaoIdUpdate(Long kakaoId) {
		this.kakaoId = kakaoId;
		return this;
	}
}
