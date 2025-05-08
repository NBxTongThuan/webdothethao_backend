package com.tongthuan.webdothethao_backend.service;

import com.tongthuan.webdothethao_backend.dto.ChatMessageDto.ChatMessageResponse;
import com.tongthuan.webdothethao_backend.entity.ChatBox;
import com.tongthuan.webdothethao_backend.entity.ChatBoxMessages;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.repository.ChatBoxMessageRepository;
import com.tongthuan.webdothethao_backend.repository.ChatBoxRepository;
import com.tongthuan.webdothethao_backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ChatBoxService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ChatBoxRepository chatBoxRepository;

    @Autowired
    ChatBoxMessageRepository chatBoxMessageRepository;

    @Transactional
    public void senMessage(ChatBoxMessages messages, Principal principal)
    {
        String userName = principal.getName();

        Users sender = usersRepository.findByUserName(userName).orElse(null);
        if(sender == null)
            return;

        System.out.println("ChatController"+userName);

        Optional<ChatBox> optionalBox = chatBoxRepository.findByUserName(userName);
        ChatBox chatBox;

        if (optionalBox.isPresent()) {
            // Dùng lại entity đã gắn kết
            chatBox = optionalBox.get();
            // Đảm bảo nó vẫn đang được quản lý
            chatBox = chatBoxRepository.findById(chatBox.getChatBoxID()).orElseThrow();
        } else {
            // Tạo mới nếu chưa có
            chatBox = new ChatBox();
            chatBox.setUser(sender);
            chatBox.setCreatedDate(LocalDateTime.now());
            chatBox = chatBoxRepository.saveAndFlush(chatBox);
        }


        ChatBoxMessages chatBoxMessages = new ChatBoxMessages();
        chatBoxMessages.setContent(messages.getContent());
        chatBoxMessages.setCreatedDate(LocalDateTime.now());
        chatBoxMessages.setChatBox(chatBox);
        chatBoxMessages.setUser(sender);

        chatBoxMessageRepository.save(chatBoxMessages);

        simpMessagingTemplate.convertAndSendToUser(
                "thuan",
                "/queue/messages",
                new ChatMessageResponse(sender.getUserName(), chatBoxMessages.getContent(), chatBoxMessages.getCreatedDate())
        );


        simpMessagingTemplate.convertAndSendToUser(
                sender.getUserName(),
                "/queue/messages",
                new ChatMessageResponse(sender.getUserName(), chatBoxMessages.getContent(), chatBoxMessages.getCreatedDate())
        );

        System.out.println(chatBox.getChatBoxID());
        System.out.println(messages.getContent());
    }

}
