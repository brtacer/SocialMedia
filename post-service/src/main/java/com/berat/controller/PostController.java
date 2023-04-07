package com.berat.controller;

import com.berat.dto.request.CreatePostRequest;
import com.berat.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.berat.constant.EndPoints.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(POST)
public class PostController {
    private final PostService postService;

    @PostMapping(CREATE)
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest dto){
        return ResponseEntity.ok(postService.createPost(dto));
    }
}
