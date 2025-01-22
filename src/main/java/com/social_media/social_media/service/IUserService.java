package com.social_media.social_media.service;

import com.social_media.social_media.dto.responseDto.FollowingResponseDto;

public interface IUserService {

    FollowingResponseDto followSeller(Long userId, Long userIdToFollow);
}
