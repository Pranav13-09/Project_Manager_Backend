package com.org.projectmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.projectmanager.model.Chat;
import com.org.projectmanager.model.Invitation;
import com.org.projectmanager.model.Project;
import com.org.projectmanager.request.InviteRequest;
import com.org.projectmanager.service.InvitationService;
import com.org.projectmanager.service.ProjectService;
import com.org.projectmanager.service.UserService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private InvitationService invitationService;

    @GetMapping("/acceptInvite")
    public ResponseEntity<Invitation> acceptInviation(@RequestParam final String token,
            @RequestHeader("Authorisation") final String jwt) {
        try {
            final var user = userService.findUserByJwt(jwt);
            final var invitation = invitationService.acceptInvitation(token, user.getId());
            projectService.addUserToProject(invitation.getProjectId(), user.getId());
            return ResponseEntity.ok(invitation);
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{projectId}/addUser")
    public ResponseEntity<Void> addUserToProject(@PathVariable final Long projectId,
            @RequestParam("userId") final Long userId) {
        try {
            projectService.addUserToProject(projectId, userId);
            return ResponseEntity.ok().build();
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody final Project project,
            @RequestHeader("Authorization") final String jwt) {
        try {
            final var user = userService.findUserByJwt(jwt); // Assume UserService or similar to fetch user

            final var createdProject = projectService.createProject(project, user);
            return ResponseEntity.ok(createdProject);
        } catch (final Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable final Long projectId,
            @RequestHeader("Authorization") final String jwt) {
        try {
            final var userId = userService.findUserByJwt(jwt).getId();
            projectService.deleteProject(projectId, userId);
            return ResponseEntity.ok().build();
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatFromProject(@PathVariable final Long projectId) {
        try {
            final var chat = projectService.getChatByProjectId(projectId);
            return ResponseEntity.ok(chat);
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable final Long projectId) {
        try {
            final var project = projectService.getProjectById(projectId);
            return ResponseEntity.ok(project);
        } catch (final Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProjectsByTeam(@RequestParam(required = false) final String category,
            @RequestParam(required = false) final String tag, @RequestHeader("Authorization") final String jwt) {
        try {
            final var user = userService.findUserByJwt(jwt);
            final var projects = projectService.getProjectByTeam(user, category, tag);
            return ResponseEntity.ok(projects);
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{projectId}/removeUser")
    public ResponseEntity<Void> removeUserFromProject(@PathVariable final Long projectId,
            @RequestParam("userId") final Long userId) {
        try {
            projectService.removeUserFromProject(projectId, userId);
            return ResponseEntity.ok().build();
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam("keyword") final String keyword,
            @RequestHeader("Authorization") final String jwt) {
        try {
            final var user = userService.findUserByJwt(jwt);
            final var projects = projectService.searchProjects(keyword, user);
            return ResponseEntity.ok(projects);
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/sendInvitation")
    public ResponseEntity<String> sendInvitation(@RequestBody final InviteRequest invitationRequest) {
        try {
            invitationService.sendInvitation(invitationRequest.getEmail(), invitationRequest.getProjectId());
            return ResponseEntity.ok("Invitation sent successfully.");
        } catch (final Exception e) {
            return ResponseEntity.badRequest().body("Failed to send invitation.");
        }
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@RequestBody final Project project,
            @PathVariable final Long projectId) {
        try {
            final var updatedProject = projectService.updateProject(project, projectId);
            return ResponseEntity.ok(updatedProject);
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
