package com.social_media.social_media.controller;

import com.social_media.social_media.dto.responseDto.FollowersResponseDto;
import com.social_media.social_media.service.user.IUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import com.social_media.social_media.dto.responseDto.FollowedResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/")
    public String getMethodName() {
        return new String("Hola Mundo");
    }

    @GetMapping("/{userId}/follower/list")
    public ResponseEntity<FollowersResponseDto> getFollowers(@PathVariable Long userId,
            @RequestParam(required = false) String order) {
        return new ResponseEntity<>(this.userService.searchFollowers(userId, order), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<FollowedResponseDto> getFollowed(@PathVariable Long userId,
            @RequestParam(required = false) String order) {
        return new ResponseEntity<>(this.userService.searchFollowed(userId, order), HttpStatus.OK);
    }
}
