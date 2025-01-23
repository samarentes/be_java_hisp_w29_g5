package com.social_media.social_media.repository.follow;

import com.social_media.social_media.entity.Follow;

import java.util.List;

public interface IFollowRepository {
    List<Follow> findFollowers(Long followedId);

}
