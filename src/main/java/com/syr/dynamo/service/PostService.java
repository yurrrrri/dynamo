package com.syr.dynamo.service;

import com.syr.dynamo.entity.Post;
import com.syr.dynamo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post write(String subject) {
        return postRepository.save(Post.builder()
                .subject(subject)
                .build());
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }
}
