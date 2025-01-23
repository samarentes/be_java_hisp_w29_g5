package com.social_media.social_media.service.user;

import com.social_media.social_media.exception.BadRequestFollowException;
import com.social_media.social_media.repository.follow.IFollowRepository;
import com.social_media.social_media.repository.post.IPostRepository;

import com.social_media.social_media.utils.MessagesExceptions;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.repository.follow.IFollowRepository;
import com.social_media.social_media.repository.post.IPostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.user.IUserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final IFollowRepository followRepository;

    @Override
    public Boolean unfollowSeller(Long userId, Long userIdToUnfollow) {
        //validar si el user id ya está siguiendo al seller especificado(si existe registro)
        if (!followRepository.existsByFollowerAndFollowed(userId, userIdToUnfollow)) {
            throw new BadRequestFollowException(MessagesExceptions.NOT_FOLLOW_ALREADY_EXISTS);
        }

        followRepository.unfollowFollow(userId, userIdToUnfollow);
        return true;
    }


}
