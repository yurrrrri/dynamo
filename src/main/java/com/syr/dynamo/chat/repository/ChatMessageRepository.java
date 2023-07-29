package com.syr.dynamo.chat.repository;

import com.syr.dynamo.chat.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class ChatMessageRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private static final String TABLE_NAME = "chatMessage";
    private final TableSchema<ChatMessage> schema = TableSchema.fromBean(ChatMessage.class);

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
        Key partitionKey = Key.builder().partitionValue(chatRoomId).build();

        QueryConditional queryConditional = QueryConditional.keyEqualTo(partitionKey);
        SdkIterable<Page<ChatMessage>> pages = dynamoDbEnhancedClient.table(TABLE_NAME, schema)
                .query(queryConditional);

        List<ChatMessage> results = new ArrayList<>();
        for (Page<ChatMessage> page : pages) {
            results.addAll(page.items());
        }

        return results;
    }

    public List<ChatMessage> findByChatRoomIdAndCreateDateStartsWith(long chatRoomId, String createDate) {
        String createDateFrom = createDate + "T00:00:00.000000";
        String createDateTo = createDate + "T23:59:59.999999";

        QueryConditional queryConditional = QueryConditional
                .sortBetween(
                        Key.builder().partitionValue(chatRoomId).sortValue(createDateFrom).build(),
                        Key.builder().partitionValue(chatRoomId).sortValue(createDateTo).build()
                );

        SdkIterable<Page<ChatMessage>> pages = dynamoDbEnhancedClient.table(TABLE_NAME, schema)
                .query(queryConditional);

        List<ChatMessage> results = new ArrayList<>();
        for (Page<ChatMessage> page : pages) {
            results.addAll(page.items());
        }

        return results;
    }
}