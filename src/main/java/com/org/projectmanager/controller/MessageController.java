package com.org.projectmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.projectmanager.model.Message;
import com.org.projectmanager.request.MessageRequest;
import com.org.projectmanager.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByProjectId(@PathVariable final Long projectId) {
        final var messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody final MessageRequest messageRequest) {
        final var chatId = messageRequest.getProjectId();
        final var senderId = messageRequest.getSenderId();
        final var content = messageRequest.getContent();

        final var sentMessage = messageService.sendMessage(chatId, senderId, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(sentMessage);
    }
}
