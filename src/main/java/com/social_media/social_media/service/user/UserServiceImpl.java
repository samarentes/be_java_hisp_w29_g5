package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.FollowersResponseDto;
import com.social_media.social_media.dto.responseDto.UserResponseDto;
import com.social_media.social_media.entity.Follow;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.exception.NotFoundException;
import com.social_media.social_media.dto.responseDto.FollowersCountResponseDto;
import com.social_media.social_media.exception.NotSellerException;
import com.social_media.social_media.repository.follow.IFollowRepository;
import com.social_media.social_media.repository.post.IPostRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.user.IUserRepository;
import com.social_media.social_media.utils.MessagesExceptions;

import java.util.List;
import java.util.Optional;

import static com.social_media.social_media.utils.MessagesExceptions.FOLLOWED_USER_NOT_SELLER;
import static com.social_media.social_media.utils.MessagesExceptions.SELLER_ID_NOT_EXIST;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final IFollowRepository followRepository;

    @Override
    public FollowersResponseDto searchFollowers(Long userId) {
        Optional<User> followedUser = this.userRepository.findById(userId);

        if (followedUser.isEmpty()) {
            throw new NotFoundException(MessagesExceptions.SELLER_ID_NOT_EXIST);
        }

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

        return new FollowersResponseDto(followedUser.get().getUserId(), followedUser.get().getName(), followers);

    }

    public FollowersCountResponseDto searchFollowersCount(Long userId) {

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty())
            throw new NotFoundException(SELLER_ID_NOT_EXIST);

        if (postRepository.findPostBySellerId(userId).isEmpty())
            throw new NotSellerException(FOLLOWED_USER_NOT_SELLER);

        List<Follow> listFilteredFollower = followRepository.findFollowers(userId);

        User user = userOptional.get();

        return FollowersCountResponseDto
                .builder()
                .user_id(user.getUserId())
                .user_name(user.getName())
                .followers_count(listFilteredFollower.size())
                .build();
    }
}
