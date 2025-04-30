package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.NotificationsResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/notifications")
public class AdminNotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PagedResourcesAssembler<NotificationsResponse> notificationsResponsePagedResourcesAssembler;

    @GetMapping("/unreadNotification")
    public ResponseEntity<PagedModel<EntityModel<NotificationsResponse>>> findUnReadNotification(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<NotificationsResponse> notificationsResponses = notificationService.getUnReadNotifications(pageable).map(NotificationsResponse::new);
        PagedModel<EntityModel<NotificationsResponse>> pagedModel = notificationsResponsePagedResourcesAssembler.toModel(notificationsResponses);
        return ResponseEntity.ok(pagedModel);

    }

    @GetMapping("/getAllNotification")
    public ResponseEntity<PagedModel<EntityModel<NotificationsResponse>>> findAllNotification(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<NotificationsResponse> notificationsResponses = notificationService.findAll(pageable).map(NotificationsResponse::new);
        PagedModel<EntityModel<NotificationsResponse>> pagedModel = notificationsResponsePagedResourcesAssembler.toModel(notificationsResponses);
        return ResponseEntity.ok(pagedModel);

    }

    @PutMapping("/setIsRead")
    public ResponseEntity<Boolean> setIsRead(@RequestParam("notificationId") String notificationId) {
        return ResponseEntity.ok(notificationService.setIsRead(notificationId));
    }

}
