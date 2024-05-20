package com.org.projectmanager.service;

import com.org.projectmanager.model.Comment;
import java.util.List;

public interface CommentService {
  Comment createComment(Long issueId, Long userId, String comment);

  void deleteComment(Long commentId, Long userId);

  List<Comment> getCommentsByIssueId(Long issueId);
}
