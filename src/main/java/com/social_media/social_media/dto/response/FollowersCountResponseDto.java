package com.social_media.social_media.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class FollowersCountResponseDto {
    private Long user_id;
    private String user_name;
    private Integer followers_count;
}
