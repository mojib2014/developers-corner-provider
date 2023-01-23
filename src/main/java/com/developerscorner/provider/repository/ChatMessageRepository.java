package com.developerscorner.provider.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developerscorner.provider.model.ChatMessage;
import com.developerscorner.provider.model.ChatMessageStatus;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{

	Optional<ChatMessage> findBySender(Long id);

	Optional<ChatMessage> findByReceiver(Long id);

	Long countBySenderAndReceiverAndStatus(Long senderId, Long receiverId, ChatMessageStatus received);

	List<ChatMessage> findBySenderAndReceiver(Long sender, Long receiver);

}
