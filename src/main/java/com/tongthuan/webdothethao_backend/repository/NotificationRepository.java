package com.tongthuan.webdothethao_backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tongthuan.webdothethao_backend.entity.Notifications;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, String> {

    @Query("SELECT no FROM Notifications no WHERE no.isRead = false")
    Page<Notifications> findUnReadNotifications(Pageable pageable);
}
