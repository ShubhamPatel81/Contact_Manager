package com.example.Contact_manager_web.service.Implementation;

import com.example.Contact_manager_web.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.properties.domain_name}" )
    private String domain_name;

    @Autowired
    private JavaMailSender eMailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage mailMessage =new SimpleMailMessage();

        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailMessage.setFrom(domain_name);

        eMailSender.send(mailMessage);



    }

    @Override
    public void sendEmaiWithHtml() {

    }

    @Override
    public void sendEmailWithAttachment() {

    }
}
