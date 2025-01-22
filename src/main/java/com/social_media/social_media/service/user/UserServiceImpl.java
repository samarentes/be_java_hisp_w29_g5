package com.social_media.social_media.service.user;

import com.social_media.social_media.dto.responseDto.FollowersCountResponseDto;
import com.social_media.social_media.entity.Follow;
import com.social_media.social_media.entity.User;
import com.social_media.social_media.repository.follow.IFollowRepository;
import com.social_media.social_media.repository.post.IPostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.social_media.social_media.repository.user.IUserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final IFollowRepository followRepository;

    @Override
    public FollowersCountResponseDto getFollowersCount(Long userId) {

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) throw new RuntimeException("User not found");

        if (!postRepository.isSeller(userId)) throw new RuntimeException("User is not a seller");

        List<Follow> listFilteredFollower = followRepository.findListFollowerByFollowedId(userId);

        User user = userOptional.get();

        return FollowersCountResponseDto
                .builder()
                .user_id(user.getUserId())
                .user_name(user.getName())
                .followers_count(listFilteredFollower.size())
                .build();
    }
}
