package com.social_media.social_media.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Long userId;
    private Date date;
    private Product product;
    private Integer category;
    private Double price;

}
