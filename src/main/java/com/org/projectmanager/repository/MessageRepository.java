package com.org.projectmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.projectmanager.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChatIdOrderByCreatedATAsc(Long chatId);

}
