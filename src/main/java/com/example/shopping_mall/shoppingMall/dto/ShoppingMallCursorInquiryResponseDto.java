package com.example.shopping_mall.shoppingMall.dto;

import java.time.LocalDate;
import java.util.List;

public record ShoppingMallCursorInquiryResponseDto(
        List<ShoppingMallDto> shoppingMallList,
        LocalDate nextCursor
) {

//    private List<ShoppingMallDto> shoppingMallList;
//    private LocalDate nextCursor;
}
