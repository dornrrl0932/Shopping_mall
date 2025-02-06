package com.example.shopping_mall.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private static final String SECRET = "hellobello-cat";
    private static final String BEARER_PREFIX = "Bearer";

    // 토큰 추출
    public String createToken(Long userId) throws UnsupportedEncodingException {

        //알고리즘 설정 -> 이 방식으로 암호화
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        //토큰 생성
        String token = JWT.create()
                .withIssuer("localhost:8080")
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30))) // 유효시간 30분
                .sign(algorithm);

        // 토큰 반환
        return token;
    }

    // 토큰 해독
    public DecodedJWT decryptToken(String bearerToken) throws UnsupportedEncodingException {

        // 알고리즘 설정
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        // 토큰 추출
        String token = bearerToken.substring(BEARER_PREFIX.length()).trim();

        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer("localhost:8080")
                .build() // JWT.require()로 설정한 조건들을 토대로 검증할 준비를 끝낸다
                .verify(token);

        return decodedJWT;
    }

    // 해독한 토큰에서 userId 추출
    public Long extractUserIdFromToken (DecodedJWT decodedJWT) {

        String userId = decodedJWT.getSubject();

        return Long.parseLong(userId); // getSubject 메서드가 Stirng으로 반환되기 때문에 Long 타입으로 변환
    }
}
