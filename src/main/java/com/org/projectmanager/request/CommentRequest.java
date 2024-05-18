package com.org.projectmanager.request;

public class CommentRequest {

    private Long issueId;
    private Long userId;
    private String comment;

    public String getComment() {
        return comment;
    }

    // Getters and setters for issueId, userId, and comment
    public Long getIssueId() {
        return issueId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public void setIssueId(final Long issueId) {
        this.issueId = issueId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }
}
