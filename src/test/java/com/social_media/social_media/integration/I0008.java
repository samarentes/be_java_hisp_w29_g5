package com.social_media.social_media.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social_media.social_media.dto.response.FollowersResponseDto;
import com.social_media.social_media.dto.response.UserResponseDto;
import com.social_media.social_media.utils.MessagesExceptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class I0008 {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("T0008 - When trying search follower list by user id, then return follower list 200 OK")
    public void whenTryingToGetFollowersList_thenReturnFollowersList200OK() throws Exception {

        Long userId = 2L;
        String name = "Luis García";
        List<UserResponseDto> followers = List.of(
                new UserResponseDto(4L, "Carlos Mendoza"),
                new UserResponseDto(5L, "Sofía Romero")
        );

        MvcResult mvcResult = mockMvc.perform(get("/users/{userId}/follower/list", userId))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        int statusCode = mvcResult.getResponse().getStatus();

        FollowersResponseDto actualResponse = objectMapper.readValue(jsonResponse, FollowersResponseDto.class);
        FollowersResponseDto expectedResponseDto = new FollowersResponseDto(userId, name, followers);

        assertEquals(actualResponse, expectedResponseDto);
        assertEquals(200, statusCode);
    }

    @Test
    @DisplayName("T0008 - When trying search follower list by user id, then return error user not found 404")
    public void whenTryingToGetFollowersList_thenReturnErrorUserNotFound404() throws Exception {

        Long userId = 100L;

        mockMvc.perform(get("/users/{userId}/follower/list", userId))
                .andExpect(jsonPath("$.message", is(MessagesExceptions.USER_NOT_FOUND)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("T0008 - When trying search follower list by user id, then return user is not a seller")
    public void whenTryingToGetFollowersList_thenReturnEmptyFollowersList200OK() throws Exception {
        Long userId = 11L;

        mockMvc.perform(get("/users/{userId}/follower/list", userId))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message", is(MessagesExceptions.FOLLOWED_USER_NOT_SELLER)))
                .andReturn();
    }

}
