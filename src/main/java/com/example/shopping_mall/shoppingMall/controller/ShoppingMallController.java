package com.example.shopping_mall.shoppingMall.controller;

import com.example.shopping_mall.common.dto.ApiResponse;
import com.example.shopping_mall.shoppingMall.dto.ShoppingMallCursorInquiryResponseDto;
import com.example.shopping_mall.shoppingMall.dto.ShoppingMallDto;
import com.example.shopping_mall.shoppingMall.service.ShoppingMallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shopping-mall")
@RequiredArgsConstructor
public class ShoppingMallController {

    private final ShoppingMallService shoppingMallService;

    // 전체평가 점수 조회 + 업소상태 조회
    @GetMapping("/status-ratings")
    public ResponseEntity<ApiResponse<List<ShoppingMallDto>>> shoppingMallSummaryAPI(
            @RequestParam(value = "rating", required = false) Integer overallRating, // required = false -> 해당 파라미터는 선택사항
            @RequestParam(value = "status", required = false) String businessStatus) {

        List<ShoppingMallDto> shoppingMallDtos = shoppingMallService.shoppingMallSummary(overallRating, businessStatus);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), "쇼핑몰 조회 성공", shoppingMallDtos));
    }

    // 전체평가 점수 조회 + 업소상태 조회(페이지네이션 적용)
    @GetMapping("/status-ratings/page/{page}")
    public ResponseEntity<ApiResponse<List<ShoppingMallDto>>> pageShoppingMallSummaryAPI(
            @PathVariable(value = "page") int page,
            @RequestParam(value = "rating", required = false) Integer overallRating, // required = false -> 해당 파라미터는 선택사항
            @RequestParam(value = "status", required = false) String businessStatus) {

        Pageable pageable = PageRequest.of(page - 1, 10);

        List<ShoppingMallDto> shoppingMallDtos = shoppingMallService.pageShoppingMallSummary(pageable, overallRating, businessStatus);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), "쇼핑몰 조회 성공", shoppingMallDtos));
    }


    // 전체평가 점수 조회 + 업소상태 조회(커서 기반 페이지네이션 적용)
    @GetMapping("/status-ratings/cursor-page")
    public ResponseEntity<ApiResponse<ShoppingMallCursorInquiryResponseDto>> ShoppingMallRatingAndStatusInquiryAPI(
            @RequestParam(value = "rating", required = false) Integer overallRating, //null값 방지 integer
            @RequestParam(value = "status", required = false) String businessStatus,
            @RequestParam(value = "cursor", required = false) LocalDate cursor,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size
    ) { //size 입력 안할 시 기본 값 10

        ShoppingMallCursorInquiryResponseDto shoppingMallDtos = shoppingMallService.ShoppingMallRatingAndStatusInquiry(overallRating, businessStatus, cursor, size);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), "쇼핑몰 조회 성공", shoppingMallDtos));
    }

    @PostMapping("/collection")
    public ResponseEntity<ApiResponse<String>> insertShoppingMallFromCSV() {

        shoppingMallService.readCsv();

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.toString(), "CSV 데이터 저장 완료", "success"));
    }
}
