package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.FollowedResponseDto;
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
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.user.IUserRepository;
import com.social_media.social_media.utils.MessagesExceptions;

import static com.social_media.social_media.utils.MessagesExceptions.FOLLOWED_USER_NOT_SELLER;
import static com.social_media.social_media.utils.MessagesExceptions.SELLER_ID_NOT_EXIST;

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
            User followedFound = this.userRepository.findById(follow.getFollowedId()).orElse(null);

            return UserResponseDto.builder().user_id(followedFound.getUserId())
                    .user_name(followedFound.getName()).build();

        }).filter(Objects::nonNull)
                .collect(Collectors.toList());
        ;

        return new FollowedResponseDto(followerUser.get().getUserId(), followerUser.get().getName(), followeds);
    }

    public FollowersResponseDto searchFollowers(Long userId) {
        Optional<User> followedUser = this.userRepository.findById(userId);

        if (followedUser.isEmpty()) {
            throw new NotFoundException(MessagesExceptions.SELLER_ID_NOT_EXIST);
        }

        List<Follow> followersFind = this.followRepository.findFollowers(userId);
        List<UserResponseDto> followers = followersFind.stream().map(follow -> {
            User followerFound = this.userRepository.findById(follow.getFollowerId()).orElse(null);

            return UserResponseDto.builder().user_id(followerFound.getUserId())
                    .user_name(followerFound.getName()).build();

        }).filter(Objects::nonNull)
                .collect(Collectors.toList());

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
