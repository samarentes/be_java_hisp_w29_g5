package com.social_media.social_media;

import com.github.javafaker.Faker;
import com.social_media.social_media.dto.request.PostRequestDto;
import com.social_media.social_media.dto.request.ProductRequestDto;
import com.social_media.social_media.dto.response.FollowingResponseDto;
import com.social_media.social_media.dto.response.PostResponseDto;
import com.social_media.social_media.dto.response.ProductResponseDto;
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

    public static Post createRandomPostNotUserId() {
        return new Post(
                faker.number().randomNumber(),
                faker.number().randomNumber(),
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

    public static ProductRequestDto createRandomProductRequestDto() {
        return new ProductRequestDto(
                faker.number().randomNumber(),
                faker.commerce().productName(),
                faker.commerce().department(),
                faker.company().name(),
                faker.color().name(),
                faker.lorem().sentence()
        );
    }

    public static ProductResponseDto createRandomProductResponseDto() {
        return new ProductResponseDto(
                faker.number().randomNumber(),
                faker.commerce().productName(),
                faker.commerce().department(),
                faker.company().name(),
                faker.color().name(),
                faker.lorem().sentence()
        );
    }

    public static PostResponseDto createRandomPostResponseDto() {
        return new PostResponseDto(
                faker.number().randomNumber(),
                faker.number().randomNumber(),
                LocalDate.now(),
                createRandomProductResponseDto(),
                random.nextInt(5) + 1,
                Double.valueOf(faker.commerce().price(10.0, 100.0).replace(",", "."))
        );
    }

    public static PostRequestDto createRandomPostRequestDto() {
        return new PostRequestDto(
                faker.number().randomNumber(),
                LocalDate.now(),
                createRandomProductRequestDto(),
                random.nextInt(5) + 1,
                Double.valueOf(faker.commerce().price(10.0, 100.0).replace(",", "."))
        );
    }

    public static PostResponseDto convertPostToResponseDto(Post post, ProductResponseDto product) {
        return PostResponseDto.builder()
                .post_id(post.getPostId())
                .user_id(post.getUserId())
                .date(post.getDate())
                .product(product)
                .category(post.getCategory())
                .price(post.getPrice())
                .build();
    }

    public static PostRequestDto convertPostToRequestDto(Post post, ProductRequestDto product) {
        return PostRequestDto.builder()
                .user_id(post.getUserId())
                .date(post.getDate())
                .product(product)
                .category(post.getCategory())
                .price(post.getPrice())
                .build();
    }

    public static ProductRequestDto convertProductToRequestDto(Product product) {
        return ProductRequestDto.builder()
                .product_id(product.getProductId())
                .product_name(product.getProductName())
                .type(product.getType())
                .color(product.getColor())
                .notes(product.getNotes())
                .build();
    }

    public static ProductResponseDto convertProductToResponsetDto(Product product) {
        return ProductResponseDto.builder()
                .product_id(product.getProductId())
                .product_name(product.getProductName())
                .type(product.getType())
                .color(product.getColor())
                .notes(product.getNotes())
                .build();
    }

}
