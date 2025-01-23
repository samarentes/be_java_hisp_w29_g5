package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.FollowersResponseDto;
import com.social_media.social_media.dto.responseDto.FollowedResponseDto;

import com.social_media.social_media.dto.responseDto.FollowersCountResponseDto;

public interface IUserService {
    public FollowedResponseDto searchFollowed(Long userId, String order);

    public FollowersResponseDto searchFollowers(Long userId, String order);

    public FollowersCountResponseDto searchFollowersCount(Long userId);

}
