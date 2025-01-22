package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.FollowedResponseDto;
import com.social_media.social_media.dto.responseDto.UserResponseDto;
import com.social_media.social_media.entity.Follow;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.exception.NotFoundException;
import com.social_media.social_media.repository.follow.IFollowRepository;
import com.social_media.social_media.repository.post.IPostRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.user.IUserRepository;
import com.social_media.social_media.utils.MessagesExceptions;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final IFollowRepository followRepository;

    @Override
    public FollowedResponseDto searchFollowed(Long userId) {
        Optional<User> followerUser = this.userRepository.findById(userId);

        if (followerUser.isEmpty()) {
            throw new NotFoundException(MessagesExceptions.USER_NOT_FOUND);
        }

        List<Follow> followedFind = this.followRepository.findFollowed(userId);
        List<UserResponseDto> followeds = followedFind.stream().map(follow -> {
            Optional<User> followedFound = this.userRepository.findById(follow.getFollowedId());
            if (followedFound.isPresent()) {

                return UserResponseDto.builder().user_id(followedFound.get().getUserId())
                        .user_name(followedFound.get().getName()).build();

            } else {
                return null;
            }
        }).toList();

        return new FollowedResponseDto(followerUser.get().getUserId(), followerUser.get().getName(), followeds);
    }

}
