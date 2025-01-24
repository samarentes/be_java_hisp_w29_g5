package com.social_media.social_media.repository.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social_media.social_media.dto.responseDto.UserResponseDto;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.exception.DataLoadException;
import com.social_media.social_media.exception.NotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.social_media.social_media.utils.MessagesExceptions.INVALID_FOLLOW_ENTITY;
import static com.social_media.social_media.utils.MessagesExceptions.INVALID_USER_ENTITY;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    private Map<Long, User> users;

    public UserRepositoryImpl() {
        try {

            loadDataBase();
        } catch (IOException e) {
            throw new DataLoadException(INVALID_USER_ENTITY);
        }
    }

    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> userList;

        file = ResourceUtils.getFile("classpath:users.json");
        userList = objectMapper.readValue(file, new TypeReference<>() {
        });

        users = userList.stream().collect(Collectors.toMap(User::getUserId, user -> user));
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(this.users.get(userId));
    }
}
