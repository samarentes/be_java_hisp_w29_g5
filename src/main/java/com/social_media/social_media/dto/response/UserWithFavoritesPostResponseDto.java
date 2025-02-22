package com.social_media.social_media.dto.response;

import com.social_media.social_media.entity.Post;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserWithFavoritesPostResponseDto {
    private Long user_id;
    private String user_name;
    private List<Post> favorite_posts;
}