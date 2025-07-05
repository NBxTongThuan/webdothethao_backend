package com.tongthuan.webdothethao_backend.dto.response.AdminResponse;

import java.time.LocalDateTime;

import com.tongthuan.webdothethao_backend.entity.Notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationsResponse {
    private String notificationId;
    private String title;
    private String content;
    private boolean isRead;
    private LocalDateTime createdDate;
    private String orderId;
    private String userId;

    public NotificationsResponse(Notifications notification) {
        this.notificationId = notification.getNotificationId();
        this.title = notification.getTitle();
        this.content = notification.getContent();
        this.isRead = notification.isRead();
        this.createdDate = notification.getCreatedDate();
        this.orderId = notification.getOrder().getOrderId();
        this.userId = notification.getUser().getUserId();
    }
}
