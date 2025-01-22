package com.social_media.social_media.repository.follow;

import com.social_media.social_media.entity.Follow;

public interface IFollowRepository {

    boolean existsByFollowerAndFollowed(Long userId, Long userIdToFollow);

    boolean unfollowFollow(Long userId, Long userIdToUnfollow);
}
