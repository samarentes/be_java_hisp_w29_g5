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

@SpringBootTest
@AutoConfigureMockMvc
public class I0006 {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepositoryImpl postRepository;

    @Autowired
    private FollowRepositoryImpl followRepository;

    @Test
    void testGetPostForLastTwoWeeks() throws Exception {

        Post recentPostOne = TestUtils.createRandomPost(11L);
        Post recentPostTwo = TestUtils.createRandomPost(11L);
        Post oldPost = TestUtils.createRandomOldPost(11L);

        postRepository.add(recentPostOne);
        postRepository.add(recentPostTwo);
        postRepository.add(oldPost);

        followRepository.addFollow(10L, 11L);

        mockMvc.perform(get("/products/followed/10/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id").value(10))
                .andExpect(jsonPath("$.posts.length()").value(2))
                .andExpect(jsonPath("$.posts[0].post_id").value(recentPostOne.getPostId()))
                .andExpect(jsonPath("$.posts[1].post_id").value(recentPostTwo.getPostId()));
    }
}
