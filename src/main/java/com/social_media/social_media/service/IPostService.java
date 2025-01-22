package com.social_media.social_media.service;

import com.social_media.social_media.dto.responseDto.PostResponseDto;
import com.social_media.social_media.dto.responseDto.SellersPostsByFollowerResponseDto;

import java.util.List;

public interface IPostService {
    SellersPostsByFollowerResponseDto searchFollowedPostsFromLastTwoWeeks(long userId);
}
