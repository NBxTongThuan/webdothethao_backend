package com.tongthuan.webdothethao_backend.dto.ChatMessageDto;

import java.time.LocalDateTime;

import com.tongthuan.webdothethao_backend.entity.ChatBoxMessages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponse {

    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime createdAt;

    public ChatMessageResponse(ChatBoxMessages messages) {
        this.sender = messages.getSender().getUserName();
        this.receiver = messages.getReceiver().getUserName();
        this.content = messages.getContent();
        this.createdAt = messages.getCreatedDate();
    }
}
