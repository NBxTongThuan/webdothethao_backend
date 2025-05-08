package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.entity.ChatBox;
import com.tongthuan.webdothethao_backend.repository.ChatBoxRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ChatBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatBoxServiceImpl implements ChatBoxService {

    @Autowired
    private ChatBoxRepository chatBoxRepository;

    @Override
    public Optional<ChatBox> findByUserName(String userName) {
        return chatBoxRepository.findByUserName(userName);
    }
}
