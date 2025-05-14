package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.entity.ChatBoxMessages;
import com.tongthuan.webdothethao_backend.service.ChatBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {


    @Autowired
    private ChatBoxService chatBoxService;


    @MessageMapping("/chat.send")
    private void sendMessage(@Payload ChatBoxMessages messages, Principal principal) {
        chatBoxService.sendMessage(messages, principal);
    }

}
