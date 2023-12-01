package com.restapi.spartaforum.domain.question.entity;

import static java.lang.Boolean.TRUE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restapi.spartaforum.domain.answer.entity.Answer;
import com.restapi.spartaforum.domain.comment.entity.Comment;
import com.restapi.spartaforum.domain.common.TimeStamp;
import com.restapi.spartaforum.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@RequiredArgsConstructor
@Table(name = "question")
public class Question extends TimeStamp {

	@JsonIgnore
	@OneToMany(targetEntity = Answer.class, mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Answer> answers = new ArrayList<>();

	@JsonIgnore
	@OneToMany(targetEntity = Comment.class, mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Comment> comments = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User author;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String body;

	@Column
	private Long likes;

	@Column
	private Long dislikes;

	@Builder
	public Question(Long id, String title, String body, User author) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.author = author;
	}

	public Boolean updateQuestion(String title, String content) {
		this.title = title;
		this.body = content;
		return TRUE;
	}
}
