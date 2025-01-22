package com.social_media.social_media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import com.social_media.social_media.service.post.IPostService;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final IPostService postService;
}
