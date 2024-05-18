package com.org.projectmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.projectmanager.model.Chat;
import com.org.projectmanager.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatRepository chatRepository;
    @Override
    public Chat createChat(Chat chat) {
       return chatRepository.save(chat);
    }

}
