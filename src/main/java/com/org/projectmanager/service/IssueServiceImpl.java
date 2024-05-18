package com.org.projectmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.projectmanager.model.Issue;
import com.org.projectmanager.repository.IssueRepository;
import com.org.projectmanager.request.IssueRequest;

import jakarta.persistence.EntityNotFoundException;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @Override
    public Issue assignUserToIssue(final Long issueId, final Long userId) {
        final var optionalIssue = issueRepository.findById(issueId);
        if (optionalIssue.isPresent()) {
            final var issue = optionalIssue.get();
            final var user = userService.findUserById(userId);
            issue.setAssignee(user);
            return issueRepository.save(issue);
        }
        throw new RuntimeException("Issue not found with ID: " + issueId);
    }

    @Override
    public Issue createIssue(final IssueRequest issueRequest, final Long userId) throws Exception {
        final var issue = new Issue();
        issue.setTitle(issueRequest.getTitle());
        issue.setDescription(issueRequest.getDescription());
        issue.setStatus(issueRequest.getStatus());
        issue.setDueDate(issueRequest.getDueDate());

        issue.setPriority(issueRequest.getPriority());
        issue.setProject(projectService.getProjectById(issueRequest.getProjectId()));

        // Save the issue using the repository
        return issueRepository.save(issue);
    }

    @Override
    public void deleteIssue(final Long issueId) {
        final var optionalIssue = issueRepository.findById(issueId);
        if (!optionalIssue.isPresent()) {
            throw new EntityNotFoundException("Issue not found with ID: " + issueId);
        }
        issueRepository.deleteById(issueId);
    }

    @Override
    public Issue getIssueById(final Long issueId) {
        final var optionalIssue = issueRepository.findById(issueId);
        if (optionalIssue.isPresent()) {
            return optionalIssue.get();
        }
        throw new RuntimeException("Issue not found with ID: " + issueId);
    }

    @Override
    public List<Issue> getIssuesByProjectId(final Long projectId) {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue updateIssueStatus(final Long issueId, final String status) {
        final var optionalIssue = issueRepository.findById(issueId);
        if (optionalIssue.isPresent()) {
            final var issue = optionalIssue.get();
            issue.setStatus(status);
            return issueRepository.save(issue);
        }
        throw new RuntimeException("Issue not found with ID: " + issueId);
    }
}
