package com.syr.dynamo.repository;

import com.syr.dynamo.entity.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class PostRepository {
    public Post save(Post post) {
        post.setId(UUID.randomUUID());
        post.setCreateDate(LocalDateTime.now().toString());

        return post;
    }
}
