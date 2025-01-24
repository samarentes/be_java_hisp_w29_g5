package com.social_media.social_media.service.post;

import com.social_media.social_media.dto.responseDto.PromoProductsResponseDto;
import com.social_media.social_media.dto.responseDto.SellersPostsByFollowerResponseDto;
import com.social_media.social_media.dto.request.PostRequestDto;
import com.social_media.social_media.dto.request.PostPromoRequestDto;
import com.social_media.social_media.dto.responseDto.PostPromoResponseDto;
import com.social_media.social_media.dto.responseDto.PostResponseDto;

public interface IPostService {
    PostResponseDto createPost(PostRequestDto postProductRequestDto);

    PostPromoResponseDto createPostPromo(PostPromoRequestDto postPromoRequestDto);

    PromoProductsResponseDto searchSellersWithPromoPosts(long user_id);

    SellersPostsByFollowerResponseDto searchFollowedPostsFromLastTwoWeeks(long userId, String order);
}
