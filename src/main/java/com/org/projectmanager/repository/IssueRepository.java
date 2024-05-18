package com.org.projectmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.projectmanager.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByProjectId(Long projectId);
}
