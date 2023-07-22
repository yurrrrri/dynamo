package com.syr.dynamo.repository;

import com.syr.dynamo.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private static final String TABLE_NAME = "post";

    public Post save(Post post) {
        post.setId(UUID.randomUUID().toString());
        post.setCreateDate(LocalDateTime.now().toString());

        DynamoDbTable<Post> table = dynamoDbEnhancedClient.table(TABLE_NAME, BeanTableSchema.create(Post.class));
        PutItemEnhancedRequest<Post> putItemEnhancedRequest = PutItemEnhancedRequest.builder(Post.class)
                .item(post)
                .build();

        table.putItem(putItemEnhancedRequest);

        return post;
    }

    public List<Post> findAll() {
        DynamoDbTable<Post> table = dynamoDbEnhancedClient.table(TABLE_NAME, BeanTableSchema.create(Post.class));
        ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder().build();
        PageIterable<Post> scan = table.scan(scanEnhancedRequest);

        return scan.items().stream().toList();
    }
}
