package com.org.projectmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmailWithToken(final String email, final String link) {
        final var subject = "Invitation Email for the Project";
        final var body = "<p>To join the project, follow the below link:</p>" + "<a href=\"" + link
                + "\">Join Project</a>";

        try {
            final var message = mailSender.createMimeMessage();
            final var helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true); // true indicates HTML

            mailSender.send(message);
            System.out.println("Email sent successfully to " + email);
        } catch (final MessagingException e) {
            System.err.println("Error sending email to " + email + ": " + e.getMessage());
            // Optionally, you can log this exception or rethrow it as a custom exception
        }
    }
}
