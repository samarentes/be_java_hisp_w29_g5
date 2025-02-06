package com.social_media.social_media.service;

import com.social_media.social_media.TestUtils;
import com.social_media.social_media.dto.response.PromoProductsResponseDto;
import com.social_media.social_media.entity.Post;
import com.social_media.social_media.entity.Product;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.enums.PostType;
import com.social_media.social_media.exception.NotFoundException;
import com.social_media.social_media.exception.NotSellerException;
import com.social_media.social_media.repository.post.PostRepositoryImpl;
import com.social_media.social_media.repository.user.UserRepositoryImpl;
import com.social_media.social_media.service.post.PostServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class T0011 {

    @Mock
    PostRepositoryImpl postRepository;

    @Mock
    UserRepositoryImpl userRepository;

    @InjectMocks
    PostServiceImpl postService;

    @Test
    @DisplayName("T-0011 - Test of search Sellers With Promo Posts - Happy way")
    void searchSellersWithPromoPostsOk(){
        // 🟢 **Arrange**
        Long id = 1L;
        List<Post> postListToMock = List.of(
                TestUtils.createRandomPostNotUserId(),
                TestUtils.createRandomPostNotUserId()
        );
        when(userRepository.findById(id)).thenReturn(Optional.of(TestUtils.createRandomUser()));
        when(postRepository.findPostBySellerId(id)).thenReturn(postListToMock);
        when(postRepository.findWithFilters(PostType.PROMO, id)).thenReturn(postListToMock);

        // 🟡 **Act**
        PromoProductsResponseDto responseActual = postService.searchSellersWithPromoPosts(id);

        // 🔴 **Assert**
        assertEquals(id, responseActual.getUser_id());
    }

    @Test
    @DisplayName("T-0011 - Test of search Sellers With Promo Posts (Seller Id Not Exist) - Sad way")
    void searchSellersWithPromoPostsNotSeller(){
        // 🟢 **Arrange**
        Long sellerId = 1L;

        //se simula que el id de usuario pasado no existe
        when(userRepository.findById(sellerId)).thenReturn(Optional.empty());

        // 🔴 **Act && Assert**
        assertThrows(NotFoundException.class, ()-> postService.searchSellersWithPromoPosts(sellerId));
    }

    @Test
    @DisplayName("T-0011 - Test of search Sellers With Promo Posts (Followed User Not Seller) - Sad way")
    void searchSellersWithPromoPostsFollowedNotSeller(){
        // 🟢 **Arrange**
        Long userId = 1L;

        User userMock = new User();

        // Se simula que el usuario existe
        when(userRepository.findById(userId)).thenReturn(Optional.of(userMock));
        when(postRepository.findPostBySellerId(userId)).thenReturn(Collections.emptyList());

        // 🔴 **Act && Assert**
        assertThrows(NotSellerException.class, ()-> postService.searchSellersWithPromoPosts(userId));
    }
}
