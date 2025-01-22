package com.social_media.social_media.repository.follow;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social_media.social_media.entity.Follow;
import com.social_media.social_media.entity.Post;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FollowRepositoryImpl implements IFollowRepository {
    private Map<UUID, Follow> follows;

    public FollowRepositoryImpl() throws IOException {
        try {

            loadDataBase();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Follow> followList;

        file = ResourceUtils.getFile("classpath:follows.json");
        followList = objectMapper.readValue(file, new TypeReference<>() {
        });

        follows = followList.stream().collect(Collectors.toMap(follow -> UUID.randomUUID(), follow -> follow));
    }

    @Override
    public List<Follow> findListFollowerByFollowedId(Long followedId) {
        return follows.values().stream()
                .filter(follow -> follow.getFollowedId().equals(followedId))
                .collect(Collectors.toList());
    }
}
