package com.example.shopping_mall.shoppingMall.repository;

import com.example.shopping_mall.entity.shoppingMall.QShoppingMall;
import com.example.shopping_mall.entity.shoppingMall.ShoppingMall;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShoppingMallQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ShoppingMall> findShoppingMallByRatingAndStatusWithCursor(Integer rating, String status, LocalDate cursor, int size) {
        QShoppingMall shoppingMall = QShoppingMall.shoppingMall;

        // 동적 조건을 만들 떄 사용하는 QueryDSL
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 기본 필터 조건(전체 평점, 업소 상태) , 조건을 and로 추가
        if (rating != null) {
            booleanBuilder.and(shoppingMall.overallRating.eq(rating)); //eq = 같다, 특정 전체 평점과 같은 데이터 조회
        }

        if (status != null) {
            booleanBuilder.and(shoppingMall.businessStatus.eq(status)); // 특정 업소 상태와 같은 데이터 조회
        }

        //만약 커서 값이 있다면 모니터링 날짜보다 이전(더 작은)데이터를 조회
        if (cursor != null) {
            booleanBuilder.and(shoppingMall.monitoringDate.lt(cursor));
        }

        return jpaQueryFactory //QueryDSL을 이용해서 쿼리를 생성하는 객체
                .selectFrom(shoppingMall) //shoppingMall테이블에서 데이터를 가져옴
                .where(booleanBuilder) // 동적으로 생성 된 조건을 적용
                .orderBy(shoppingMall.monitoringDate.desc()) // 정렬기준
                .limit(size) // 10개 만큼 결과 조회
                .fetch(); // 쿼리를 실행해서 결과 리스트를 가져오기
    }
}