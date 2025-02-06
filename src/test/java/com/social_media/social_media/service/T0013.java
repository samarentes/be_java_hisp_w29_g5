package com.social_media.social_media.service;

import com.social_media.social_media.TestUtils;
import com.social_media.social_media.dto.request.PostPromoRequestDto;
import com.social_media.social_media.dto.response.PostPromoResponseDto;
import com.social_media.social_media.dto.response.UserFavoritesResponseDto;
import com.social_media.social_media.entity.Post;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.exception.NotFoundException;
import com.social_media.social_media.repository.post.PostRepositoryImpl;
import com.social_media.social_media.repository.user.UserRepositoryImpl;
import com.social_media.social_media.service.user.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.social_media.social_media.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class T0013 {
    @Mock
    UserRepositoryImpl userRepository;

    @Mock
    PostRepositoryImpl postRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("T-0013 Search User Favorites Post Test 200")
    void searchUserFavoritesPostTest200() {
        // Arrange
        User user = createRandomUser();
        List<Post> favoritePosts = createListRandomPosts(5);
        List<Long> idFavoritePosts = favoritePosts.stream().mapToLong(Post::getPostId).boxed().toList();
        List<PostPromoResponseDto> favoritePostsDto = favoritePosts.stream().map(TestUtils::convertPostPromoToResponseDto).toList();

        UserFavoritesResponseDto expectedResponse = UserFavoritesResponseDto.builder().favorites(favoritePostsDto).build();

        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.findFavoritePostsById(user.getUserId())).thenReturn(idFavoritePosts);
        for(int i = 0; i < idFavoritePosts.size(); i++) {
            when(postRepository.findById(idFavoritePosts.get(i))).thenReturn(Optional.of(favoritePosts.get(i)));
        }

        // Act
        UserFavoritesResponseDto actualResponse = userService.searchUserFavoritesPost(user.getUserId());

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("T-0013 Search User With 0 Favorites Post Test 200")
    void searchUserWith0FavoritesPostTest200() {
        // Arrange
        User user = createRandomUser();
        UserFavoritesResponseDto expectedResponse = UserFavoritesResponseDto.builder().favorites(List.of()).build();

        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.findFavoritePostsById(user.getUserId())).thenReturn(List.of());

        // Act
        UserFavoritesResponseDto actualResponse = userService.searchUserFavoritesPost(user.getUserId());

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("T-0013 Search User Favorites Post Test 404 UserNotFound")
    void searchUserFavoritesPostTestFailUserNotFound() {
        // Arrange
        Long randomUserId = anyLong();
        when(userRepository.findById(randomUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> userService.searchUserFavoritesPost(randomUserId));
        verify(userRepository, never()).findFavoritePostsById(randomUserId);
        verify(postRepository, never()).findById(anyLong());
    }
}
