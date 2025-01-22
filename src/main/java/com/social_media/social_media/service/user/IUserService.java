package com.social_media.social_media.service.user;

public interface IUserService {
    Boolean unfollowSeller(Long userId, Long userIdToUnfollow);
}
