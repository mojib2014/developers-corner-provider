package com.developerscorner.provider.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class QuestionDto {
	
	@NotNull(message = "Username is required field")
	private String username;
	
	@NotNull(message = "Tags is required field")
	private String tags;
	
	@NotNull(message = "Question is required field")
	private String question;
	
	@NotNull(message = "User Id is required field")
	private Long userId;
}
