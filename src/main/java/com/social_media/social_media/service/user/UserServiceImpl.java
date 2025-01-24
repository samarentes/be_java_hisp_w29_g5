package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.*;
import com.social_media.social_media.entity.Follow;
import com.social_media.social_media.entity.Post;
import com.social_media.social_media.exception.BadRequestFollowException;
import com.social_media.social_media.exception.InvalidOrderException;
import com.social_media.social_media.repository.follow.IFollowRepository;
import com.social_media.social_media.repository.post.IPostRepository;

import com.social_media.social_media.utils.MessagesExceptions;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.exception.NotFoundException;
import com.social_media.social_media.exception.NotSellerException;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.user.IUserRepository;

import static com.social_media.social_media.utils.MessagesExceptions.*;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final IFollowRepository followRepository;

    @Override
    public Boolean unfollowSeller(Long userId, Long userIdToUnfollow) {
        // validar si el user id ya está siguiendo al seller especificado(si existe
        // registro)
        Optional<Follow> follow = followRepository.existsByFollowerAndFollowed(userId, userIdToUnfollow);
        if (follow.isEmpty()) {
            throw new BadRequestFollowException(MessagesExceptions.NOT_FOLLOW_ALREADY_EXISTS);
        }

        followRepository.deleteFollow(follow);
        return true;
    }

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

        if (order.equals("name_asc")) {
            followers.sort(Comparator.comparing(UserResponseDto::getUser_name));
        } else if (order.equals("name_desc")) {
            followers.sort(Comparator.comparing(UserResponseDto::getUser_name).reversed());

        } else {
            throw new InvalidOrderException(MessagesExceptions.INVALID_ORDER_NAME);
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

        if (order.equals("name_asc")) {
            followeds.sort(Comparator.comparing(UserResponseDto::getUser_name));
        } else if (order.equals("name_desc")) {
            followeds.sort(Comparator.comparing(UserResponseDto::getUser_name).reversed());

        } else {
            throw new InvalidOrderException(MessagesExceptions.INVALID_ORDER_NAME);
        }

        return new FollowedResponseDto(followerUser.get().getUserId(), followerUser.get().getName(), followeds);
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

        // validar que el usuario que intenta seguir si es un seller buscando si existe
        // al menos un post
        if (postRepository.findByUserId(userIdToFollow).isEmpty()) {
            throw new BadRequestFollowException(MessagesExceptions.FOLLOWED_USER_NOT_SELLER);
        }

        Follow follow = followRepository.addFollow(userId, userIdToFollow);

        return FollowingResponseDto.builder()
                .user_id(follow.getFollowerId())
                .userIdToFollow(follow.getFollowedId())
                .build();
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

    @Override
    public UserWithFavoritesPostResponseDto updateUserFavoritesPost(Long userId, Long postId) {
        if (userRepository.findById(userId).isEmpty()) throw new NotFoundException(USER_NOT_FOUND);

        if (postRepository.findById(postId).isEmpty()) throw new NotFoundException(POST_NOT_FOUND);

        if (userRepository.findFavoritePostsById(userId).contains(postId))
            throw new BadRequestFollowException(POST_ALREADY_FAVORITE);

        User updateUser = userRepository.update(userId, postId);

        List<Post> favoritePosts = updateUser.getFavoritePosts().stream()
                .map(postRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        return UserWithFavoritesPostResponseDto.builder()
                .user_id(updateUser.getUserId())
                .user_name(updateUser.getName())
                .favorite_posts(favoritePosts)
                .build();
    }

    @Override
    public UserFavoritesResponseDto searchUserFavoritesPost(long userId) {
        if (userRepository.findById(userId).isEmpty()) throw new NotFoundException(USER_NOT_FOUND);

        List<Long> favoritePosts = userRepository.findFavoritePostsById(userId);

        List<Post> posts = favoritePosts.stream()
                .map(postRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        List<PostPromoResponseDto> postsDto = posts.stream()
                .map(post -> PostPromoResponseDto.builder()
                        .post_id(post.getPostId())
                        .user_id(post.getUserId())
                        .date(post.getDate())
                        .product(ProductResponseDto.builder()
                                .product_id(post.getProduct().getProductId())
                                .product_name(post.getProduct().getProductName())
                                .type(post.getProduct().getType())
                                .brand(post.getProduct().getBrand())
                                .color(post.getProduct().getColor())
                                .notes(post.getProduct().getNotes())
                                .build())
                        .category(post.getCategory())
                        .price(post.getPrice())
                        .has_promo(post.getDiscount() != 0.0)
                        .discount(post.getDiscount())
                        .build()
                ).toList();


        return UserFavoritesResponseDto.builder().favorites(postsDto).build();

    }


}
