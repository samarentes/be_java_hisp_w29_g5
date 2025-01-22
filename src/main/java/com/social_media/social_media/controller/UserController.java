package com.social_media.social_media.controller;

import com.social_media.social_media.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/")
    public String getMethodName() {
        return new String("Hola Mundo");
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<Boolean> postUnfollowSeller(@PathVariable Long userId, @PathVariable Long userIdToUnfollow){
        return new ResponseEntity<>(userService.unfollowSeller(userId, userIdToUnfollow), HttpStatus.NO_CONTENT);
    }
}
