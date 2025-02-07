package com.social_media.social_media.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.social_media.social_media.TestUtils;
import com.social_media.social_media.entity.Post;
import com.social_media.social_media.repository.follow.FollowRepositoryImpl;
import com.social_media.social_media.repository.post.PostRepositoryImpl;
import com.social_media.social_media.repository.user.UserRepositoryImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class I0015 {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepositoryImpl postRepository;

    @Autowired
    private FollowRepositoryImpl followRepository;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    void testGetSuggestions() throws Exception {

        String brand = "TEST";

        Post postOne = TestUtils.createRandomPostWithBrand(11L, brand);
        Post postTwo = TestUtils.createRandomPostWithBrand(10L, brand);

        postRepository.add(postOne);
        postRepository.add(postTwo);

        followRepository.addFollow(9L, 10L);

        userRepository.update(9L, postTwo.getPostId());

        mockMvc.perform(get("/users/9/suggestions?limit=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].user_id").value(11))
                .andExpect(jsonPath("$[0].user_name").value("Pepito Ruiz"))
                .andExpect(jsonPath("$[0].brandsSold").value(brand));
    }
}
