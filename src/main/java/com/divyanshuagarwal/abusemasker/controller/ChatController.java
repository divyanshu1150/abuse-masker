package com.divyanshuagarwal.abusemasker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.divyanshuagarwal.abusemasker.model.ChatMessage;
import com.divyanshuagarwal.abusemasker.service.AbuseMaskService;

@Controller
public class ChatController {
    
    @Autowired
    private AbuseMaskService service;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        String original = message.getContent();
        String masked = service.mask(original);

        ChatMessage response = new ChatMessage();
        response.setUsername(message.getUsername());
        response.setContent(masked);
        response.setOriginalContent(original);
        return response;
    }
}
