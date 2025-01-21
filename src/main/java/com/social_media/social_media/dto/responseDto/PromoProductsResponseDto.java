package com.social_media.social_media.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoProductsResponseDto {
    private Long user_id;
    private String user_name;
    private Integer promo_products_count;
}
