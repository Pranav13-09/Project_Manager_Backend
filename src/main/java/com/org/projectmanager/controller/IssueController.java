package com.org.projectmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.projectmanager.model.Issue;
import com.org.projectmanager.request.IssueRequest;
import com.org.projectmanager.service.IssueService;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @PostMapping("/assignUser")
    public ResponseEntity<Issue> assignUserToIssue(@RequestParam("issueId") final Long issueId,
            @RequestParam("userId") final Long userId) {
        final var updatedIssue = issueService.assignUserToIssue(issueId, userId);
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping
    public ResponseEntity<Issue> createIssue(@RequestBody final IssueRequest issueRequest,
            @RequestParam("userId") final Long userId) {
        try {
            final var createdIssue = issueService.createIssue(issueRequest, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdIssue);
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<Void> deleteIssue(@PathVariable final Long issueId) {
        issueService.deleteIssue(issueId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable final Long issueId) {
        final var issue = issueService.getIssueById(issueId);
        return ResponseEntity.ok(issue);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssuesByProjectId(@PathVariable final Long projectId) {
        final var issues = issueService.getIssuesByProjectId(projectId);
        return ResponseEntity.ok(issues);
    }

    @PutMapping("/{issueId}")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable final Long issueId,
            @RequestParam("status") final String status) {
        final var updatedIssue = issueService.updateIssueStatus(issueId, status);
        return ResponseEntity.ok(updatedIssue);
    }
}
