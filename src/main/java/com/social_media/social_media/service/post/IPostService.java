package com.social_media.social_media.service.post;
import com.social_media.social_media.dto.responseDto.SellersPostsByFollowerResponseDto;
import com.social_media.social_media.dto.request.PostProductRequestDto;
import com.social_media.social_media.dto.responseDto.PostResponseDto;

public interface IPostService {
    PostResponseDto createPost(PostProductRequestDto postProductRequestDto);
    SellersPostsByFollowerResponseDto searchFollowedPostsFromLastTwoWeeks(long userId, String order);
}
