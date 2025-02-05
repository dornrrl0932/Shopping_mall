package com.example.shopping_mall.shoppingMall.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ShoppingMallDto {

    private Long shoppingMallId;
    private String businessName;
    private String storeName;
    private int overall_rating;
    private String domainName;
    private String phoneNumber;
    private String operatorEmail;
    private String business_status;
    private String firstReportDate;
    private String monitoringDate;

}
