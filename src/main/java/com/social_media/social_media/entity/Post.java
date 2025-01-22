package com.social_media.social_media.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Long postId;
    private Long userId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Product product;
    private Integer category;
    private Double price;
}
