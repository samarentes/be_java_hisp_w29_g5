package com.social_media.social_media.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowingResponseDto {
    private Long user_id;
    private Long userIdToFollow;
}
