package com.org.projectmanager.request;

public class MessageRequest {

    private Long projectId;
    private Long senderId;
    private String content;

    public String getContent() {
        return content;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void setSenderId(final Long senderId) {
        this.senderId = senderId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
