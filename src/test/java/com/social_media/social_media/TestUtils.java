package com.social_media.social_media;

import com.github.javafaker.Faker;
import com.social_media.social_media.dto.response.FollowingResponseDto;
import com.social_media.social_media.entity.Follow;
import com.social_media.social_media.entity.Post;
import com.social_media.social_media.entity.Product;
import com.social_media.social_media.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

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
                faker.lorem().sentence()
        );
    }

    public static Post createRandomPost(Long userId) {
        return new Post(
                faker.number().randomNumber(),
                userId,
                LocalDate.now(),
                createRandomProduct(),
                random.nextInt(5) + 1,
                Double.valueOf(faker.commerce().price(10.0, 100.0).replace(",", ".")),
                faker.number().randomDouble(2, 0, 50),
                LocalDate.now().plusDays(faker.number().numberBetween(1, 30))
        );
    }

    public static User createRandomUser() {
        return new User(
                faker.number().randomNumber(),
                faker.name().username(),
                new ArrayList<>()
        );
    }

    public static Follow createFollow(Long userId, long userIdToFollow){
        return new Follow(userId,userIdToFollow);
    }

    public static FollowingResponseDto convertFollowToResponseDto(Follow follow) {
        return FollowingResponseDto.builder()
                .user_id(follow.getFollowerId())
                .userIdToFollow(follow.getFollowedId())
                .build();
    }


}