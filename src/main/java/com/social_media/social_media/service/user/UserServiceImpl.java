package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.FollowingResponseDto;
import com.social_media.social_media.entity.Follow;
import com.social_media.social_media.exception.BadRequestFollowException;
import com.social_media.social_media.repository.follow.IFollowRepository;
import com.social_media.social_media.repository.post.IPostRepository;

import com.social_media.social_media.utils.MessagesExceptions;
import com.social_media.social_media.dto.responseDto.FollowedResponseDto;
import com.social_media.social_media.dto.responseDto.FollowersResponseDto;
import com.social_media.social_media.dto.responseDto.UserResponseDto;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.exception.NotFoundException;
import com.social_media.social_media.dto.responseDto.FollowersCountResponseDto;
import com.social_media.social_media.exception.NotSellerException;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.user.IUserRepository;

import static com.social_media.social_media.utils.MessagesExceptions.FOLLOWED_USER_NOT_SELLER;
import static com.social_media.social_media.utils.MessagesExceptions.SELLER_ID_NOT_EXIST;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final IFollowRepository followRepository;

    @Override
    public FollowersResponseDto searchFollowers(Long userId, String order) {
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

        if (order != null && order.equals("name_asc")) {
            followers.sort(Comparator.comparing(UserResponseDto::getUser_name));
        } else if (order != null && order.equals("name_desc")) {
            followers.sort(Comparator.comparing(UserResponseDto::getUser_name).reversed());

        }

        return new FollowersResponseDto(followedUser.get().getUserId(), followedUser.get().getName(), followers);

    }

    @Override
    public FollowedResponseDto searchFollowed(Long userId, String order) {
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

        if (order != null && order.equals("name_asc")) {
            followeds.sort(Comparator.comparing(UserResponseDto::getUser_name));
        } else if (order != null && order.equals("name_desc")) {
            followeds.sort(Comparator.comparing(UserResponseDto::getUser_name).reversed());

        }

        return new FollowedResponseDto(followerUser.get().getUserId(), followerUser.get().getName(), followeds);
    }

    public FollowingResponseDto followSeller(Long userId, Long userIdToFollow) {
        // validar que el mismo id de user no se siga
        if (userId.equals(userIdToFollow)) {
            throw new BadRequestFollowException(MessagesExceptions.THE_USER_CANNOT_FOLLOW_THEMSELVES);
        }

        // validar si el user id ya está siguiendo al seller especificado
        if (followRepository.existsByFollowerAndFollowed(userId, userIdToFollow)) {
            throw new BadRequestFollowException(MessagesExceptions.FOLLOW_ALREADY_EXISTS);
        }

        // validar que el usuario que intenta seguir si es un seller buscando si existe
        // al menos un post
        if (postRepository.findById(userIdToFollow).isEmpty()) {
            throw new BadRequestFollowException(MessagesExceptions.FOLLOWED_USER_NOT_SELLER);
        }

        Follow follow = followRepository.addFollow(userId, userIdToFollow);

        return FollowingResponseDto.builder()
                .user_id(follow.getFollowerId())
                .userIdToFollow(follow.getFollowedId())
                .build();
    }

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
