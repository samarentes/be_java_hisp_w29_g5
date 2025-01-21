package com.social_media.social_media.controller;

import com.social_media.social_media.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final IUserService userService;


}
