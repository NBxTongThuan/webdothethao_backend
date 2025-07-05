package com.tongthuan.webdothethao_backend.service;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tongthuan.webdothethao_backend.dto.ChatMessageDto.ChatMessageResponse;
import com.tongthuan.webdothethao_backend.entity.ChatBoxMessages;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.repository.ChatBoxMessageRepository;
import com.tongthuan.webdothethao_backend.repository.UsersRepository;

@Service
public class ChatBoxService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ChatBoxMessageRepository chatBoxMessageRepository;

    @Transactional
    public void sendMessage(ChatBoxMessages messages, Principal principal) {
        String userName = principal.getName();

        Users sender = usersRepository.findByUserName(userName).orElse(null);
        if (sender == null) return;

        Users receiver = usersRepository.findByUserName("thuan").orElse(null);
        if (receiver == null) return;

        System.out.println("Sender: " + userName);
        System.out.println("Receiver: " + receiver.getUserName());

        ChatBoxMessages chatBoxMessages = new ChatBoxMessages();
        chatBoxMessages.setContent(messages.getContent());
        chatBoxMessages.setCreatedDate(LocalDateTime.now());
        chatBoxMessages.setReceiver(receiver);
        chatBoxMessages.setSender(sender);

        chatBoxMessageRepository.save(chatBoxMessages);

        simpMessagingTemplate.convertAndSendToUser(
                "thuan",
                "/queue/messages",
                new ChatMessageResponse(
                        sender.getUserName(),
                        receiver.getUserName(),
                        chatBoxMessages.getContent(),
                        chatBoxMessages.getCreatedDate()));

        simpMessagingTemplate.convertAndSendToUser(
                sender.getUserName(),
                "/queue/messages",
                new ChatMessageResponse(
                        sender.getUserName(),
                        receiver.getUserName(),
                        chatBoxMessages.getContent(),
                        chatBoxMessages.getCreatedDate()));

        System.out.println(messages.getContent());
    }

    public Page<ChatBoxMessages> getHistoryMessage(Principal principal, Pageable pageable) {
        String userName = principal.getName();
        Users us1 = usersRepository.findByUserName(userName).orElse(null);
        if (us1 == null) return null;

        return chatBoxMessageRepository.findBySenderAndReceiver(userName, "thuan", pageable);
    }
}
