package com.org.projectmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.projectmanager.model.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findByEmail(String email);

    Optional<Invitation> findByToken(String token);
}
