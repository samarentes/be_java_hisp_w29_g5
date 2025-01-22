package com.social_media.social_media.controller;

import com.social_media.social_media.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final IPostService postService;

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() {

        return new ResponseEntity<>(postService.searchAllPosts(), HttpStatus.OK);
    }
}
