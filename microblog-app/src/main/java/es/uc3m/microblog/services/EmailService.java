package es.uc3m.microblog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Async
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("techwave.eja@gmail.com");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        try {
            emailSender.send(message);
        } catch (Exception e) {
            System.out.println("Error enviando email: " + e.getMessage());
        }
    }

}
