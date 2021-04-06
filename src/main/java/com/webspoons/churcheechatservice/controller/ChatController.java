package com.webspoons.churcheechatservice.controller;

import com.webspoons.churcheechatservice.model.ChatMessageGroup;
import com.webspoons.churcheechatservice.model.ChatMessageOne;
import com.webspoons.churcheechatservice.pojo.ChatMessage;
import com.webspoons.churcheechatservice.pojo.ChatNotification;
import com.webspoons.churcheechatservice.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/oneOnOne")
    public void oneOnOne(@Payload ChatMessageOne chatMessageOne) throws Exception{
        chatService.oneOnOneChat(chatMessageOne);
    }

    @MessageMapping("/oneToGroup")
    public void oneToGroup(@Payload ChatMessageGroup chatMessageGroup) throws Exception{
        chatService.oneToGroup(chatMessageGroup);
    }

    @PostMapping("/poolMessage")
    public List<ChatMessage> poolMessage(@RequestBody ChatNotification chatNotification) throws Exception{
        var chat = chatService.poolMessage(chatNotification);
        return chat;
    }

    @PostMapping("/messageStatus")
    public ChatMessage messageStatus(@RequestBody ChatNotification chatNotification) throws Exception{
        var chat = chatService.messageStatus(chatNotification);
        return chat;
    }
}
