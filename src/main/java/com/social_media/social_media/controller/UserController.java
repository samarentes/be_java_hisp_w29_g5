package com.social_media.social_media.controller;

import com.social_media.social_media.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final IUserService userService;

    @GetMapping("/users/{userId}/followers/count/")
    public ResponseEntity<?> getFollowersCount(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getFollowersCount(userId), HttpStatus.OK);
    }

}
