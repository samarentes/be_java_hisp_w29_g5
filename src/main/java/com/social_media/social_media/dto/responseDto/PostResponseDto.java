package com.social_media.social_media.dto.responseDto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private Long user_id;
    private Date date;
    private ProductResponseDto product;
    private Integer category;
    private Double price;
}
