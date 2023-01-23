package com.developerscorner.provider.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developerscorner.provider.exception.NotFoundException;
import com.developerscorner.provider.model.ChatMessage;
import com.developerscorner.provider.model.ChatMessageStatus;
import com.developerscorner.provider.model.ChatRoom;
import com.developerscorner.provider.repository.ChatMessageRepository;

@Service
public class ChatMessageService {

	@Autowired
	private ChatMessageRepository messageRepo;
	@Autowired
	private ChatRoomService chatRoomService;
	
	public void save(ChatMessage message) {
		messageRepo.save(message);
	}
	
	public Long countNewMessages(Long senderId, Long receiverId) {
		return messageRepo.countBySenderAndReceiverAndStatus(senderId, receiverId, ChatMessageStatus.RECEIVED);
	}
	
	 public List<ChatMessage> findChatMessages(Long sender, Long receiver) {
	        ChatRoom chatRoom = chatRoomService.getChatRoom(sender, receiver);

	        List<ChatMessage> messages = messageRepo.findBySenderAndReceiver(chatRoom.getSender(), chatRoom.getReceiver());

	        if(messages.size() > 0) {
	            updateStatuses(sender, receiver, ChatMessageStatus.DELIVERED);
	        }

	        return messages;
	    }
	 
	  public void updateStatuses(Long senderId, Long recipientId, ChatMessageStatus status) {
//	        Query query = new Query(
//	                Criteria
//	                        .where("senderId").is(senderId)
//	                        .and("recipientId").is(recipientId));
//	        Update update = Update.update("status", status);
//	        mongoOperations.updateMulti(query, update, ChatMessage.class);
	    }
	
	public ChatMessage getMessageById(Long id) {
		return messageRepo.findById(id).orElseThrow(() -> new NotFoundException("Message not found"));
	}
	
	public ChatMessage getMessageBySender(Long id) {
		return messageRepo.findBySender(id).orElseThrow(() -> new NotFoundException("Message not found"));
	}
	
	public ChatMessage getMessageByReciever(Long id) {
		return messageRepo.findByReceiver(id).orElseThrow(() -> new NotFoundException("Message not found"));
	}
	
	public List<ChatMessage> getMessages() {
		return messageRepo.findAll();
	}
}
