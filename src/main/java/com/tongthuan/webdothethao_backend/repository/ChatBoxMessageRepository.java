package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.ChatBoxMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatBoxMessageRepository extends JpaRepository<ChatBoxMessages, String> {

    @Query("""
                SELECT m FROM ChatBoxMessages m
                WHERE
                    (m.sender.userName = :us1 AND m.receiver.userName = :us2)
                    OR
                    (m.sender.userName = :us2 AND m.receiver.userName = :us1)
                ORDER BY m.createdDate ASC
            """)
    Page<ChatBoxMessages> findBySenderAndReceiver(@Param("us1") String us1, @Param("us2") String us2, Pageable pageable);

}
