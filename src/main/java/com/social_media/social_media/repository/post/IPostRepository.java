package com.social_media.social_media.repository.post;

import com.social_media.social_media.entity.Post;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface IPostRepository {
    List<Post> findAll();
    List<Post> findByIdSince(Long followedId, LocalDate lastTwoWeeks);
}
