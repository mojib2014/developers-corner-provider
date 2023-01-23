package com.developerscorner.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developerscorner.provider.exception.NotFoundException;
import com.developerscorner.provider.model.ChatRoom;
import com.developerscorner.provider.repository.ChatRoomRepository;

@Service
public class ChatRoomService {

	@Autowired
	private ChatRoomRepository chatRoomRepo;
	
	 public ChatRoom getChatRoom(Long sender, Long receiver) {
	         ChatRoom chatRoom = chatRoomRepo.findBySenderAndReceiver(sender, receiver).orElseThrow(() -> new NotFoundException("Chat room not found"));
	         return chatRoom;
	    }
}
