package com.social_media.social_media.service;

import com.social_media.social_media.dto.responseDto.FollowingResponseDto;
import com.social_media.social_media.entity.Follow;
import com.social_media.social_media.exception.BadRequestFollowException;
import com.social_media.social_media.repository.follow.IFollowRepository;
import com.social_media.social_media.repository.post.IPostRepository;

import com.social_media.social_media.utils.MessagesExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.user.IUserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final IFollowRepository followRepository;

    @Override
    public FollowingResponseDto followSeller(Long userId, Long userIdToFollow) {
        //validar que el mismo id de user no se siga
        if (userId.equals(userIdToFollow)){
            throw new BadRequestFollowException(MessagesExceptions.THE_USER_CANNOT_FOLLOW_THEMSELVES);
        }

        //validar si el user id ya está siguiendo al seller especificado
        if (followRepository.existsByFollowerAndFollowed(userId, userIdToFollow)) {
            throw new BadRequestFollowException(MessagesExceptions.FOLLOW_ALREADY_EXISTS);
        }

        //validar que el usuario que intenta seguir si es un seller buscando si existe al menos un post
        if (postRepository.findSellerById(userIdToFollow).isEmpty()){
            throw new BadRequestFollowException(MessagesExceptions.FOLLOWED_USER_NOT_SELLER);
        }

        Follow follow = followRepository.addFollow(userId, userIdToFollow);

        return FollowingResponseDto.builder()
                .user_id(follow.getFollowerId())
                .userIdToFollow(follow.getFollowedId())
                .build();
    }
}
