package com.social_media.social_media.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.user.IUserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
}
