package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.entity.Notifications;
import com.tongthuan.webdothethao_backend.repository.NotificationRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NotificationsServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Page<Notifications> getUnReadNotifications(Pageable pageable) {
        return notificationRepository.findUnReadNotifications(pageable);
    }

    @Override
    public Page<Notifications> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    @Override
    public boolean setIsRead(String notificationId) {

        Notifications notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification == null)
            return false;
        notification.setRead(true);
        notificationRepository.saveAndFlush(notification);
        return true;
    }


}
