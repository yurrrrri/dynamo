package com.syr.dynamo.post.service;

import com.syr.dynamo.post.entity.Post;
import com.syr.dynamo.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Post> findByIdAndCreateDate(String id) {
        return postRepository.findById(id);
    }

}
