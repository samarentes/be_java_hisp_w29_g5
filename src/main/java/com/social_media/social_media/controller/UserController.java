package com.social_media.social_media.controller;

import com.social_media.social_media.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final IUserService userService;

    @GetMapping("/")
    public String getMethodName() {
        return new String("Hola Mundo");
    }

}
