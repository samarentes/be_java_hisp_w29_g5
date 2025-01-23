package com.social_media.social_media.service.post;

import com.social_media.social_media.dto.responseDto.PostResponseWithIdDto;
import com.social_media.social_media.dto.responseDto.ProductResponseDto;
import com.social_media.social_media.dto.responseDto.SellersPostsByFollowerResponseDto;
import com.social_media.social_media.entity.Follow;
import com.social_media.social_media.repository.follow.IFollowRepository;
import com.social_media.social_media.utils.MessagesExceptions;
import com.social_media.social_media.dto.request.PostProductRequestDto;
import com.social_media.social_media.dto.request.PostRequestDto;
import com.social_media.social_media.dto.request.PostPromoRequestDto;
import com.social_media.social_media.dto.request.IPostRequestDto;
import com.social_media.social_media.dto.request.ProductRequestDto;
import com.social_media.social_media.dto.responseDto.PostPromoResponseDto;
import com.social_media.social_media.dto.responseDto.PostResponseDto;
import com.social_media.social_media.entity.Post;
import com.social_media.social_media.entity.Product;
import com.social_media.social_media.enums.PostType;
import com.social_media.social_media.exception.NotFoundException;
import com.social_media.social_media.repository.user.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.social_media.social_media.repository.post.IPostRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static com.social_media.social_media.utils.MessagesExceptions.SELLER_ID_NOT_EXIST;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final IUserRepository userRepository;
    private final IFollowRepository followRepository;

    @Override
    public PostResponseDto createPost(PostRequestDto postProductRequestDto) {
        Post post = createPostCommon(postProductRequestDto, 0.0);
        postRepository.add(post);
        return buildPostResponseDto(post);
    }

    @Override
    public PostPromoResponseDto createPostPromo(PostPromoRequestDto postPromoRequestDto) {
        Post post = createPostCommon(postPromoRequestDto, postPromoRequestDto.getDiscount());
        postRepository.add(post);
        return buildPostPromoResponseDto(post);
    }

    private Post createPostCommon(IPostRequestDto postRequestDto, Double discount) {
        if (userRepository.findById(postRequestDto.getUser_id()).isEmpty()) {
            throw new NotFoundException(SELLER_ID_NOT_EXIST);
        }

        return Post.builder()
                .postId(Integer.toUnsignedLong(postRepository.findAll(PostType.ALL).size() + 1))
                .userId(postRequestDto.getUser_id())
                .date(postRequestDto.getDate())
                .product(buildProduct(postRequestDto.getProduct()))
                .category(postRequestDto.getCategory())
                .price(postRequestDto.getPrice())
                .discount(discount)
                .build();
    }

    // Method to build a Product object from a ProductRequestDto object
    private Product buildProduct(ProductRequestDto productRequestDto) {
        return Product.builder()
                .productId(productRequestDto.getProduct_id())
                .productName(productRequestDto.getProduct_name())
                .type(productRequestDto.getType())
                .brand(productRequestDto.getBrand())
                .color(productRequestDto.getColor())
                .notes(productRequestDto.getNotes())
                .build();
    }

    // Method to build a PostResponseDto object from a Post object
    private PostResponseDto buildPostResponseDto(Post post) {
        return PostResponseDto.builder()
                .post_id(newPost.getPostId())
                .user_id(newPost.getUserId())
                .date(newPost.getDate())
                .product(ProductResponseDto.builder()
                        .product_id(newPost.getProduct().getProductId())
                        .product_name(newPost.getProduct().getProductName())
                        .type(newPost.getProduct().getType())
                        .brand(newPost.getProduct().getBrand())
                        .color(newPost.getProduct().getColor())
                        .notes(newPost.getProduct().getNotes())
                        .build())
                .category(newPost.getCategory())
                .price(newPost.getPrice())
                .post_id(post.getPostId())
                .user_id(post.getUserId())
                .date(post.getDate())
                .product(buildProductResponseDto(post.getProduct()))
                .category(post.getCategory())
                .price(post.getPrice())
                .build();
    }

    // Method to build a PostPromoResponseDto object from a Post object
    private PostPromoResponseDto buildPostPromoResponseDto(Post post) {
        return PostPromoResponseDto.builder()
                .post_id(post.getPostId())
                .user_id(post.getUserId())
                .date(post.getDate())
                .product(buildProductResponseDto(post.getProduct()))
                .category(post.getCategory())
                .price(post.getPrice())
                .discount(post.getDiscount())
                .has_promo(true)
                .build();
    }

    // Method to build a ProductResponseDto object from a Product object
    private ProductResponseDto buildProductResponseDto(Product product) {
        return ProductResponseDto.builder()
                .product_id(product.getProductId())
                .product_name(product.getProductName())
                .type(product.getType())
                .brand(product.getBrand())
                .color(product.getColor())
                .notes(product.getNotes())
                .build();
    }

    @Override
    public SellersPostsByFollowerResponseDto searchFollowedPostsFromLastTwoWeeks(long userId) {
        LocalDate lastTwoWeeks = LocalDate.now().minusWeeks(2);

        List<Long> followedIds = followRepository.findFollowed(userId).stream().map(Follow::getFollowedId).toList();
        if (followedIds.isEmpty()) {
            throw new NotFoundException(MessagesExceptions.NO_FOLLOWERS_FOUND + userId);
        }
        List<Post> posts = followedIds.stream().flatMap(followedId ->
                postRepository.findByIdSince(followedId, lastTwoWeeks).stream()).toList();

        List<PostResponseWithIdDto> postsDto = posts.stream()
                .map(post -> PostResponseWithIdDto.builder()
                        .user_id(post.getUserId())
                        .post_id(post.getPostId())
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
                        .build()
                ).sorted(Comparator.comparing(PostResponseWithIdDto::getDate).reversed()
                        .thenComparing(PostResponseWithIdDto::getPost_id))
                .toList();
        return SellersPostsByFollowerResponseDto.builder().user_id(userId).posts(postsDto).build();
    }
}
