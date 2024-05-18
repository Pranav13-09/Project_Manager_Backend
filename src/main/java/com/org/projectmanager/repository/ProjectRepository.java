package com.org.projectmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.org.projectmanager.model.Project;
import com.org.projectmanager.model.User;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByNameContainingAndTeamContains(String partialName, User user);

    List<Project> findByOwner(User user);

    List<Project> findByTeamContainingOrOwner(User user, User owner);

    @Query("Select p from Project p join p.team t where t=:user")
    List<Project> findProjectByTeam(@Param("user") User user);
}
