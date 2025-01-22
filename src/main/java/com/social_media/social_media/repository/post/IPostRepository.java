package com.social_media.social_media.repository.post;

import com.social_media.social_media.entity.Post;

import java.util.List;

public interface IPostRepository {
    List<Post> findById(Long userId);
}
