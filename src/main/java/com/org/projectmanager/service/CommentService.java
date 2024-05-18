package com.org.projectmanager.service;

import java.util.List;

import com.org.projectmanager.model.Comment;

public interface CommentService {

    Comment createComment(Long issueId, Long userId, String comment);

    void deleteComment(Long issueId, Long userId);

    List<Comment> getCommentsByIssueId(Long issueId);
}
