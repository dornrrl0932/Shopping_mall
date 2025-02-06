package com.example.shopping_mall.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse <T> {

    private String status;
    private String message;
    private T data;

}
