package com.social_media.social_media.service;

import com.social_media.social_media.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService{
    private final IUserRepository userRepository;
}
