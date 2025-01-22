package com.social_media.social_media.repository.user;

import com.social_media.social_media.entity.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> findById(Long userId);
}
