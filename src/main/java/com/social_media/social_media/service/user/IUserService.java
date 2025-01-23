package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.FollowersCountResponseDto;
import com.social_media.social_media.dto.responseDto.FollowersResponseDto;

public interface IUserService {
    public FollowersResponseDto searchFollowers(Long userId);

    public FollowersCountResponseDto searchFollowersCount(Long userId);

}
