package com.developerscorner.provider.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developerscorner.provider.model.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{

	Optional<ChatRoom> findBySenderAndReceiver(Long sender, Long receiver);

//	Optional<Long> findBySenderIdAndRecipientId(Long sender, Long receiver);
}
