package com.developerscorner.provider.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.developerscorner.provider.dto.ChatDto;
import com.developerscorner.provider.model.ChatMessage;
import com.developerscorner.provider.model.ChatMessageStatus;
import com.developerscorner.provider.service.ChatMessageService;

@Controller
public class ChatMessageController {

	@Autowired
	private ChatMessageService messageService;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/secured/user")
	public void processMessage(@Payload ChatDto dto) {
		ChatMessage message = ChatMessage.builder().sender(dto.getSender()).receiver(dto.getReceiver())
				.message(dto.getMessage()).status(ChatMessageStatus.RECEIVED).createdAt(LocalDateTime.now()).build();
		messageService.save(message);
		messagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/messages", message);
	}

}
