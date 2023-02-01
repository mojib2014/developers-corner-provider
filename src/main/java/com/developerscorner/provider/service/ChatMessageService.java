package com.developerscorner.provider.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developerscorner.provider.exception.NotFoundException;
import com.developerscorner.provider.model.ChatMessage;
import com.developerscorner.provider.model.ChatMessageStatus;
import com.developerscorner.provider.repository.ChatMessageRepository;

@Service
public class ChatMessageService {

	@Autowired
	private ChatMessageRepository messageRepo;
	
	public ChatMessage save(ChatMessage message) {
		return messageRepo.saveAndFlush(message);
	}
	
	public Long countNewMessages(Long senderId, Long receiverId) {
		return messageRepo.countBySenderAndReceiverAndStatus(senderId, receiverId, ChatMessageStatus.RECEIVED);
	}
	
	 public List<ChatMessage> findChatMessages(Long sender, Long receiver) {
	        //ChatRoom chatRoom = chatRoomService.getChatRoom(sender, receiver);

	        List<ChatMessage> messages = messageRepo.findBySenderAndReceiver(sender, receiver);

	        if(messages.size() > 0) {
	            updateStatuses(sender, receiver, ChatMessageStatus.DELIVERED);
	        }

	        return messages;
	    }
	 
	  public void updateStatuses(Long sender, Long receiver, ChatMessageStatus status) {
		  List<ChatMessage> messages = messageRepo.findBySenderAndReceiver(sender, receiver);
	      
		  if(messages.size() > 0) {
			  messages.forEach(message -> {
				  message.setStatus(status);
			  }); 
		  }
		  messageRepo.saveAll(messages);
	 }
	
	public ChatMessage findMessageById(Long id) {
		return messageRepo.findById(id).orElseThrow(() -> new NotFoundException("Message not found"));
	}
	
	public ChatMessage findMessageBySender(Long id) {
		return messageRepo.findBySender(id).orElseThrow(() -> new NotFoundException("Message not found"));
	}
	
	public ChatMessage findMessageByReciever(Long id) {
		return messageRepo.findByReceiver(id).orElseThrow(() -> new NotFoundException("Message not found"));
	}
	
	public List<ChatMessage> findMessages() {
		return messageRepo.findAll();
	}
}
