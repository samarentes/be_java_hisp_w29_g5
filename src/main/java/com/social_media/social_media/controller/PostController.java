package com.social_media.social_media.controller;

import com.social_media.social_media.dto.request.PostRequestDto;
import com.social_media.social_media.dto.request.PostPromoRequestDto;
import com.social_media.social_media.service.post.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class PostController {
    private final IPostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> postNew(@RequestBody PostRequestDto postProductRequestDto) {
        return new ResponseEntity<>(postService.createPost(postProductRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/promo-post")
    public ResponseEntity<?> postNewPromo(@RequestBody PostPromoRequestDto postPromoRequestDto) {
        return new ResponseEntity<>(postService.createPostPromo(postPromoRequestDto), HttpStatus.CREATED);
    }

}