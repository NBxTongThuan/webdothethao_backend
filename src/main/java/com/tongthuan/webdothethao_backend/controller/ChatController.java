package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.dto.ChatMessageDto.ChatMessageResponse;
import com.tongthuan.webdothethao_backend.dto.ChatMessageDto.ChatMessages;
import com.tongthuan.webdothethao_backend.entity.ChatBox;
import com.tongthuan.webdothethao_backend.entity.ChatBoxMessages;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.repository.ChatBoxMessageRepository;
import com.tongthuan.webdothethao_backend.repository.ChatBoxRepository;
import com.tongthuan.webdothethao_backend.service.ChatBoxService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class ChatController {


    @Autowired
    private ChatBoxService chatBoxService;

    @MessageMapping("/chat.send")
    private void sendMessage(@Payload ChatBoxMessages messages, Principal principal) {
        chatBoxService.senMessage(messages,principal);
    }
}
