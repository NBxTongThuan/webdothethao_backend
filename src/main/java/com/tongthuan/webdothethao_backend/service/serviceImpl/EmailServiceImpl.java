package com.tongthuan.webdothethao_backend.service.serviceImpl;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.tongthuan.webdothethao_backend.service.serviceInterface.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendMessage(String from, String to, String subject, String text) {

        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //        Thực hiện gửi mail
        emailSender.send(mimeMessage);
    }
}
