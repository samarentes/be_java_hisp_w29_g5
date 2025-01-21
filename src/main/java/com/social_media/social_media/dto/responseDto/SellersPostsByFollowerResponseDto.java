package com.social_media.social_media.dto.responseDto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellersPostsByFollowerResponseDto {
    private Long user_id;
    private List<PostResponseDto> posts;
}
