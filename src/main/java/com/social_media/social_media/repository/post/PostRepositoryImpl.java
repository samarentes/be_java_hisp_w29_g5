package com.social_media.social_media.repository.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.social_media.social_media.entity.Post;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements IPostRepository {
    private Map<Long, Post> posts;

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
        objectMapper.registerModule(new JavaTimeModule());
        List<Post> postList;

        file = ResourceUtils.getFile("classpath:posts.json");
        postList = objectMapper.readValue(file, new TypeReference<>() {});

        posts = postList.stream().collect(Collectors.toMap(Post::getPostId, post -> post));
    }

    @Override
    public Post create(Post post) {
        posts.put(post.getPostId(), post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        List<Post> postList = new ArrayList<>();
        posts.forEach((key, post) -> postList.add(post));
        return postList;
    }

    @Override
    public List<Post> findByIdSince(Long userId, LocalDate lastTwoWeeks) {
        return posts.values().stream().filter(post -> post.getUserId().equals(userId)
            && !post.getDate().isBefore(lastTwoWeeks)).toList();
    }
}
