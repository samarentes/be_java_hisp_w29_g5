package com.social_media.social_media.service.post;

import com.social_media.social_media.dto.responseDto.PostResponseWithIdDto;
import com.social_media.social_media.dto.responseDto.ProductResponseDto;
import com.social_media.social_media.dto.responseDto.SellersPostsByFollowerResponseDto;
import com.social_media.social_media.entity.Follow;
import com.social_media.social_media.entity.Post;
import com.social_media.social_media.repository.follow.IFollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.post.IPostRepository;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final IFollowRepository followRepository;

    @Override
    public SellersPostsByFollowerResponseDto searchFollowedPostsFromLastTwoWeeks(long userId) {
        LocalDate lastTwoWeeks = LocalDate.now().minusWeeks(2);

        List<Long> followedIds = followRepository.findFollowed(userId).stream().map(Follow::getFollowedId).toList();
        List<Post> posts = followedIds.stream().flatMap(followedId ->
                postRepository.findByIdSince(followedId, lastTwoWeeks).stream()).toList();

        List<PostResponseWithIdDto> postsDto = posts.stream()
                .map(post -> PostResponseWithIdDto.builder()
                        .user_id(post.getUserId())
                        .post_id(post.getPostId())
                        .date(post.getDate())
                        .product(ProductResponseDto.builder()
                                .product_id(post.getProduct().getProductId())
                                .product_name(post.getProduct().getProductName())
                                .type(post.getProduct().getType())
                                .brand(post.getProduct().getBrand())
                                .color(post.getProduct().getColor())
                                .notes(post.getProduct().getNotes())
                                .build())
                        .category(post.getCategory())
                        .price(post.getPrice())
                        .build()
                ).toList();
        return SellersPostsByFollowerResponseDto.builder().user_id(userId).posts(postsDto).build();
    }
}

