package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.FollowedResponseDto;
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
    public FollowedResponseDto searchFollowed(Long userId) {
        User followedUser = this.userRepository.findById(userId).get();
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

        return new FollowedResponseDto(followedUser.getUserId(), followedUser.getName(), followeds);
    }

}
