package com.social_media.social_media.repository.follow;

import com.social_media.social_media.entity.Follow;
import java.util.List;


public interface IFollowRepository {

    Follow addFollow(Long followerId, Long followedId);

    boolean existsByFollowerAndFollowed(Long userId, Long userIdToFollow);

    public List<Follow> findFollowers(Long followedId);

    List<Follow> findFollowed(Long userId);

    boolean unfollowFollow(Long userId, Long userIdToUnfollow);
}
