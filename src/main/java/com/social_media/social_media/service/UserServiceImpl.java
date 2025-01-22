package com.social_media.social_media.service;

import com.social_media.social_media.dto.responseDto.UserResponseDto;
import com.social_media.social_media.repository.follow.IFollowRepository;
import com.social_media.social_media.repository.post.IPostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.user.IUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final IFollowRepository followRepository;

    @Override
    public List<UserResponseDto> searchAllUsers() {

        return userRepository.searchAllUsers().stream()
                .map(user -> new UserResponseDto(
                        user.getUserId(),
                        user.getName()
                ))
                .collect(Collectors.toList());
    }


}
