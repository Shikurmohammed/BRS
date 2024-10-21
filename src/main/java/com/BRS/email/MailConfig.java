package com.BRS.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    // @Bean
    @Qualifier("mailSender1")
    public JavaMailSender mailSender1() {
        return createMailSender("smtp.example.com",
                25, " shakirmoha5496@gmail.com",
                "bO63#F!k@");
    }

    // @Bean
    // @Qualifier("mailSender2")
    public JavaMailSender mailSender2() {

        return createMailSender(null, 0, null, null);
    }

    public JavaMailSenderImpl createMailSender(String host, int port, String username, String password) {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(host);
        mailSenderImpl.setPort(port);
        mailSenderImpl.setUsername(username);
        mailSenderImpl.setPassword(password);

        Properties props = mailSenderImpl.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSenderImpl;
    }

}
