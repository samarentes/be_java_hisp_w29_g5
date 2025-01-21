package com.social_media.social_media.dto.responseDto;

import java.util.List;

import com.social_media.social_media.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowersResponseDto {
    private Long user_id;
    private String user_name;
    private List<User> followers;
}
