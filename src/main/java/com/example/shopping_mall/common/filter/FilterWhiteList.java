package com.example.shopping_mall.common.filter;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class FilterWhiteList {

    private static final List<String> whiteList = List.of("/api/auth/log-in","/api/auth/sign-up");

    public List<String> getWhiteList() {
        return whiteList; // whiteList 반환(로그인없이 접근 가능)
    }

}
