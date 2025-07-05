package com.tongthuan.webdothethao_backend.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tongthuan.webdothethao_backend.dto.ChatMessageDto.ChatMessageResponse;
import com.tongthuan.webdothethao_backend.service.ChatBoxService;

@RestController
@RequestMapping("/api/chat")
public class MessagesController {

    @Autowired
    private ChatBoxService chatBoxService;

    @Autowired
    private PagedResourcesAssembler<ChatMessageResponse> pagedResourcesAssembler;

    @GetMapping("/history")
    public ResponseEntity<PagedModel<EntityModel<ChatMessageResponse>>> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size,
            Principal principal) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ChatMessageResponse> chatMessageResponses =
                chatBoxService.getHistoryMessage(principal, pageable).map(ChatMessageResponse::new);

        PagedModel<EntityModel<ChatMessageResponse>> pagedModel = pagedResourcesAssembler.toModel(chatMessageResponses);
        return ResponseEntity.ok(pagedModel);
    }
}
