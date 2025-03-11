package com.example.Contact_manager_web.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendEmail(String to, String subject ,String body);

    void  sendEmaiWithHtml();

    void  sendEmailWithAttachment();
}
