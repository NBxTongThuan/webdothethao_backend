package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.ChatBoxMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatBoxMessageRepository extends JpaRepository<ChatBoxMessages , String> {

}
