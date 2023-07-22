package com.syr.dynamo.chat.repository;

import com.syr.dynamo.chat.entity.ChatMessage;
import org.springframework.stereotype.Repository;

@Repository
public class ChatMessageRepository {

    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessage;
    }

}