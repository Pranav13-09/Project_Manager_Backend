package com.org.projectmanager.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.projectmanager.model.Message;
import com.org.projectmanager.repository.MessageRepository;
import com.org.projectmanager.repository.ProjectRepository;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Message> getMessagesByProjectId(final Long projectId) {
        final var project = projectRepository.findById(projectId).get();
        return messageRepository.findByChatIdOrderByCreatedATAsc(project.getChat().getId());
    }

    @Override
    public Message sendMessage(final Long projectId, final Long senderId, final String content) {
        final var message = new Message();
        message.setChat(projectRepository.findById(projectId).get().getChat());
        message.setSender(userService.findUserById(senderId));
        message.setContent(content);
        message.setCreatedAT(LocalDateTime.now());

        return messageRepository.save(message);
    }
}
