package com.example.shopping_mall.shoppingMall.service;

import com.example.shopping_mall.entity.shoppingMall.ShoppingMall;
import com.example.shopping_mall.shoppingMall.dto.ShoppingMallDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingMallService {

    private final ShoppingMallRepository shoppingMallRepository;
//
//    //전체평가 조회
//    public List<ShoppingMallDto> overallRatingInquiry(int overallRating) {
//
//        //입력받은 별점 확인
//        if (0 > overallRating || overallRating > 3) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "평가 별점은 최소 0점, 최대 3점입니다.");
//        }
//
//        //ShoppingMall 조회
//        List<ShoppingMall> shoppingMallList = shoppingMallRepository.findAllByOverallRating(
//                overallRating);
//
//        // ShoppingMall -> ShoppingMallDto로 변환
//        List<ShoppingMallDto> shoppingMallDtos = shoppingMallList.stream().map(shoppingMall ->
//                        new ShoppingMallDto(
//                                shoppingMall.getShoppingMallId(),
//                                shoppingMall.getBusinessName(),
//                                shoppingMall.getStoreName(),
//                                shoppingMall.getOverallRating(),
//                                shoppingMall.getDomainName(),
//                                shoppingMall.getPhoneNumber(),
//                                shoppingMall.getOperatorEmail(),
//                                shoppingMall.getBusinessStatus(),
//                                shoppingMall.getFirstReportDate(),
//                                shoppingMall.getMonitoringDate()))
//                .collect(Collectors.toList());
//
//        return shoppingMallDtos;
//    }
//
//    //업소 상태 조회
//    public List<ShoppingMallDto> businessStatusInquiry(String businessStatus) {
//
//        List<ShoppingMall> shoppingMallList = shoppingMallRepository.findAllByBusinessStatusOrThrowException(
//                businessStatus);
//
//        // ShoppingMall -> ShoppingMallDto로 변환
//        List<ShoppingMallDto> shoppingMallDtos = shoppingMallList.stream().map(shoppingMall ->
//                        new ShoppingMallDto(
//                                shoppingMall.getShoppingMallId(),
//                                shoppingMall.getBusinessName(),
//                                shoppingMall.getStoreName(),
//                                shoppingMall.getOverallRating(),
//                                shoppingMall.getDomainName(),
//                                shoppingMall.getPhoneNumber(),
//                                shoppingMall.getOperatorEmail(),
//                                shoppingMall.getBusinessStatus(),
//                                shoppingMall.getFirstReportDate(),
//                                shoppingMall.getMonitoringDate()))
//                .collect(Collectors.toList());
//
//        return shoppingMallDtos;
//    }
//
//    //모니터링 날짜 내림차순 조회
//    public List<ShoppingMallDto> monitoringDateDescendingOrder() {
//
//        List<ShoppingMall> shoppingMallList = shoppingMallRepository.findAllByOrderByMonitoringDateDesc();
//
//        // ShoppingMall -> ShoppingMallDto로 변환
//        List<ShoppingMallDto> shoppingMallDtos = shoppingMallList.stream().map(shoppingMall ->
//                        new ShoppingMallDto(
//                                shoppingMall.getShoppingMallId(),
//                                shoppingMall.getBusinessName(),
//                                shoppingMall.getStoreName(),
//                                shoppingMall.getOverallRating(),
//                                shoppingMall.getDomainName(),
//                                shoppingMall.getPhoneNumber(),
//                                shoppingMall.getOperatorEmail(),
//                                shoppingMall.getBusinessStatus(),
//                                shoppingMall.getFirstReportDate(),
//                                shoppingMall.getMonitoringDate()))
//                .collect(Collectors.toList());
//
//        return shoppingMallDtos;
//    }

    private static final String CSV_FILE_PATH = "C:\\Users\\bette\\Downloads\\서울시 인터넷 쇼핑몰 현황.csv";  // 실제 파일 경로로 변경

//    public void insertShoppingMallFromCSV() {
//        try (Reader reader = new FileReader(CSV_FILE_PATH)) {
//            CsvToBean<ShoppingMall> csvToBean = new CsvToBeanBuilder<ShoppingMall>(reader)
//                    .withType(ShoppingMall.class)
//                    .withIgnoreLeadingWhiteSpace(true)
//                    .build();
//
//            List<ShoppingMall> shoppingMallList = csvToBean.parse();
//
////            // DTO를 엔티티로 변환 후 저장
////            List<ShoppingMall> entities = shoppingMallList.stream()
////                    .map(dto -> new ShoppingMall(
////                            dto.getBusinessName(),
////                            dto.getStoreName(),
////                            dto.getDomainName(),
////                            dto.getPhoneNumber(),
////                            dto.getOperatorEmail(),
////                            dto.getBusinessStatus(),
////                            dto.getOverallRating(),
////                            dto.getFirstReportDate(),
////                            dto.getMonitoringDate()
////                    ))
////                    .collect(Collectors.toList());
//
//            shoppingMallRepository.saveAll(shoppingMallList);  // 한 번에 저장하여 성능 향상
//
//        } catch (IOException e) {
//            log.error("CSV 파일을 읽는 중 오류 발생", e);
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "CSV 파일을 읽는 중 오류 발생");
//        }
//    }

//    public void insertShoppingMallFromCSV() {
//        try (Reader reader = new FileReader(CSV_FILE_PATH)) {
//            CsvToBean<ShoppingMall> csvToBean = new CsvToBeanBuilder<ShoppingMall>(reader)
//                    .withType(ShoppingMall.class)
//                    .withIgnoreLeadingWhiteSpace(true)
//                    .build();
//
//            List<ShoppingMall> shoppingMallList = csvToBean.parse();
//
//            for (ShoppingMall mall : shoppingMallList) {
//                System.out.println(mall.getField2());
//            }
//
//            // 데이터를 100개씩 나누어 저장
//            ListUtils.partition(shoppingMallList, 100).forEach(shoppingMallRepository::saveAll);
//
//        } catch (IOException e) {
//            log.error("CSV 파일을 읽는 중 오류 발생", e);
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "CSV 파일을 읽는 중 오류 발생");
//
//        }
//
//    }
//
//
//    public List<ShoppingMall> readCsvFile() {
//        List<ShoppingMall> shoppingMallList = new ArrayList<>();
//
//        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(CSV_FILE_PATH), StandardCharsets.UTF_8))) {
//            String[] line;
//
//            reader.readNext();
//
//            while ((line = reader.readNext()) != null) {
//                String store_name = line[0];
//                String field2 = line[1];
//                String field3 = line[2];
//                String field4 = line[3];
//
//                shoppingMallList.add(new ShoppingMall(store_name, field2, field3, field4));
//            }
//        } catch (IOException | CsvValidationException e) {
//            e.printStackTrace();
//        }
//
//        return shoppingMallList;
//    }

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

            shoppingMallRepository.saveAll(entities);

        } catch (IOException e) {
            log.error("CSV 파일을 읽는 중 오류 발생", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "CSV 파일을 읽는 중 오류 발생");
        }
    }

    // ✅ 문자열 날짜를 LocalDate로 변환하는 메서드
    private LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            log.warn("잘못된 날짜 형식: " + dateString);
            return null;  // 오류 발생 시 null 저장
        }
    }
}


