package com.syr.dynamo.post.controller;

import com.syr.dynamo.post.entity.Post;
import com.syr.dynamo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/create")
    @ResponseBody
    public Post create() {
        return postService.write("제목");
    }

    @GetMapping("")
    @ResponseBody
    public List<Post> posts() {
        return postService.findAll();
    }

    @GetMapping("{id}")
    @ResponseBody
    public Post post(@PathVariable String id) {
        return postService.findByIdAndCreateDate(id).orElse(null);
    }

}
