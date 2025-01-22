package com.social_media.social_media.repository.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social_media.social_media.entity.Post;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.UUID;

@Repository
public class PostRepositoryImpl implements IPostRepository {
    private Map<UUID, Post> posts;

    public PostRepositoryImpl() throws IOException {
        try {
            loadDataBase();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Post> postList;

        file = ResourceUtils.getFile("classpath:posts.json");
        postList = objectMapper.readValue(file, new TypeReference<>() {
        });

        posts = postList.stream().collect(Collectors.toMap(post -> UUID.randomUUID(), post -> post));
    }

    @Override
    public List<Post> findSellerById(Long userIdToFollow) {
        List<Post> filteredPosts = new ArrayList<>();
        for (Post post : posts.values()) {
            if (post.getUserId().equals(userIdToFollow)) {
                filteredPosts.add(post);
            }
        }
        return filteredPosts;
    }
}
