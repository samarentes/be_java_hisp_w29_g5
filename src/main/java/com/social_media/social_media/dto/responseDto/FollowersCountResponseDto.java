package com.social_media.social_media.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FollowersCountResponseDto {
    private Long user_id;
    private String user_name;
    private Integer followers_count;
}
