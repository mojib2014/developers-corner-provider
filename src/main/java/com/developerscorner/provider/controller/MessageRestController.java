package com.developerscorner.provider.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developerscorner.provider.model.ChatMessage;
import com.developerscorner.provider.service.ChatMessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageRestController {

	private final ChatMessageService messageService;

	@GetMapping("/messages")
	public List<ChatMessage> messages() {
		System.out.println("/messages called ======================================");
		return messageService.findMessages();
	}
}
