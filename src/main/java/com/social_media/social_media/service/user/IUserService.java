package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.FollowingResponseDto;

import com.social_media.social_media.dto.responseDto.FollowedResponseDto;
import com.social_media.social_media.dto.responseDto.FollowersCountResponseDto;
import com.social_media.social_media.dto.responseDto.FollowersResponseDto;

public interface IUserService {

    Boolean unfollowSeller(Long userId, Long userIdToUnfollow);

    FollowedResponseDto searchFollowed(Long userId, String order);

    FollowingResponseDto followSeller(Long userId, Long userIdToFollow);

    FollowersResponseDto searchFollowers(Long userId, String order);

    FollowersCountResponseDto searchFollowersCount(Long userId);

}
