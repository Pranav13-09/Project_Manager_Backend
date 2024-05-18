package com.org.projectmanager.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User assignee;

    private String title;
    private String description;
    private String Status;
    private Date dueDate;

    private String priority;
    @ManyToOne
    @JsonIgnore
    private Project project;
    @Lob
    @Column(name = "Tags", columnDefinition = "BLOB")
    private List<String> tags;
    @Lob
    @Column(name = "Comments", columnDefinition = "BLOB")
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> Comments;
}
