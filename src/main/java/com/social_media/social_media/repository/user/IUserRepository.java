package com.social_media.social_media.repository.user;

import java.util.Optional;

import com.social_media.social_media.entity.User;

public interface IUserRepository {
     Optional<User> findById(Long userId);
}
