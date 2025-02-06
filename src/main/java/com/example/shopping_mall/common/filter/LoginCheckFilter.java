package com.example.shopping_mall.common.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckFilter implements Filter {

    private final FilterWhiteList filterWhiteList;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 요청 헤더로 토큰 받기
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = httpServletRequest.getHeader("Authorization");

        String requestURI = httpServletRequest.getRequestURI(); // 요청 URI
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse; // 응답 객체

        // whiteList 가져오기
        List<String> whiteList = filterWhiteList.getWhiteList();

        //WhiteList에 있는 경로는 필터 건너뜀
        log.info("필터 적용 경로 → " + "[" + "{}" + "]", requestURI); // whiteList 경로인지 확인
        if(whiteList.contains(requestURI)) { //contains -> 주어진 요소가 리스트 등에 포함되어 있는지 확인하는 메서드(있으면 ture)
            log.info("필터가 적용되지 않았습니다.");
            filterChain.doFilter(servletRequest,servletResponse);
            return;

        } else {
            log.info("필터가 적용되었습니다.");
            // 토큰 검증
            if (token == null) {
                httpServletResponse.setStatus(httpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.setContentType("text/html; charset=UTF-8"); //콘텐츠타입(응답타입) 설정 + 문자 인코딩
                httpServletResponse.setCharacterEncoding("UTF-8"); // 문자 인코딩(이중설정)
                httpServletResponse.getWriter().write("Authorization 헤더를 확인해주세요.");
                return;
            }
        }

        // 다음 필터로 전달
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
