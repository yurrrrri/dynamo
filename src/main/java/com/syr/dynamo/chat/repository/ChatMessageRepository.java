package com.syr.dynamo.chat.repository;

import com.syr.dynamo.chat.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class ChatMessageRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private static final String TABLE_NAME = "chatMessage";

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setId(UUID.randomUUID().toString());
        chatMessage.setCreateDate(LocalDateTime.now().toString());

        DynamoDbTable<ChatMessage> table = dynamoDbEnhancedClient
                .table(TABLE_NAME, BeanTableSchema.create(ChatMessage.class));
        PutItemEnhancedRequest<ChatMessage> putItemEnhancedRequest =
                PutItemEnhancedRequest.builder(ChatMessage.class)
                        .item(chatMessage)
                        .build();

        table.putItem(putItemEnhancedRequest);

        return chatMessage;
    }

    public List<ChatMessage> findByChatRoomId(long chatRoomId) {
        DynamoDbTable<ChatMessage> table = dynamoDbEnhancedClient
                .table(TABLE_NAME, BeanTableSchema.create(ChatMessage.class));

        return table
                .scan()
                .items()
                .stream()
                .filter(chatMessage -> chatMessage.getChatRoomId() == chatRoomId)
                .toList();
    }

}