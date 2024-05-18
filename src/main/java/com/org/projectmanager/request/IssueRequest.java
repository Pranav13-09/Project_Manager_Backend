package com.org.projectmanager.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class IssueRequest {
    private String title;
    private String description;
    private String status;
    private Date dueDate;
    private Long projectId;
    private String priority;
}
