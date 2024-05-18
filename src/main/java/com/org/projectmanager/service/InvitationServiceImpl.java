package com.org.projectmanager.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.projectmanager.model.Invitation;
import com.org.projectmanager.repository.InvitationRepository;

@Service
public class InvitationServiceImpl implements InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public Invitation acceptInvitation(final String token, final Long userId) {
        Optional<Invitation> invitationOpt = invitationRepository.findByToken(token);
        if (invitationOpt.isPresent()) {
            Invitation invitation = invitationOpt.get();
            // Implement the logic to associate the user with the project
            // For now, let's just return the invitation
            return invitation;
        } else {
            throw new RuntimeException("Invalid or expired token.");
        }
    }

    @Override
    public void deleteToken(final String token) {
        Optional<Invitation> invitationOpt = invitationRepository.findByToken(token);
        if (invitationOpt.isPresent()) {
            invitationRepository.delete(invitationOpt.get());
        } else {
            throw new RuntimeException("Token not found.");
        }
    }

    @Override
    public String getTokenByMail(final String email) {
        Optional<Invitation> invitationOpt = invitationRepository.findByEmail(email);
        if (invitationOpt.isPresent()) {
            return invitationOpt.get().getToken();
        } else {
            throw new RuntimeException("No invitation found for the given email.");
        }
    }

    @Override
    public void sendInvitation(final String email, final Long projectId) {
        String token = UUID.randomUUID().toString();
        String link = "http://localhost:5173/accept_invitation?token=" + token;

        Invitation invitation = new Invitation();
        invitation.setProjectId(projectId);
        invitation.setToken(token);
        invitation.setEmail(email);

        invitationRepository.save(invitation);
        emailService.sendEmailWithToken(email, link);
    }
}
