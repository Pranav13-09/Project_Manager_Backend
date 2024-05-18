package com.org.projectmanager.service;

import java.util.List;

import com.org.projectmanager.model.Message;

public interface MessageService {

    List<Message> getMessagesByProjectId(Long projectId);

    Message sendMessage(Long projectId, Long senderId, String content);
}
