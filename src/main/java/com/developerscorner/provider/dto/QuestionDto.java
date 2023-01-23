package com.developerscorner.provider.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
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
