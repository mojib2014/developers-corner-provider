package com.developerscorner.provider.model;


import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "username is required field")
	private String username;

	@NotNull(message = "Tags is required field")
	private String tags;

	@NotNull(message = "Question is required field")
	private String question;

	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDate createdAt;

	@ManyToOne
	@JoinColumn(name = "userId")
	@JsonManagedReference("questions")
	private User user;
}
