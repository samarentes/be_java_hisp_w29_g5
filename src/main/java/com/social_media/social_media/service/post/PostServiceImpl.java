package com.social_media.social_media.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.post.IPostRepository;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
}
