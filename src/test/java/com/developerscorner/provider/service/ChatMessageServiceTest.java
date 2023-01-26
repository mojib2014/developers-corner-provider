package com.developerscorner.provider.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.any;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.developerscorner.provider.ProviderMocksConfig;
import com.developerscorner.provider.exception.NotFoundException;
import com.developerscorner.provider.model.ChatMessage;
import com.developerscorner.provider.model.ChatMessageStatus;
import com.developerscorner.provider.model.User;
import com.developerscorner.provider.repository.ChatMessageRepository;

@ActiveProfiles("test")
@SpringBootTest(classes = { ChatMessageService.class, ProviderMocksConfig.class})
class ChatMessageServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private ChatMessageRepository chatMessageRepo;

	@Autowired
	private ChatMessageService chatMessageService;
	
	private ChatMessage message;
	private List<ChatMessage> messages;
	private User sender = null;
	private User receiver = null;
	
	@BeforeClass
	public void setup() {
		MockitoAnnotations.openMocks(this);
		
		sender = User.builder()
				.id(1L)
				.firstName("sender")
				.lastName("sender")
				.type("Student")
				.email("sender@email.com")
				.password("sender").build();
		receiver = User.builder()
				.id(2L)
				.firstName("receiver")
				.lastName("receiver")
				.type("Student")
				.email("receiver@email.com")
				.password("receiver").build();
		
		messages = new ArrayList<>();
		
		ChatMessage m1 = ChatMessage.builder()
				.id(1L)
				.sender(sender.getEmail())
				.receiver(receiver.getEmail())
				.message("Hello sender")
				.status(ChatMessageStatus.DELIVERED)
				.createdAt(LocalDateTime.now()).build();
		
		message = m1;
		
		ChatMessage m2 = ChatMessage.builder()
				.id(2L)
				.sender(sender.getEmail())
				.receiver(receiver.getEmail())
				.message("my second message")
				.status(ChatMessageStatus.RECEIVED)
				.createdAt(LocalDateTime.now()).build();
		ChatMessage m3 = ChatMessage.builder()
				.id(3L)
				.sender(sender.getEmail())
				.receiver(receiver.getEmail())
				.message("my third message")
				.status(ChatMessageStatus.RECEIVED)
				.createdAt(LocalDateTime.now()).build();
		
		messages.add(m1);
		messages.add(m2);
		messages.add(m3);
	}
	
	@Test
	void shouldSaveChatMessage() {
		when(chatMessageRepo.save(any(ChatMessage.class))).thenReturn(null);
		
		ChatMessage newMessage = ChatMessage.builder()
				.sender("sender")
				.receiver("recevier")
				.message("message")
				.status(ChatMessageStatus.RECEIVED).build();
		
		chatMessageService.save(newMessage);
		
		verify(chatMessageRepo).save(newMessage);
	}
	
	@Test
	void shouldCountNewMessages() {
		when(chatMessageRepo.countBySenderAndReceiverAndStatus(sender.getId(), receiver.getId(), ChatMessageStatus.RECEIVED)).thenReturn(2L);
		
		Long result = chatMessageService.countNewMessages(sender.getId(), receiver.getId());
	
		assertNotNull(result);
		assertTrue(result == 2L);
		verify(chatMessageRepo).countBySenderAndReceiverAndStatus(sender.getId(), receiver.getId(), ChatMessageStatus.RECEIVED);
	}
	
	@Test
	void shouldFindChatMessagesBySenderAndReceiver() {
		when(chatMessageRepo.findBySenderAndReceiver(sender.getId(), receiver.getId())).thenReturn(messages);
		
		List<ChatMessage> result = chatMessageService.findChatMessages(sender.getId(), receiver.getId());
		
		assertNotNull(result);
		verify(chatMessageRepo, atMost(2)).findBySenderAndReceiver(sender.getId(), receiver.getId());
	}
	
	@Test
	void shouldUpdateStatuses() { 
		when(chatMessageRepo.findBySenderAndReceiver(sender.getId(), receiver.getId())).thenReturn(messages);
		
		chatMessageService.updateStatuses(sender.getId(), receiver.getId(), ChatMessageStatus.DELIVERED);
		
		verify(chatMessageRepo, atMost(2)).saveAll(messages);
	}
	
	@Test
	void shouldFindChatMessageById() { 
		when(chatMessageRepo.findById(anyLong())).thenReturn(Optional.of(message));
		
		chatMessageService.findMessageById(message.getId());
		
		verify(chatMessageRepo).findById(anyLong());
	}
	
	@Test
	void shouldFindChatMessageBySender() { 
		when(chatMessageRepo.findBySender(anyLong())).thenReturn(Optional.of(message));
		
		chatMessageService.findMessageBySender(message.getId());
		
		verify(chatMessageRepo).findBySender(anyLong());
	}
	
	@Test
	void shouldFindChatMessageByReceiver() { 
		when(chatMessageRepo.findByReceiver(anyLong())).thenReturn(Optional.of(message));
		
		chatMessageService.findMessageByReciever(message.getId());
		
		verify(chatMessageRepo).findByReceiver(anyLong());
	}
	
	@Test
	void shouldFindChatMessages() {
		when(chatMessageRepo.findAll()).thenReturn(messages);
		
		List<ChatMessage> result = chatMessageService.findMessages();
		
		assertNotNull(result);
		verify(chatMessageRepo).findAll();
	}
	
	// ================== Negative tests ========================

	@Test(expectedExceptions = NotFoundException.class, expectedExceptionsMessageRegExp = "Message not found")
	void shouldThrowNotFoundExceptionIfByIdNotExists() {
		when(chatMessageRepo.findById(anyLong())).thenReturn(Optional.empty());
		
		chatMessageService.findMessageById(100L);
		
		verify(chatMessageRepo).findById(100L);
	}
	
	@Test(expectedExceptions = NotFoundException.class, expectedExceptionsMessageRegExp = "Message not found")
	void shouldThrowNotFoundExceptionIfBySenderNotExists() {
		when(chatMessageRepo.findBySender(anyLong())).thenReturn(Optional.empty());
		
		chatMessageService.findMessageBySender(100L);
		
		verify(chatMessageRepo).findBySender(100L);
	}
	
	@Test(expectedExceptions = NotFoundException.class, expectedExceptionsMessageRegExp = "Message not found")
	void shouldThrowNotFoundExceptionIfByReceiverNotExists() {
		when(chatMessageRepo.findByReceiver(anyLong())).thenReturn(Optional.empty());
		
		chatMessageService.findMessageByReciever(100L);
		
		verify(chatMessageRepo).findByReceiver(100L);
	}
}
