package com.org.projectmanager.service;

import java.util.List;

import com.org.projectmanager.model.Chat;
import com.org.projectmanager.model.Project;
import com.org.projectmanager.model.User;

public interface ProjectService {
    Project createProject(Project project, User user) throws Exception;

    List<Project> getProjectByTeam(User user, String Category, String tag) throws Exception;

    Project getProjectById(Long porjectId) throws Exception;

    void deleteProject(Long projectId, Long userId) throws Exception;

    Project updateProject(Project updatedProject, Long id) throws Exception;

    void addUserToProject(Long projectId, Long userId) throws Exception;

    void removeUserFromProject(Long projectId, Long userId) throws Exception;

    Chat getChatByProjectId(Long projectId);
    
    List<Project> searchProjects(String keyword,User user)throws Exception;
}
