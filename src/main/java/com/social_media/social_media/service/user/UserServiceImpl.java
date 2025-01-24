package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.*;
import com.social_media.social_media.entity.Follow;
import com.social_media.social_media.exception.BadRequestFollowException;
import com.social_media.social_media.repository.follow.IFollowRepository;
import com.social_media.social_media.repository.post.IPostRepository;

import com.social_media.social_media.utils.MessagesExceptions;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.exception.NotFoundException;
import com.social_media.social_media.exception.NotSellerException;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.user.IUserRepository;

import static com.social_media.social_media.utils.ComparatorOrder.getComparator;
import static com.social_media.social_media.utils.MessagesExceptions.FOLLOWED_USER_NOT_SELLER;
import static com.social_media.social_media.utils.MessagesExceptions.SELLER_ID_NOT_EXIST;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final IFollowRepository followRepository;

    @Override
    public Boolean unfollowSeller(Long userId, Long userIdToUnfollow) {
        // validar si el user id ya está siguiendo al seller especificado (si existe registro)
        Optional<Follow> follow = followRepository.existsByFollowerAndFollowed(userId, userIdToUnfollow);
        if (follow.isEmpty()) {
            throw new BadRequestFollowException(MessagesExceptions.NOT_FOLLOW_ALREADY_EXISTS);
        }

        followRepository.deleteFollow(follow.get());
        return true;
    }

    @Override
    public FollowersResponseDto searchFollowers(Long userId, String order) {
        Optional<User> followedUser = userRepository.findById(userId);

        if (followedUser.isEmpty()) {
            throw new NotFoundException(MessagesExceptions.SELLER_ID_NOT_EXIST);
        }

        List<Follow> followersFind = followRepository.findFollowers(userId);
        List<UserResponseDto> followers = followersFind.stream()
                .map(follow -> userRepository.findById(follow.getFollowerId())
                        .map(followerFound -> UserResponseDto.builder()
                                .user_id(followerFound.getUserId())
                                .user_name(followerFound.getName())
                                .build())
                        .orElse(null))
        .filter(Objects::nonNull)
        .sorted(getComparator(order, UserResponseDto::getUser_name))
        .toList();

        return new FollowersResponseDto(followedUser.get().getUserId(), followedUser.get().getName(), followers);
    }

    @Override
    public FollowedResponseDto searchFollowed(Long userId, String order) {
        Optional<User> followerUser = userRepository.findById(userId);

        if (followerUser.isEmpty()) {
            throw new NotFoundException(MessagesExceptions.USER_NOT_FOUND);
        }

        List<Follow> followedFind = followRepository.findFollowed(userId);
        List<UserResponseDto> followed = followedFind.stream()
                .map(follow -> userRepository.findById(follow.getFollowedId())
                    .map(followedFound -> UserResponseDto.builder()
                        .user_id(followedFound.getUserId())
                        .user_name(followedFound.getName())
                        .build())
                    .orElse(null))
        .filter(Objects::nonNull)
        .sorted(getComparator(order, UserResponseDto::getUser_name))
        .toList();

        return new FollowedResponseDto(followerUser.get().getUserId(), followerUser.get().getName(), followed);
    }

    public FollowingResponseDto followSeller(Long userId, Long userIdToFollow) {
        Optional<Follow> followExist = followRepository.existsByFollowerAndFollowed(userId, userIdToFollow);

        // validar que el mismo id de user no se siga
        if (userId.equals(userIdToFollow)) {
            throw new BadRequestFollowException(MessagesExceptions.THE_USER_CANNOT_FOLLOW_THEMSELVES);
        }

        // validar si el user id ya está siguiendo al seller especificado
        if (followExist.isPresent()) {
            throw new BadRequestFollowException(MessagesExceptions.FOLLOW_ALREADY_EXISTS);
        }

        // validar que el usuario que intenta seguir es un seller, buscando si existe al menos un post
        if (postRepository.findById(userIdToFollow).isEmpty()) {
            throw new BadRequestFollowException(MessagesExceptions.FOLLOWED_USER_NOT_SELLER);
        }

        Follow follow = followRepository.addFollow(userId, userIdToFollow);

        return FollowingResponseDto.builder()
                .user_id(follow.getFollowerId())
                .userIdToFollow(follow.getFollowedId())
                .build();
    }

    @Override
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
