package com.syr.dynamo.post.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private String id;
    private String createDate;
    private String subject;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    @DynamoDbSortKey
    public String getCreateDate() {
        return createDate;
    }

}
