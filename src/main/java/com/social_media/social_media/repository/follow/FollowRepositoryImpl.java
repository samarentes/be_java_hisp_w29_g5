package com.social_media.social_media.repository.follow;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social_media.social_media.entity.Follow;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public boolean unfollowFollow(Long userId, Long userIdToUnfollow) {
        // Validar que el registro de seguimiento exista
        Optional<Map.Entry<UUID, Follow>> followEntry = follows.entrySet().stream()
                .filter(entry -> entry.getValue().getFollowerId().equals(userId)
                        && entry.getValue().getFollowedId().equals(userIdToUnfollow))
                .findFirst();

        UUID followId = followEntry.get().getKey();
        follows.remove(followId);
        return true;
    }










    @Override
    public Follow addFollow(Long followerId, Long followedId) {
        Follow newFollow = Follow.builder()
                .followerId(followerId)
                .followedId(followedId)
                .build();
        follows.put(UUID.randomUUID(), newFollow);

        return newFollow;
    }

    @Override
    public boolean existsByFollowerAndFollowed(Long userId, Long userIdToFollow) {
        return follows.values().stream()
                .anyMatch(f -> f.getFollowerId().equals(userId) && f.getFollowedId().equals(userIdToFollow));
    }

    public List<Follow> findFollowed(Long userId) {
        List<Follow> followedsFind = new ArrayList<>();
        follows.forEach((__, follow) -> {
            if (follow.getFollowerId().equals(userId)) {
                followedsFind.add(follow);
            }
        });

        return followedsFind;
    }

    @Override
    public List<Follow> findFollowers(Long userId) {
        List<Follow> followersFind = new ArrayList<>();
        this.follows.forEach((__, follow) -> {
            if (follow.getFollowedId().equals(userId)) {
                followersFind.add(follow);
            }
        });

        return followersFind;
    }

}
