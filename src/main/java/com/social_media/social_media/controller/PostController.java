package com.social_media.social_media.controller;

import com.social_media.social_media.dto.responseDto.SellersPostsByFollowerResponseDto;
import com.social_media.social_media.dto.request.PostProductRequestDto;
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

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<SellersPostsByFollowerResponseDto> getFollowedRecentPosts(
            @PathVariable long userId,
            @RequestParam(defaultValue = "date_desc") String order) {
        SellersPostsByFollowerResponseDto responseDto = postService.searchFollowedPostsFromLastTwoWeeks(userId, order);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/post")
    public ResponseEntity<?> createNewPost(@RequestBody PostProductRequestDto postProductRequestDto){
        return new ResponseEntity<>(postService.createPost(postProductRequestDto), HttpStatus.CREATED);
    }

}