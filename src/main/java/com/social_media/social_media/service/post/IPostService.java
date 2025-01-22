package com.social_media.social_media.service.post;

import com.social_media.social_media.dto.responseDto.SellersPostsByFollowerResponseDto;

public interface IPostService {
    SellersPostsByFollowerResponseDto searchFollowedPostsFromLastTwoWeeks(long userId);
}
