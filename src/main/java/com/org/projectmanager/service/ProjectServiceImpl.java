package com.org.projectmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.org.projectmanager.model.Chat;
import com.org.projectmanager.model.PlanType;
import com.org.projectmanager.model.Project;
import com.org.projectmanager.model.User;
import com.org.projectmanager.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;
    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    @Transactional
    public void addUserToProject(final Long projectId, final Long userId) throws Exception {
        final var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new Exception("Project not found with ID: " + projectId));

        final var user = userService.findUserById(userId);
        if (user == null) {
            throw new Exception("User not found with ID: " + userId);
        }

        project.getTeam().add(user);
        project.getChat().getUsers().add(user);
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public Project createProject(final Project project, final User user) throws Exception {
        if (project == null || user == null) {
            throw new IllegalArgumentException("Project and user cannot be null");
        }
        if (user.getProjectSize() == 3
                && PlanType.FREE.equals(subscriptionService.getUsersSubscription(user.getId()).getPlanType())) {
            return null;
        }

        final var createdProject = new Project();
        createdProject.setOwner(user);
        createdProject.setDescription(project.getDescription());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setTags(project.getTags());

        if (createdProject.getTeam() == null) {
            createdProject.setTeam(new ArrayList<>());
        }
        createdProject.getTeam().add(user);
        user.setProjectSize(user.getProjectSize() + 1);
        final var savedProject = projectRepository.save(createdProject);

        final var chat = new Chat();
        chat.setProject(savedProject);
        final var projectChat = chatService.createChat(chat);

        savedProject.setChat(projectChat);
        projectRepository.save(savedProject);

        return savedProject;
    }

    @Override
    public void deleteProject(final Long projectId, final Long userId) throws Exception {
        final var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new Exception("Project not found with ID: " + projectId));

        if (project.getOwner() == null || !project.getOwner().getId().equals(userId)) {
            throw new IllegalAccessException("User not authorized to delete this project");
        }

        projectRepository.delete(project);
    }

    @Override
    public Chat getChatByProjectId(final Long projectId) {
        final var project = projectRepository.getById(projectId);
        return project.getChat();
    }

    @Override
    public Project getProjectById(final Long projectId) throws Exception {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new Exception("Project not found with ID: " + projectId));
    }

    @Override
    public List<Project> getProjectByTeam(final User user, final String category, final String tag) throws Exception {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        final var projects = projectRepository.findByTeamContainingOrOwner(user, user);
        final List<Project> filteredProjects = new ArrayList<>();

        // Filter projects by category
        if (category != null && !category.isEmpty()) {
            for (final Project project : projects) {
                if (category.equals(project.getCategory())) {
                    filteredProjects.add(project);
                }
            }
        } else {
            // If no category filter is applied, add all projects
            filteredProjects.addAll(projects);
        }

        // Filter by tag if specified
        if (tag != null && !tag.isEmpty()) {
            final List<Project> tempProjects = new ArrayList<>(filteredProjects);
            filteredProjects.clear();

            for (final Project project : tempProjects) {
                if (project.getTags() != null && project.getTags().contains(tag)) {
                    filteredProjects.add(project);
                }
            }
        }

        return filteredProjects;
    }

    @Override
    @Transactional
    public void removeUserFromProject(final Long projectId, final Long userId) throws Exception {
        final var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new Exception("Project not found with ID: " + projectId));
        final var user = userService.findUserById(userId);
        if (user == null) {
            throw new Exception("User not found with ID: " + userId);
        }
        project.getTeam().remove(user);
        project.getChat().getUsers().remove(user);
        projectRepository.save(project);
    }

    @Override
    public List<Project> searchProjects(final String keyword, final User user) throws Exception {
        final var partialName = "%" + keyword + "%";
        return projectRepository.findByNameContainingAndTeamContains(partialName, user);
    }

    @Override
    @Transactional
    public Project updateProject(final Project updatedProject, final Long id) throws Exception {
        if (updatedProject == null || id == null) {
            throw new IllegalArgumentException("Project and ID must not be null");
        }

        final var project = projectRepository.findById(id)
                .orElseThrow(() -> new Exception("Project not found with ID: " + id));

        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setCategory(updatedProject.getCategory());
        project.setTags(updatedProject.getTags());
        // Assume other fields are updated similarly

        return projectRepository.save(project);
    }
}
