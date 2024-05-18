package com.org.projectmanager.service;

import java.util.List;

import com.org.projectmanager.model.Issue;
import com.org.projectmanager.request.IssueRequest;

public interface IssueService {

    Issue assignUserToIssue(Long issueId, Long userId);

    Issue createIssue(IssueRequest issueRequest, Long userId) throws Exception;

    void deleteIssue(Long issueId);

    Issue getIssueById(Long issueId);

    List<Issue> getIssuesByProjectId(Long projectId);

    Issue updateIssueStatus(Long issueId, String status);
}
