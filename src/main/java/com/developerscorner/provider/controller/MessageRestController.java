package com.developerscorner.provider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developerscorner.provider.model.ChatMessage;
import com.developerscorner.provider.service.ChatMessageService;

@RestController
public class MessageRestController {

	@Autowired
	private ChatMessageService messageService;
	
	@GetMapping("/messages")
	public List<ChatMessage> messages() {
		System.out.println("/messages called ======================================");
		return messageService.findMessages();
	}
}
