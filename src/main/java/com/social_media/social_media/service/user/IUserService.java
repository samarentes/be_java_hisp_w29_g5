package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.FollowedResponseDto;

public interface IUserService {
    public FollowedResponseDto searchFollowed(Long userId);
}
