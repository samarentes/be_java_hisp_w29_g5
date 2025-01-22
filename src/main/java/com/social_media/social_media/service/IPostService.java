package com.social_media.social_media.service;

import com.social_media.social_media.dto.responseDto.PostResponseDto;
import com.social_media.social_media.dto.responseDto.ProductResponseDto;
import java.util.List;

public interface IPostService {
    List <PostResponseDto> searchAllPosts();
}
