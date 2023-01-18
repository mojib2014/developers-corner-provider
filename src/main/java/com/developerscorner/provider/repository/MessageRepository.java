package com.developerscorner.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developerscorner.provider.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

}
