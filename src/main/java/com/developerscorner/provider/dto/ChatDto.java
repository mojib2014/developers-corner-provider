package com.developerscorner.provider.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChatDto {
	
	private String username;
	private Long userId;
	private String message;
}
