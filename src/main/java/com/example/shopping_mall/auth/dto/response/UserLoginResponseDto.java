package com.example.shopping_mall.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponseDto {

    private Long id;
    private String email;
    private String token;
}
