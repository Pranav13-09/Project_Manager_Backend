package com.org.projectmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.projectmanager.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByIssueId(Long issueId);

    Comment findByIssueIdAndUserId(Long issueId, Long userId);

}
