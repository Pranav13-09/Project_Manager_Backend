package com.org.projectmanager.service;

import com.org.projectmanager.model.Comment;
import com.org.projectmanager.repository.CommentRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  IssueService issueService;

  @Autowired
  UserService userService;

  @Override
  public Comment createComment(
    final Long issueId,
    final Long userId,
    final String commentText
  ) {
    final var comment = new Comment();
    comment.setIssue(issueService.getIssueById(issueId));
    comment.setUser(userService.findUserById(userId));
    comment.setContent(commentText);
    comment.setCreatedDateTime(LocalDateTime.now());

    return commentRepository.save(comment);
  }

  @Override
  public void deleteComment(final Long commentId, final Long userId) {
    final var comment = commentRepository.findById(commentId);
    final var user = userService.findUserById(userId);
    if (comment.isEmpty()) {
      throw new IllegalArgumentException(
        "Comment not found or you don't have permission to delete it"
      );
    }

    try {
      if (comment.get().getUser().equals(user)) {
        commentRepository.delete(comment.get());
      }
    } catch (final EmptyResultDataAccessException e) {
      throw new IllegalArgumentException(
        "Comment not found or you don't have permission to delete it"
      );
    }
  }

  @Override
  public List<Comment> getCommentsByIssueId(final Long issueId) {
    return commentRepository.findByIssueId(issueId);
  }
}
