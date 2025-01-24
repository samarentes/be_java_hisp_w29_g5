package com.social_media.social_media.repository.post;

import com.social_media.social_media.entity.Post;
import com.social_media.social_media.enums.PostType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.util.Optional;

public interface IPostRepository {
    Optional<Post> findById(Long postId);

    List<Post> findByUserId(Long userId);

    List<Post> findPostBySellerId(Long userIdToFollow);

    List<Post> findByIdSince(Long followedId, LocalDate lastTwoWeeks);

    Post add(Post post);

    List<Post> findWithFilters(PostType postType, Long userId);

}
