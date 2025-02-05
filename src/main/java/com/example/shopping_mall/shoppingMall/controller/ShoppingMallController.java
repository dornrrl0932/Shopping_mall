package com.example.shopping_mall.shoppingMall.controller;

import com.example.shopping_mall.common.dto.ApiResponse;
import com.example.shopping_mall.shoppingMall.dto.ShoppingMallDto;
import com.example.shopping_mall.shoppingMall.service.ShoppingMallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-mall")
@RequiredArgsConstructor
public class ShoppingMallController {

    private final ShoppingMallService shoppingMallService;

    // 전체평가 점수 조회(점수를 입력하여 해당 업체 리스트만 조회)
    @GetMapping("/overall-rating")
    public ResponseEntity<ApiResponse<List<ShoppingMallDto>>> overallRatingInquiryAPI (@RequestParam("overall_rating") int overallRating) {

        List<ShoppingMallDto> shoppingMallDtos = shoppingMallService.overallRatingInquiry(overallRating);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), "쇼핑몰 조회 성공", shoppingMallDtos));
    }

    // 업소 상태 조회 (업소 상태 중 1가지를 선택하여 해당 업체 리스트만 조회)
    @GetMapping("/business-status")
    public ResponseEntity<ApiResponse<List<ShoppingMallDto>>> businessStatusInquiryAPI (@RequestParam("business_status") String businessStatus) {

        List<ShoppingMallDto> shoppingMallDtos = shoppingMallService.businessStatusInquiry(businessStatus);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), "쇼핑몰 조회 성공",shoppingMallDtos));
    }

    // 모니터링 날짜 내림차순 조회(최신날짜 -> 과거날짜)
    @GetMapping("/monitoring-date")
    public ResponseEntity<ApiResponse<List<ShoppingMallDto>>> monitoringDateDescendingOrderAPI() {

        List<ShoppingMallDto> shoppingMallDtos = shoppingMallService.monitoringDateDescendingOrder();

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), "쇼핑몰 조회 성공",shoppingMallDtos));
    }

    @PostMapping("/collection")
    public ResponseEntity<ApiResponse<String>> insertShoppingMallFromCSV() {

        shoppingMallService.insertShoppingMallFromCSV();

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), "CSV 데이터 저장 완료", "success"));
    }

}
