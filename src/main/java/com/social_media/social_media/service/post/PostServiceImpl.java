package com.social_media.social_media.service.post;

import com.social_media.social_media.dto.request.PostRequestDto;
import com.social_media.social_media.dto.request.PostPromoRequestDto;
import com.social_media.social_media.dto.request.IPostRequestDto;
import com.social_media.social_media.dto.request.ProductRequestDto;
import com.social_media.social_media.dto.responseDto.PostPromoResponseDto;
import com.social_media.social_media.dto.responseDto.PostResponseDto;
import com.social_media.social_media.dto.responseDto.ProductResponseDto;
import com.social_media.social_media.entity.Post;
import com.social_media.social_media.entity.Product;
import com.social_media.social_media.enums.PostType;
import com.social_media.social_media.exception.NotFoundException;
import com.social_media.social_media.repository.user.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.post.IPostRepository;

import static com.social_media.social_media.utils.MessagesExceptions.SELLER_ID_NOT_EXIST;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final IUserRepository userRepository;

    @Override
    public PostResponseDto createPost(PostRequestDto postProductRequestDto) {
        Post post = createPostCommon(postProductRequestDto, 0.0);
        postRepository.create(post);
        return buildPostResponseDto(post);
    }

    @Override
    public PostPromoResponseDto createPostPromo(PostPromoRequestDto postPromoRequestDto) {
        Post post = createPostCommon(postPromoRequestDto, postPromoRequestDto.getDiscount());
        postRepository.create(post);
        return buildPostPromoResponseDto(post);
    }

    private <T extends IPostRequestDto> Post createPostCommon(T postRequestDto, Double discount) {
        if (userRepository.findById(postRequestDto.getUser_id()).isEmpty()){
            throw new NotFoundException(SELLER_ID_NOT_EXIST);
        }

        return Post.builder()
                .postId((long) (postRepository.findAll(PostType.ALL).size() + 1))
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
}
