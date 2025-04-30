package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.entity.Notifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {

    public Page<Notifications> getUnReadNotifications(Pageable pageable);

    public Page<Notifications> findAll(Pageable pageable);

    public boolean setIsRead(String notificationId);

}
