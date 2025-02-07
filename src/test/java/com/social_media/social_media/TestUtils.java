package com.social_media.social_media;

import com.github.javafaker.Faker;
import com.social_media.social_media.dto.response.*;
import com.social_media.social_media.entity.Follow;
import com.social_media.social_media.entity.Post;
import com.social_media.social_media.entity.Product;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.exception.NotFoundException;
import com.social_media.social_media.utils.MessagesExceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestUtils {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static Product createRandomProduct() {
        return new Product(
                faker.number().randomNumber(),
                faker.commerce().productName(),
                faker.commerce().department(),
                faker.company().name(),
                faker.color().name(),
                faker.lorem().sentence());
    }

    public static Post createRandomPost(Long userId) {
        return new Post(
                faker.number().randomNumber(),
                userId,
                LocalDate.now(),
                createRandomProduct(),
                random.nextInt(5) + 1,
                Double.valueOf(faker.commerce().price(10.0, 100.0)),
                faker.number().randomDouble(2, 0, 50),
                LocalDate.now().plusDays(faker.number().numberBetween(1, 30)));
    }

    public static User createRandomUser() {
        return new User(
                faker.number().randomNumber(),
                faker.name().username(),
                new ArrayList<>());
    }

    public static Follow createFollow(Long userId, long userIdToFollow) {
        return new Follow(userId, userIdToFollow);
    }

    public static FollowingResponseDto convertFollowToResponseDto(Follow follow) {
        return FollowingResponseDto.builder()
                .user_id(follow.getFollowerId())
                .userIdToFollow(follow.getFollowedId())
                .build();
    }

    public static Post createRandomPostWithBrand(Long userId, String brand) {
        return new Post(
                faker.number().randomNumber(),
                userId,
                LocalDate.now(),
                createRandomProductWithBrand(brand),
                random.nextInt(5) + 1,
                Double.valueOf(faker.commerce().price(10.0, 100.0)),
                faker.number().randomDouble(2, 0, 50),
                LocalDate.now().plusDays(faker.number().numberBetween(1, 30)));
    }

    public static Product createRandomProductWithBrand(String brand) {
        return new Product(
                faker.number().randomNumber(),
                faker.commerce().productName(),
                faker.commerce().department(),
                brand,
                faker.color().name(),
                faker.lorem().sentence());
    }

    public static Post createRandomOldPost(Long userId) {
        return new Post(
                faker.number().randomNumber(),
                userId,
                LocalDate.now().minusWeeks(3),
                createRandomProduct(),
                random.nextInt(5) + 1,
                Double.valueOf(faker.commerce().price(10.0, 100.0)),
                faker.number().randomDouble(2, 0, 50),
                LocalDate.now().plusDays(faker.number().numberBetween(1, 30)));
    }

    public static FollowersCountResponseDto convertFollowersToFollowersCountResponseDto(User user,
            List<Follow> followers) {
        return FollowersCountResponseDto
                .builder()
                .user_id(user.getUserId())
                .user_name(user.getName())
                .followers_count(followers.size())
                .build();
    }

    public static List<User> createTenRandomUsers() {
        return IntStream.range(0, 10)
                .mapToObj(i -> createRandomUser())
                .collect(Collectors.toList());
    }

    public static List<Follow> createTenFollowersForUser(Long userId, List<User> followers, boolean ascending) {
        Comparator<User> comparator = Comparator.comparing(User::getName);
        if (!ascending) {
            comparator = comparator.reversed();
        }

        followers.sort(comparator);

        return followers.stream()
                .map(user -> createFollow(user.getUserId(), userId))
                .collect(Collectors.toList());
    }

    public static FollowersResponseDto convertFollowersToResponseDto(User userFollowed, List<User> followers) {
        return FollowersResponseDto.builder()
                .user_id(userFollowed.getUserId())
                .user_name(userFollowed.getName())
                .followers(followers.stream().map(TestUtils::convertUserToResponseDto).toList())
                .build();
    }

    public static FollowedResponseDto convertFollowedToResponseDto(User userFollowed, List<User> followers){
        return FollowedResponseDto.builder()
                .user_id(userFollowed.getUserId())
                .user_name(userFollowed.getName())
                .followed(followers.stream().map(TestUtils::convertUserToResponseDto).toList())
                .build();
    }


    public static UserResponseDto convertUserToResponseDto(User user) {
        return UserResponseDto.builder()
                .user_id(user.getUserId())
                .user_name(user.getName())
                .build();
    }
}