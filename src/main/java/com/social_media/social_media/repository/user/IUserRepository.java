package com.social_media.social_media.repository.user;

import com.social_media.social_media.dto.responseDto.UserResponseDto;
import com.social_media.social_media.entity.User;

import java.util.List;

public interface IUserRepository {
    List<User> searchAllUsers();
}
