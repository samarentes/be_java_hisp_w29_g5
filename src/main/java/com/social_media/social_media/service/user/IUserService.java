package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.FollowersResponseDto;
import com.social_media.social_media.dto.responseDto.FollowedResponseDto;

public interface IUserService {

    public FollowersResponseDto searchFollowers(Long userId, String order);

    public FollowedResponseDto searchFollowed(Long userId, String order);

}
