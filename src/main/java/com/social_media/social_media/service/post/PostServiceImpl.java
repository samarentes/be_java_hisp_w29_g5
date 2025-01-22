package com.social_media.social_media.service.post;

import com.social_media.social_media.dto.request.PostProductRequestDto;
import com.social_media.social_media.dto.responseDto.PostResponseDto;
import com.social_media.social_media.dto.responseDto.ProductResponseDto;
import com.social_media.social_media.entity.Post;
import com.social_media.social_media.entity.Product;
import com.social_media.social_media.exception.NotFoundException;
import com.social_media.social_media.repository.user.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.post.IPostRepository;

import java.util.ArrayList;
import java.util.List;

import static com.social_media.social_media.utils.MessagesExceptions.SELLER_ID_NOT_EXIST;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final IUserRepository userRepository;

    @Override
    public PostResponseDto createPost(PostProductRequestDto postProductRequestDto) {

        if (userRepository.findById(postProductRequestDto.getUser_id()).isEmpty())
            throw new NotFoundException(SELLER_ID_NOT_EXIST);

        Post post = Post.builder()
                .postId((long) (postRepository.findAll().size() + 1))
                .userId(postProductRequestDto.getUser_id())
                .date(postProductRequestDto.getDate())
                .product(Product.builder()
                        .productId(postProductRequestDto.getProduct().getProduct_id())
                        .productName(postProductRequestDto.getProduct().getProduct_name())
                        .type(postProductRequestDto.getProduct().getType())
                        .brand(postProductRequestDto.getProduct().getBrand())
                        .color(postProductRequestDto.getProduct().getColor())
                        .notes(postProductRequestDto.getProduct().getNotes())
                        .build())
                .category(postProductRequestDto.getCategory())
                .price(postProductRequestDto.getPrice())
                .build();

        Post newPost = postRepository.create(post);

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
                .build();
    }
}
