package com.syr.dynamo.controller;

import com.syr.dynamo.entity.Post;
import com.syr.dynamo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/create")
    @ResponseBody
    public Post create() {
        return postService.write("제목");
    }
}
