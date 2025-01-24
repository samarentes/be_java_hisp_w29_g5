package com.social_media.social_media.service.stock;

import com.social_media.social_media.dto.request.StockRequestDto;
import com.social_media.social_media.dto.responseDto.StockResponseDto;

public interface IStockService {
    public StockResponseDto createStock(Long postId, StockRequestDto stockRequestDto);
}
