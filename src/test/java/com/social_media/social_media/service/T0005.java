package com.social_media.social_media.service;

import com.social_media.social_media.dto.request.PostRequestDto;
import com.social_media.social_media.dto.request.ProductRequestDto;
import com.social_media.social_media.dto.response.PostResponseDto;
import com.social_media.social_media.dto.response.ProductResponseDto;
import com.social_media.social_media.entity.Post;
import com.social_media.social_media.entity.Product;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.exception.NotFoundException;
import com.social_media.social_media.repository.post.PostRepositoryImpl;
import com.social_media.social_media.repository.user.UserRepositoryImpl;
import com.social_media.social_media.service.post.PostServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class T0005 {

    @Mock
    PostRepositoryImpl postRepository;

    @Mock
    UserRepositoryImpl userRepository;

    @InjectMocks
    PostServiceImpl postService;

    @Test
    @DisplayName("T-0005 - Test of create post - Happy way")
    void createPostOk(){

        // 🟢 **Arrange**
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("18-01-2025", formatter);

        //entitys to mock
        Product productMock = new Product(
                11L, "Sudadera Deportiva","Ropa","Nike","Negro", "Diseñada para mantener el calor."
        );
        Post postMock = new Post(1L, 1L, date, productMock, 2, 49.99, 0.0, null);

        ProductRequestDto productRequestDto = new ProductRequestDto(
                11L, "Sudadera Deportiva","Ropa","Nike","Negro", "Diseñada para mantener el calor."
        );

        ProductResponseDto productResponseDto = new ProductResponseDto(
                11L, "Sudadera Deportiva","Ropa","Nike","Negro", "Diseñada para mantener el calor."
        );
        PostResponseDto postResponseDtoExpected = new PostResponseDto(1L, 1L, date, productResponseDto, 2, 49.99);
        PostRequestDto postRequestDto = new PostRequestDto(1L, date, productRequestDto, 2, 49.99);

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(postRepository.add(postMock)).thenReturn(postMock);

        // 🟡 **Act**
        PostResponseDto postResponseDtoActual = postService.createPost(postRequestDto);

        // 🔴 **Assert**
        //assertThat compara automáticamente todos los atributos de los objetos de forma recursiva, esto evita la necesidad de escribir múltiples assertEquals() manualmente.
        assertThat(postResponseDtoActual).usingRecursiveComparison().isEqualTo(postResponseDtoExpected);
    }

    @Test
    @DisplayName("T-0005 - Test of create post (User not found) - Sad way")
    void createPostNotOk() {
        // 🟢 **Arrange**
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("18-01-2025", formatter);

        ProductRequestDto productRequestDto = new ProductRequestDto(
                11L, "Sudadera Deportiva","Ropa","Nike","Negro", "Diseñada para mantener el calor."
        );
        PostRequestDto postRequestDto = new PostRequestDto(1L, date, productRequestDto, 2, 49.99);

        //se simula que el id de usuario pasado no existe
        when(userRepository.findById(postRequestDto.getUser_id())).thenReturn(Optional.empty());

        // 🔴 **Act && Assert**
        assertThrows(NotFoundException.class,
                ()-> postService.createPost(postRequestDto)
        );

    }
}
