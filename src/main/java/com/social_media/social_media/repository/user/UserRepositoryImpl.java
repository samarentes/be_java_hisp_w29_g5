package com.social_media.social_media.repository.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social_media.social_media.dto.responseDto.UserResponseDto;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.exception.NotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    private Map<Long, User> users;

    public UserRepositoryImpl() throws IOException {
        System.out.println("test");
        try {

            loadDataBase();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
    public List<User> searchAllUsers() {

        return users.values().stream().toList();}
}
