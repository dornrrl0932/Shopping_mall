package com.example.shopping_mall.common.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.shopping_mall.common.jwt.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TokenParsingFilter implements Filter { //servlet filter import하기!

    // 속성
    private final JwtUtil jwtUtil;
    private final FilterWhiteList filterWhiteList;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 요청 헤더로 토큰 받기
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest; //ServletRequest는 부모 클래스로, Http에 특화된 기능이 없기 때문에 다운캐스팅하여 HttpServletRequest의 기능을 사용
        String token = httpServletRequest.getHeader("Authorization");

        String requestURI = httpServletRequest.getRequestURI(); // 요청 URI
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse; // 응답 객체

        // whiteList 가져오기
        List<String> whiteList = filterWhiteList.getWhiteList();

        //WhiteList에 있는 경로는 필터 건너뜀
        if (whiteList.contains(requestURI)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        try {

            try {
                // 토큰 해독
                DecodedJWT decodedJWT = jwtUtil.decryptToken(token);

//                // 만료된 토큰인지 확인
//                if (decodedJWT.getExpiresAt().before(new Date())) {
//                    throw new RuntimeException("토큰이 만료되었습니다. 다시 로그인 해주세요.");
//                }

                //ID 추출
                Long userId = jwtUtil.extractUserIdFromToken(decodedJWT);

                // 요청 객체 속성에 userId를 추가
                httpServletRequest.setAttribute("userId", userId);

                // TokenExpiredException 발생 예외 처리(토큰 만료와 관련된 예외를 명시적으로 처리)
            } catch (TokenExpiredException e) {
                httpServletResponse.setStatus(httpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.setContentType("text/html; charset=UTF-8"); //콘텐츠타입(응답타입) 설정 + 문자 인코딩
                httpServletResponse.setCharacterEncoding("UTF-8"); // 문자 인코딩(이중설정)
                httpServletResponse.getWriter().write("토큰이 만료되었습니다. 다시 로그인 해주세요.");
                return;
            }

            // SignatureVerificationException 발생 예외 처리
        } catch (SignatureVerificationException e) {
            httpServletResponse.setStatus(httpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.setContentType("text/html; charset=UTF-8"); //콘텐츠타입 설정 + 문자 인코딩
            httpServletResponse.setCharacterEncoding("UTF-8"); // 문자 인코딩
            httpServletResponse.getWriter().write("유효하지 않은 토큰입니다. 토큰을 확인해주세요.");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
