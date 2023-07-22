package com.syr.dynamo.chat.service;

import com.syr.dynamo.chat.entity.ChatMessage;
import com.syr.dynamo.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage write(long chatRoomId, String message) {
        return chatMessageRepository.save(ChatMessage.builder()
                .id(UUID.randomUUID().toString())
                .chatRoomId(chatRoomId)
                .createDate(LocalDateTime.now().toString())
                .message(message)
                .build()
        );
    }

}