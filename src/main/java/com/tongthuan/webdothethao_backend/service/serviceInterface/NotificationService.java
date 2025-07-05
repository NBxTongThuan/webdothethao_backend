package com.tongthuan.webdothethao_backend.service.serviceInterface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tongthuan.webdothethao_backend.entity.Notifications;

public interface NotificationService {

    public Page<Notifications> getUnReadNotifications(Pageable pageable);

    public Page<Notifications> findAll(Pageable pageable);

    public boolean setIsRead(String notificationId);
}
