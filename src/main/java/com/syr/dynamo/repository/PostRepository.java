package com.syr.dynamo.repository;

import com.syr.dynamo.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private static final String TABLE_NAME = "post";

    public Post save(Post post) {
        post.setId(UUID.randomUUID());
        post.setCreateDate(LocalDateTime.now().toString());

        DynamoDbTable<Post> table = dynamoDbEnhancedClient.table(TABLE_NAME, BeanTableSchema.create(Post.class));
        PutItemEnhancedRequest<Post> putItemEnhancedRequest = PutItemEnhancedRequest.builder(Post.class)
                .item(post)
                .build();

        table.putItem(putItemEnhancedRequest);

        return post;
    }
}
