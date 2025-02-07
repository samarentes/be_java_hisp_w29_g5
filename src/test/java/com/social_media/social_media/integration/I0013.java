package com.social_media.social_media.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.social_media.social_media.dto.response.PostPromoResponseDto;
import com.social_media.social_media.dto.response.ProductResponseDto;
import com.social_media.social_media.dto.response.UserFavoritesResponseDto;
import com.social_media.social_media.dto.response.UserWithFavoritesPostResponseDto;
import com.social_media.social_media.entity.Post;
import com.social_media.social_media.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class I0013 {
        @Autowired
        private MockMvc mockMvc;

        @Test
        @DisplayName("I-0013 - Search User Favorites Post Test")
        void getFavorites() throws Exception {
                Long userId = 14L;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                List<PostPromoResponseDto> favoritePosts = new ArrayList<>();
                favoritePosts.add(new PostPromoResponseDto(15L, 1L, LocalDate.parse("03-10-2023", formatter),
                                new ProductResponseDto(1L, "Camiseta Deportiva", "Ropa", "Adidas", "Rojo",
                                                "Ideal para correr y hacer ejercicio."),
                                2, 29.99, false, 0.0));
                favoritePosts.add(new PostPromoResponseDto(21L, 2L, LocalDate.parse("03-10-2023", formatter),
                                new ProductResponseDto(2L, "Pantalones Deportivos", "Ropa", "Nike", "Negro",
                                                "Comodidad y estilo."),
                                2, 39.99, false, 0.0));
                favoritePosts.add(new PostPromoResponseDto(31L, 3L, LocalDate.parse("03-10-2023", formatter),
                                new ProductResponseDto(3L, "Zapatillas Running", "Calzado", "Puma", "Blanco",
                                                "Amortiguación avanzada."),
                                1, 79.99, false, 0.0));
                favoritePosts.add(new PostPromoResponseDto(41L, 4L, LocalDate.parse("03-10-2023", formatter),
                                new ProductResponseDto(4L, "Gorra Deportiva", "Accesorio", "Reebok", "Azul",
                                                "Protección solar y estilo."),
                                3, 19.99, false, 0.0));
                UserFavoritesResponseDto responseDto = UserFavoritesResponseDto.builder().favorites(favoritePosts)
                                .build();

                ObjectWriter objectWriter = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                                .registerModule(new JavaTimeModule())
                                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                                .writer();
                String responseJson = objectWriter.writeValueAsString(responseDto);

                MvcResult response = mockMvc.perform(get("/users/{userId}/favorites/list", userId))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType("application/json"))
                                .andReturn();

                assertEquals(responseJson, response.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("I-0013 - Update User Favorites Post Test")
        void postUpdateFavorites() throws Exception {
                Long userId = 14L;
                Long postId = 5L;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                List<Post> favoritePosts = new ArrayList<>();
                favoritePosts.add(new Post(1L, 1L, LocalDate.parse("03-10-2023", formatter),
                                new Product(1L, "Camiseta Deportiva", "Ropa", "Adidas", "Rojo",
                                                "Ideal para correr y hacer ejercicio."),
                                2, 29.99, 0.0, null));
                favoritePosts.add(new Post(2L, 2L, LocalDate.parse("03-10-2023", formatter),
                                new Product(2L, "Pantalones Deportivos", "Ropa", "Nike", "Negro",
                                                "Comodidad y estilo."),
                                2, 39.99, 0.0, null));
                favoritePosts.add(new Post(3L, 3L, LocalDate.parse("03-10-2023", formatter),
                                new Product(3L, "Zapatillas Running", "Calzado", "Puma", "Blanco",
                                                "Amortiguación avanzada."),
                                1, 79.99, 0.0, null));
                favoritePosts.add(new Post(4L, 4L, LocalDate.parse("03-10-2023", formatter),
                                new Product(4L, "Gorra Deportiva", "Accesorio", "Reebok", "Azul",
                                                "Protección solar y estilo."),
                                3, 19.99, 0.0, null));
                favoritePosts.add(new Post(5L, 5L, LocalDate.parse("03-10-2023", formatter),
                                new Product(5L, "Botella de Agua", "Accesorio", "CamelBak", "Transparente",
                                                "Ideal para hidratarse en el deporte."),
                                3, 12.99, 0.0, null));
                UserWithFavoritesPostResponseDto responseDto = UserWithFavoritesPostResponseDto.builder()
                                .user_id(userId)
                                .user_name("Lionel Messi")
                                .favorite_posts(favoritePosts)
                                .build();

                ObjectWriter objectWriter = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                                .registerModule(new JavaTimeModule())
                                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                                .writer();
                String responseJson = objectWriter.writeValueAsString(responseDto);

                MvcResult response = mockMvc.perform(post("/users/{userId}/favorites/{postId}", userId, postId))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType("application/json"))
                                .andReturn();

                assertEquals(responseJson, response.getResponse().getContentAsString());
        }
}