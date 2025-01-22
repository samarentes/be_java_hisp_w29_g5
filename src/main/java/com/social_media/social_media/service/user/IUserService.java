package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.FollowersCountResponseDto;

public interface IUserService {
    FollowersCountResponseDto getFollowersCount(Long userId);

}
