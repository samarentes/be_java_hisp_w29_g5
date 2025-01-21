package com.social_media.social_media.service;

import com.social_media.social_media.repository.IPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements IPostService{
    private final IPostRepository postRepository;
}
