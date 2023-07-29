package com.syr.dynamo.chat.service;

import com.syr.dynamo.chat.entity.ChatMessage;
import com.syr.dynamo.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    public List<ChatMessage> findChatMessagesByChatRoomId(long chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId);
    }

    public List<ChatMessage> findChatMessagesByChatRoomIdAndCreateDate(
            long chatRoomId, String createDate
    ) {
        return chatMessageRepository.findByChatRoomIdAndCreateDateStartsWith(chatRoomId, createDate);
    }
}