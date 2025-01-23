package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.FollowingResponseDto;

import com.social_media.social_media.dto.responseDto.FollowedResponseDto;
import com.social_media.social_media.dto.responseDto.FollowersCountResponseDto;
import com.social_media.social_media.dto.responseDto.FollowersResponseDto;

public interface IUserService {

    Boolean unfollowSeller(Long userId, Long userIdToUnfollow);

    public FollowedResponseDto searchFollowed(Long userId, String order);

    public FollowingResponseDto followSeller(Long userId, Long userIdToFollow);

    public FollowedResponseDto searchFollowed(Long userId);

    public FollowersResponseDto searchFollowers(Long userId, String order);

    public FollowersCountResponseDto searchFollowersCount(Long userId);

}
