package com.BRS.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender mailSender;

    public void sendEmail(String from, String to, String subject, String body) {
        System.out.println("***************SENDING EMAIL******************");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shakirmoha5496@gmail.com");// from
        message.setTo(new String[] {
                "mosaliya46@gmail.com", "lomimoha99@gmail.com"
        });// to
        message.setSubject(subject);
        message.setText("Dear, We have detected a login to the system... ");
        mailSender.send(message);

    }

    public String sendMail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Test E-Mail!");
            // if (user != null)
            // message.setText("Dear, We have detected a login to the system... with
            // username: " + user);
            message.setText("Dear, We have detected a login to the system... ");
            message.setFrom("shakirmoha5496@gmail.com");
            message.setTo("lomimoha99@gmail.com");
            System.out.println("Sending...");
            mailSender.send(message);

            return "Success";
        } catch (Exception e) {
            System.out.println("We can not send this email: " + e.getMessage());
            return "We can not send this email: " + e.getMessage();
        }
    }

}
