package com.org.projectmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.projectmanager.model.Comment;
import com.org.projectmanager.request.CommentRequest;
import com.org.projectmanager.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody final CommentRequest commentRequest) {
        final var issueId = commentRequest.getIssueId();
        final var userId = commentRequest.getUserId();
        final var commentText = commentRequest.getComment();

        final var createdComment = commentService.createComment(issueId, userId, commentText);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComment(@RequestParam("issueId") final Long issueId,
            @RequestParam("userId") final Long userId) {
        commentService.deleteComment(issueId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentsByIssueId(@PathVariable final Long issueId) {
        final var comments = commentService.getCommentsByIssueId(issueId);
        return ResponseEntity.ok(comments);
    }
}
