package com.social_media.social_media.controller;

import com.social_media.social_media.dto.responseDto.SellersPostsByFollowerResponseDto;
import com.social_media.social_media.dto.request.PostProductRequestDto;
import com.social_media.social_media.service.post.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class PostController {
    private final IPostService postService;

    @GetMapping("/products/followed/{userId}/list")
    public ResponseEntity<SellersPostsByFollowerResponseDto> getFollowedPostsFromLastTwoWeeks(@PathVariable long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public ResponseEntity<?> createNewPost(@RequestBody PostProductRequestDto postProductRequestDto){
    }

}