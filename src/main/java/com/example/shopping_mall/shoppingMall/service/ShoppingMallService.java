package com.example.shopping_mall.shoppingMall.service;

import com.example.shopping_mall.entity.shoppingMall.ShoppingMall;
import com.example.shopping_mall.shoppingMall.dto.ShoppingMallCursorInquiryResponseDto;
import com.example.shopping_mall.shoppingMall.dto.ShoppingMallDto;
import com.example.shopping_mall.shoppingMall.repository.ShoppingMallQueryRepository;
import com.example.shopping_mall.shoppingMall.repository.ShoppingMallRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

        // 전체평점과 업소상태로 쇼핑몰 리스트 조회
        List<ShoppingMall> shoppingMallList
                = shoppingMallRepository.findAllByOverallRatingAndBusinessStatusOrderByMonitoringDateDesc(overallRating, businessStatus);

        List<ShoppingMallDto> shoppingMallDtos = shoppingMallList
                .stream()
                .map(ShoppingMallDto::convertToDto)
                .toList();

        return shoppingMallDtos;
    }


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


    private static final int BATCH_SIZE = 100;

    @Transactional
    public void readCsv() {
        List<List<String>> records = new ArrayList<>();
        List<ShoppingMall> shopSave = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("/Users/qlgh2gh/Desktop/ internet.csv"))){
            String line; //라인 변수
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null){
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");

                List<String> informationList = Arrays.asList(values);

                ShoppingMall shop = new ShoppingMall();

                String businessName = informationList.get(0);
                String storeName = informationList.get(1);
                String domainName = informationList.get(2);
                String phoneNumber = informationList.get(3);
                String operatorEmail = informationList.get(4);
                String eCommerceLicense = informationList.get(5);
                String businessType = informationList.get(6);
                LocalDate firstReportDate = firstReportDate(informationList.get(7));
                String companyAddress = informationList.get(8);
                String businessStatus = informationList.get(9);

//                int overallRating = Integer.parseInt(informationList.get(10));

                int overallRating = 0;
                int businessInfoRating = 0;
                int withdrawalPolicyRating = 0;
                int paymentMethodRating = 0;
                int termsOfServiceRating = 0;
                int privacySecurityRating = 0;

                try {
                    overallRating = Integer.parseInt(informationList.get(10));
                } catch (NumberFormatException e){
                    overallRating = 0;
                }

//                int businessInfoRating = Integer.parseInt(informationList.get(11));

                try {
                    businessInfoRating = Integer.parseInt(informationList.get(11));
                } catch (NumberFormatException e){
                    businessInfoRating = 0;
                }

//                int withdrawalPolicyRating = Integer.parseInt(informationList.get(12));
                try {
                    withdrawalPolicyRating = Integer.parseInt(informationList.get(12));
                } catch (NumberFormatException e){
                    withdrawalPolicyRating = 0;
                }
//                int paymentMethodRating = Integer.parseInt(informationList.get(13));

                try {
                    paymentMethodRating = Integer.parseInt(informationList.get(13));
                } catch (NumberFormatException e){
                    paymentMethodRating = 0;
                }
//                int termsOfServiceRating = Integer.parseInt(informationList.get(14));

                try {
                    termsOfServiceRating = Integer.parseInt(informationList.get(14));
                } catch (NumberFormatException e){
                    termsOfServiceRating = 0;
                }

//                int privacySecurityRating = Integer.parseInt(informationList.get(15));

                try {
                    privacySecurityRating = Integer.parseInt(informationList.get(15));
                } catch (NumberFormatException e){
                    privacySecurityRating = 0;
                }


                String mainProducts = informationList.get(16);

                String withdrawalPossible = informationList.get(17);
//                if (withdrawalPossible != null && withdrawalPossible.length() > 255) {
//                    withdrawalPossible = withdrawalPossible.substring(0, 255);  // 255자까지만 자르기
//                }
                shop.setWithdrawalPossible(withdrawalPossible);
                String requiredHomepageInfo = informationList.get(18);
                String paymentMethods = informationList.get(19);
                String termsCompliance = informationList.get(20);
                String PrivacyPolicy = informationList.get(21);
                String extraPersonalInfoRequest = informationList.get(22);
                String purchaseSafetyService = informationList.get(23);
                String SecurityServer = informationList.get(24);
                String certificationMarks = informationList.get(25);
                String estimatedDeliveryDisplay = informationList.get(26);
                String withdrawalShippingFee = informationList.get(27);
                String customerComplaintBoard = informationList.get(28);
                String membershipCancellation = informationList.get(29);
                String siteEstablishmentYear = informationList.get(30);
                LocalDate monitoringDate = monitoringDate(informationList.get(31));

                shop.setBusinessName(businessName);
                shop.setStoreName(storeName);
                shop.setDomainName(domainName);
                shop.setPhoneNumber(phoneNumber);
                shop.setOperatorEmail(operatorEmail);
                shop.setECommerceLicense(eCommerceLicense);
                shop.setBusinessType(businessType);
                shop.setFirstReportDate(firstReportDate);
                shop.setCompanyAddress(companyAddress);
                shop.setBusinessStatus(businessStatus);
                shop.setOverallRating(overallRating);
                shop.setBusinessInfoRating(businessInfoRating);
                shop.setWithdrawalPolicyRating(withdrawalPolicyRating);
                shop.setPaymentMethodRating(paymentMethodRating);
                shop.setTermsOfServiceRating(termsOfServiceRating);
                shop.setPrivacySecurityRating(privacySecurityRating);
                shop.setMainProducts(mainProducts);
                shop.setWithdrawalPossible(withdrawalPossible);
                shop.setRequiredHomepageInfo(requiredHomepageInfo);
                shop.setPaymentMethods(paymentMethods);
                shop.setTermsCompliance(termsCompliance);
                shop.setPrivacyPolicy(PrivacyPolicy);
                shop.setExtraPersonalInfoRequest(extraPersonalInfoRequest);
                shop.setPurchaseSafetyService(purchaseSafetyService);
                shop.setSecurityServer(SecurityServer);
                shop.setCertificationMarks(certificationMarks);
                shop.setEstimatedDeliveryDisplay(estimatedDeliveryDisplay);
                shop.setWithdrawalShippingFee(withdrawalShippingFee);
                shop.setCustomerComplaintBoard(customerComplaintBoard);
                shop.setMembershipCancellation(membershipCancellation);
                shop.setSiteEstablishmentYear(siteEstablishmentYear);
                shop.setMonitoringDate(monitoringDate);

                shopSave.add(shop);

                if(shopSave.size() == BATCH_SIZE){
                    shoppingMallRepository.saveAll(shopSave);
                    shopSave.clear();
                }

            }
//            private static final int BATCH_SIZE = 100;
            if(!shopSave.isEmpty()){
                shoppingMallRepository.saveAll(shopSave);
            }








        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch(Exception e) {
            log.info("**********************");
            e.printStackTrace(); // 어떤 메서드에서 에러 발생했는지
            throw new RuntimeException(e);

        }


    }
    private LocalDate firstReportDate(String date){
        if (date == null || !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return null; // 날짜 형식이 아니면 null 반환
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // CSV 날짜 형식에 맞춰 변경
        return LocalDate.parse(date, formatter);
    }

    private LocalDate monitoringDate(String date){
        if (date == null || !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return null; // 날짜 형식이 아니면 null 반환
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // CSV 날짜 형식에 맞춰 변경
        return LocalDate.parse(date, formatter);
    }

}

