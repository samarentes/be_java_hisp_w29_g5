package com.social_media.social_media.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseWithIdDto {
    private Long user_id;
    private Long post_id;
    private LocalDate date;
    private ProductResponseDto product;
    private Integer category;
    private Double price;
}