package com.example.shopping_mall.shoppingMall.repository;

import com.example.shopping_mall.entity.shoppingMall.ShoppingMall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface ShoppingMallRepository extends JpaRepository<ShoppingMall, Long> {
//
//    //전체 평가 쇼핑몰 리스트 조회
//    List<ShoppingMall> findAllByOverallRating(int overallRating);
//
//    //상태에 따른 쇼핑몰 리스트 조회
//    List<ShoppingMall> findAllByBusinessStatus(String businessStatus);
//
//    //상태에 따른 쇼핑몰 리스트 조회 후 리스트가 비어있으면 예외발생
//    default List<ShoppingMall> findAllByBusinessStatusOrThrowException(String businessStatus){
//
//        //쇼핑몰 리스트 조회
//        List<ShoppingMall> shoppingMallList = findAllByBusinessStatus(businessStatus);
//
//        //리스트 비어있는 경우 예외발생
//        if (shoppingMallList.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"해당 상태가 존재하지 않습니다.");
//        }
//
//        return shoppingMallList;
//    }
////
////    //모니터링 날짜를 기준으로 내림차순으로 쇼핑몰 리스트 조회
////    //OrderBy -> 정렬기준 설정(뒤에 오는 필드가 정렬기준이 됨), Desc -> 내림차순(만약 Asc을 사용했다면 오름차순으로 정렬)
////    List<ShoppingMall> findAllByOrderByMonitoringDateDesc();
////
}
