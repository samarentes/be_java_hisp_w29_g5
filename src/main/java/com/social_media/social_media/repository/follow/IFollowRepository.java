package com.social_media.social_media.repository.follow;

import com.social_media.social_media.entity.Follow;
import java.util.List;

import java.util.Optional;

public interface IFollowRepository {

    public List<Follow> findFollowed(Long userId);

    Follow addFollow(Long followerId, Long followedId);

    public List<Follow> findFollowers(Long followedId);

    Optional<Follow> existsByFollowerAndFollowed(Long userId, Long userIdToFollow);

    void deleteFollow(Optional<Follow> follow);
}
