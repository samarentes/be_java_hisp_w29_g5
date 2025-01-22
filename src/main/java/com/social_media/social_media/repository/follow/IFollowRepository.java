package com.social_media.social_media.repository.follow;

import com.social_media.social_media.dto.responseDto.FollowingResponseDto;
import com.social_media.social_media.entity.Follow;

public interface IFollowRepository {

    Follow addFollow(Long userId, Long userIdToFollow);

    boolean existsByFollowerAndFollowed(Long userId, Long userIdToFollow);
}
