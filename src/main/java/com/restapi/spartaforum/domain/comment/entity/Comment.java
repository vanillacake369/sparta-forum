package com.restapi.spartaforum.domain.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restapi.spartaforum.domain.answer.entity.Answer;
import com.restapi.spartaforum.domain.question.entity.Question;
import com.restapi.spartaforum.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@Table(name = "comment")
public class Comment {

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Question question;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Answer answer;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String body;
}
