package com.restapi.spartaforum.domain.question.entity;

import static java.lang.Boolean.TRUE;

import com.restapi.spartaforum.domain.common.TimeStamp;
import com.restapi.spartaforum.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Question extends TimeStamp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User author;

	@Builder
	public Question(Long id, String title, String content, User author) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public Boolean updateBoard(String title, String content) {
		this.title = title;
		this.content = content;
		return TRUE;
	}
}