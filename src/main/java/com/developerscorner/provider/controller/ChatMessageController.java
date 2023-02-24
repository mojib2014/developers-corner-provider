package com.developerscorner.provider.controller;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.developerscorner.provider.dto.ChatDto;
import com.developerscorner.provider.model.ChatMessage;
import com.developerscorner.provider.model.ChatMessageStatus;
import com.developerscorner.provider.service.ChatMessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

	private final ChatMessageService messageService;
	private final SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/everyone")
	@SendTo("/all/messages")
	public ChatMessage send(final ChatDto dto) {
		return ChatMessage.builder()
				.sender(dto.getSender())
				.receiver(dto.getReceiver())
				.createdAt(LocalDateTime.now())
				.message(dto.getMessage()).build();
	}

	@MessageMapping("/private-message")
	public void processMessage(@Payload ChatDto dto, Authentication auth) {
		// System.out.println("our sesion is ==================================" +
		// auth.toString());
		ChatMessage message = ChatMessage.builder()
				.sender(dto.getSender())
				.receiver(dto.getReceiver())
				.message(dto.getMessage())
				.status(ChatMessageStatus.RECEIVED)
				.createdAt(LocalDateTime.now())
				.build();

		ChatMessage out = messageService.save(message);

		messagingTemplate.convertAndSendToUser(message.getReceiver(), "/specific", out);
	}

}
