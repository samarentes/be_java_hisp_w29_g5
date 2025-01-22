package com.social_media.social_media.service;

import com.social_media.social_media.dto.responseDto.PostResponseDto;
import com.social_media.social_media.dto.responseDto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.post.IPostRepository;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;


    @Override
    public List<PostResponseDto> searchAllPosts() {
        // Convertir entidades Post a DTO
        return postRepository.searchAllPosts().stream()
                .map(post -> new PostResponseDto(
                        post.getUserId(),
                        post.getDate(),
                        new ProductResponseDto(
                                post.getProduct().getProductId(),
                                post.getProduct().getProductName(),
                                post.getProduct().getType(),
                                post.getProduct().getBrand(),
                                post.getProduct().getColor(),
                                post.getProduct().getNotes()
                        ),
                        post.getCategory(),
                        post.getPrice()
                ))
                .collect(Collectors.toList());
    }
    }

