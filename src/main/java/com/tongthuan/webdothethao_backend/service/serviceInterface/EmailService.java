package com.tongthuan.webdothethao_backend.service.serviceInterface;

public interface EmailService {

    public void sendMessage(String from, String to, String subject, String text);

}
