package com.example.shopping_mall.common.config;

import com.example.shopping_mall.common.filter.FilterWhiteList;
import com.example.shopping_mall.common.filter.LoginCheckFilter;
import com.example.shopping_mall.common.filter.TokenParsingFilter;
import com.example.shopping_mall.common.jwt.JwtUtil;
import jakarta.servlet.Filter;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 이 클래스가 스프링 컨테이너에 의해 설정클래스로 인식되도록 지정
@AllArgsConstructor
public class WebConfig {

    private JwtUtil jwtUtil;
    private FilterWhiteList filterWhiteList;

    @Bean // 빈으로 등록
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>(); // FilterRegistrationBean 객체를 생성하고
        filterFilterRegistrationBean.setFilter(new LoginCheckFilter(filterWhiteList)); // 생성한 FilterRegistrationBean에 LoginCheckFilter를 인스턴스 등록
        filterFilterRegistrationBean.setOrder(1); // 필터의 실행 순서를 1로 지정
        filterFilterRegistrationBean.addUrlPatterns("/*"); // 모든 URL에 이 필터가 적용되도록 설정

        return filterFilterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean tokenParsingFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new TokenParsingFilter(jwtUtil, filterWhiteList));
        filterFilterRegistrationBean.setOrder(2);
        filterFilterRegistrationBean.addUrlPatterns("/*");

        return filterFilterRegistrationBean;
    }
}