package com.social_media.social_media.service.post;

import com.social_media.social_media.dto.request.PostPromoEndDateRequestDto;
import com.social_media.social_media.dto.responseDto.*;
import com.social_media.social_media.dto.request.PostRequestDto;
import com.social_media.social_media.dto.request.PostPromoRequestDto;

public interface IPostService {
    PostResponseDto createPost(PostRequestDto postProductRequestDto);

    PostPromoResponseDto createPostPromo(PostPromoRequestDto postPromoRequestDto);

    PromoProductsResponseDto searchSellersWithPromoPosts(Long user_id);

    PostPromoEndDateResponseDto createPostPromoEndDate(PostPromoEndDateRequestDto postPromoEndDateRequestDto);

    SellersPostsByFollowerResponseDto searchFollowedPostsFromLastTwoWeeks(Long userId, String order);
}
