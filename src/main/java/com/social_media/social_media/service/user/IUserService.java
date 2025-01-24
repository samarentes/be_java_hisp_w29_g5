package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.*;

public interface IUserService {

    Boolean unfollowSeller(Long userId, Long userIdToUnfollow);

    FollowedResponseDto searchFollowed(Long userId, String order);

    FollowingResponseDto followSeller(Long userId, Long userIdToFollow);

    FollowersResponseDto searchFollowers(Long userId, String order);

    FollowersCountResponseDto searchFollowersCount(Long userId);

    UserWithFavoritesPostResponseDto updateUserFavoritesPost(Long userId, Long postId);

    UserFavoritesResponseDto searchUserFavoritesPost(long userId);

}
