package com.example.shopping_mall.shoppingMall.service;

import com.example.shopping_mall.entity.shoppingMall.ShoppingMall;
import com.example.shopping_mall.shoppingMall.dto.ShoppingMallCursorInquiryResponseDto;
import com.example.shopping_mall.shoppingMall.dto.ShoppingMallDto;
import com.example.shopping_mall.shoppingMall.repository.ShoppingMallQueryRepository;
import com.example.shopping_mall.shoppingMall.repository.ShoppingMallRepository;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingMallService {

    private final ShoppingMallRepository shoppingMallRepository;
    private final ShoppingMallQueryRepository shoppingMallQueryRepository;

    // 전체평가 점수 조회 + 업소상태 조회
    public List<ShoppingMallDto> shoppingMallSummary(
            Integer overallRating,
            String businessStatus
    ) {

    private static final String CSV_FILE_PATH = "C:\\Users\\bette\\Downloads\\서울시 인터넷 쇼핑몰 현황.csv";  // 실제 파일 경로로 변경
        // 전체평점과 업소상태로 쇼핑몰 리스트 조회
        List<ShoppingMall> shoppingMallList
                = shoppingMallRepository.findAllByOverallRatingAndBusinessStatusOrderByMonitoringDateDesc(overallRating, businessStatus);

        List<ShoppingMallDto> shoppingMallDtos = shoppingMallList
                .stream()
                .map(ShoppingMallDto::convertToDto)
                .toList();

    public void insertShoppingMallFromCSV() {
        try (Reader reader = new FileReader(CSV_FILE_PATH)) {
            CsvToBean<ShoppingMallDto> csvToBean = new CsvToBeanBuilder<ShoppingMallDto>(reader)
                    .withType(ShoppingMallDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<ShoppingMallDto> csvDataList = csvToBean.parse();

            // DTO -> 엔티티 변환 후 저장
            List<ShoppingMall> entities = csvDataList.stream()
                    .map(dto -> new ShoppingMall(
                            null,  // ID는 자동 생성
                            dto.getBusinessName(),
                            dto.getStoreName(),
                            dto.getDomainName(),
                            dto.getPhoneNumber(),
                            dto.getOperatorEmail(),
                            dto.getECommerceLicense(),
                            dto.getBusinessType(),
                            dto.getFirstReportDate(),  // 날짜 변환
                            dto.getCompanyAddress(),
                            dto.getBusinessStatus(),
                            dto.getOverallRating(),
                            dto.getBusinessInfoRating(),
                            dto.getWithdrawalPolicyRating(),
                            dto.getPaymentMethodRating(),
                            dto.getTermsOfServiceRating(),
                            dto.getPrivacySecurityRating(),
                            dto.getMainProducts(),
                            dto.getWithdrawalPossible(),
                            dto.getRequiredHomepageInfo(),
                            dto.getPaymentMethods(),
                            dto.getTermsCompliance(),
                            dto.getPrivacyPolicy(),
                            dto.getExtraPersonalInfoRequest(),
                            dto.getPurchaseSafetyService(),
                            dto.getSecurityServer(),
                            dto.getCertificationMarks(),
                            dto.getEstimatedDeliveryDisplay(),
                            dto.getWithdrawalShippingFee(),
                            dto.getCustomerComplaintBoard(),
                            dto.getMembershipCancellation(),
                            dto.getSiteEstablishmentYear(),
                            dto.getMonitoringDate()  // 날짜 변환
                    ))
                    .collect(Collectors.toList());
    // 전체평가 점수 조회 + 업소상태 조회(페이지네이션 적용)
    public List<ShoppingMallDto> pageShoppingMallSummary(
            Pageable pageable,
            Integer overallRating,
            String businessStatus
    ) {

        //페이징 처리 된 쇼핑몰 데이터 조회
        Page<ShoppingMall> shoppingMallPage = shoppingMallRepository.findAllByOverallRatingAndBusinessStatusOrderByMonitoringDateDesc(overallRating, businessStatus, pageable);

        //ShoppingMall -> ShoppingMallDto로 변환
        List<ShoppingMallDto> shoppingMallDtos = shoppingMallPage.stream()
                .map(ShoppingMallDto::convertToDto)
                .toList();

        return shoppingMallDtos;
    }


    // 전체평가 점수 조회 + 업소상태 조회(커서 기반 페이지네이션 적용)
    public ShoppingMallCursorInquiryResponseDto ShoppingMallRatingAndStatusInquiry(
            Integer overallRating,
            String businessStatus,
            LocalDate cursor,
            int size) {

        //QueryDSL로 데이터 조회
        List<ShoppingMall> shoppingMallList = shoppingMallQueryRepository.findShoppingMallByRatingAndStatusWithCursor(overallRating, businessStatus, cursor, size);

        //ShoppingMall -> ShoppingMallDto로 변환
//        List<ShoppingMallDto> shoppingMallDtos = convertToDtoList(shoppingMallList);

        List<ShoppingMallDto> shoppingMallDtos = shoppingMallList
                .stream()
                .map(ShoppingMallDto::convertToDto)
                .toList();

        //페이지의 마지막 모니터링 날짜
        LocalDate lastMonitoringDate = shoppingMallList.get(shoppingMallList.size() - 1).getMonitoringDate();

        return new ShoppingMallCursorInquiryResponseDto(shoppingMallDtos, lastMonitoringDate);
    }
}

