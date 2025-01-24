package com.social_media.social_media.controller;

import com.social_media.social_media.dto.responseDto.FollowersCountResponseDto;
import com.social_media.social_media.dto.responseDto.FollowingResponseDto;
import com.social_media.social_media.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.social_media.social_media.dto.responseDto.FollowersResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.social_media.social_media.dto.responseDto.FollowedResponseDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/{userId}/followers/count/")
    public ResponseEntity<FollowersCountResponseDto> getFollowersCount(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.searchFollowersCount(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<Boolean> postUnfollowSeller(@PathVariable Long userId, @PathVariable Long userIdToUnfollow) {
        return new ResponseEntity<>(userService.unfollowSeller(userId, userIdToUnfollow), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<FollowingResponseDto> postFollowSeller(@PathVariable Long userId,
                                                                 @PathVariable Long userIdToFollow) {
        return new ResponseEntity<>(userService.followSeller(userId, userIdToFollow), HttpStatus.OK);

    }

    @GetMapping("/{userId}/follower/list")
    public ResponseEntity<FollowersResponseDto> getFollowers(@PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "name_asc") String order) {
        return new ResponseEntity<>(this.userService.searchFollowers(userId, order), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<FollowedResponseDto> getFollowed(@PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "name_asc") String order) {
        return new ResponseEntity<>(this.userService.searchFollowed(userId, order), HttpStatus.OK);
    }
}
