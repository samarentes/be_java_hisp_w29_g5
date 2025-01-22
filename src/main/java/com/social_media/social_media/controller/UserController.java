package com.social_media.social_media.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import com.social_media.social_media.dto.responseDto.FollowedResponseDto;
import com.social_media.social_media.service.user.IUserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/")
    public String getMethodName() {
        return new String("Hola Mundo");
    }

    @GetMapping("/{userId}/followed/list")
    public FollowedResponseDto getFollowed(@PathVariable Long userId) {
        return this.userService.searchFollowed(userId);
    }
}
