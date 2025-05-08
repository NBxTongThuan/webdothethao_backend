package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.ChatBox;
import com.tongthuan.webdothethao_backend.entity.ChatBoxMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatBoxRepository extends JpaRepository<ChatBox,String> {

    @Query("SELECT c FROM ChatBox c WHERE c.user.userName = :userName")
    Optional<ChatBox> findByUserName(@Param("userName") String userName);

}
