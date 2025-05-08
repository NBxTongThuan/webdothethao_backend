package com.tongthuan.webdothethao_backend.service.serviceInterface;


import com.tongthuan.webdothethao_backend.entity.ChatBox;

import java.util.Optional;

public interface ChatBoxService {

    Optional<ChatBox> findByUserName(String userName);

}
