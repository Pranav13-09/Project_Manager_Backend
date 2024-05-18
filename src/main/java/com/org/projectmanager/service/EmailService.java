package com.org.projectmanager.service;

public interface EmailService {

    void sendEmailWithToken(String email, String link);
}
