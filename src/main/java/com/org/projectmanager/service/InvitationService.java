package com.org.projectmanager.service;

import com.org.projectmanager.model.Invitation;

public interface InvitationService {

    Invitation acceptInvitation(String token, Long userId);

    void deleteToken(String token);

    String getTokenByMail(String email);

    void sendInvitation(String email, Long projectId);

}
