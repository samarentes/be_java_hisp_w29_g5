package com.social_media.social_media.service;

import com.social_media.social_media.dto.responseDto.FollowersResponseDto;
import com.social_media.social_media.dto.responseDto.UserResponseDto;
import com.social_media.social_media.entity.Follow;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.repository.follow.IFollowRepository;
import com.social_media.social_media.repository.post.IPostRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.user.IUserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final IFollowRepository followRepository;

    @Override
    public FollowersResponseDto searchFollowers(Long userId) {
        User followedUser = this.userRepository.findById(userId).get();
        List<Follow> followersFind = this.followRepository.findFollowers(userId);
        List<UserResponseDto> followers = followersFind.stream().map(follow -> {
            Optional<User> followerFound = this.userRepository.findById(follow.getFollowerId());
            if (followerFound.isPresent()) {

                return UserResponseDto.builder().user_id(followerFound.get().getUserId())
                        .user_name(followerFound.get().getName()).build();

            } else {
                return null;
            }
        }).toList();

        return new FollowersResponseDto(followedUser.getUserId(), followedUser.getName(), followers);

    }

}
