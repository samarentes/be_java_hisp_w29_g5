package com.social_media.social_media.repository.post;

import com.social_media.social_media.entity.Post;
import com.social_media.social_media.enums.PostType;

import java.time.LocalDate;
import java.util.List;

public interface IPostRepository {
    List<Post> findById(Long userId);

    List<Post> findPostBySellerId(Long userIdToFollow);

    List<Post> findByIdSince(Long followedId, LocalDate lastTwoWeeks);

    Post add(Post post);

    List<Post> findAll(PostType postType);
}
