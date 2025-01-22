package com.social_media.social_media.repository.post;

import com.social_media.social_media.dto.responseDto.PostResponseDto;
import com.social_media.social_media.dto.responseDto.ProductResponseDto;
import com.social_media.social_media.entity.Post;

import java.util.List;

public interface IPostRepository {


    List<Post> searchAllPosts();
}
