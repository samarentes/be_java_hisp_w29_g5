package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.*;

import java.util.List;

public interface IUserService {

    Boolean unfollowSeller(Long userId, Long userIdToUnfollow);

    FollowedResponseDto searchFollowed(Long userId, String order);

    FollowingResponseDto followSeller(Long userId, Long userIdToFollow);

    FollowersResponseDto searchFollowers(Long userId, String order);

    FollowersCountResponseDto searchFollowersCount(Long userId);

    List<FollowSuggestionResponseDto> searchFollowSuggestions(Long userId, Integer limit);
}
