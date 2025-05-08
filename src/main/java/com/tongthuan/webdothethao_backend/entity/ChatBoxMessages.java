package com.tongthuan.webdothethao_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_box_messages")
public class ChatBoxMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id", length = 50)
    private String messageId;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "chat_box_id",nullable = false)
    private ChatBox chatBox;



}
