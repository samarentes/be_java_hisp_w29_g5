package com.social_media.social_media.repository.follow;

import java.util.List;

import com.social_media.social_media.entity.Follow;

public interface IFollowRepository {
    public List<Follow> findFollowed(Long userId);
}
