package org.ai.hospitalmanagementapplicationbackend.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImplementation implements EmailService{
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        // Create MimeMessage for multipart email
        MimeMessage message = mailSender.createMimeMessage();

        // Create MimeMessageHelper for easy manipulation of the message
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // 'true' means multipart


        helper.setTo(to);  // Recipient's email address

        // Set the subject of the email
        helper.setSubject(subject);

        // Set the email body, both HTML and plain text versions
        helper.setText(body, true); // true means HTML content
        // If you want to add plain text as well, uncomment the line below:
        // helper.setText("Your plain text version of the body.", false);

        // Send the email
        mailSender.send(message);
        System.out.println("Email sent successfully to " + to);

    }
}
