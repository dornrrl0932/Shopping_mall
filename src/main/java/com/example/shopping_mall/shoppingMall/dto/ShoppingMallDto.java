package com.example.shopping_mall.shoppingMall.dto;

import com.example.shopping_mall.entity.shoppingMall.ShoppingMall;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShoppingMallDto {

//    private Long shoppingMallId;
//    private String businessName;
//    private String storeName;
//    private int overall_rating;
//    private String domainName;
//    private String phoneNumber;
//    private String operatorEmail;
//    private String business_status;
//    private String firstReportDate;
//    private String monitoringDate;
@CsvBindByName(column = "business_name")
private String businessName;

    @CsvBindByName(column = "store_name")
    private String storeName;

    @CsvBindByName(column = "domain_name")
    private String domainName;

    @CsvBindByName(column = "phone_number")
    private String phoneNumber;

    @CsvBindByName(column = "operator_email")
    private String operatorEmail;

    @CsvBindByName(column = "e_commerce_license")
    private String eCommerceLicense;

    @CsvBindByName(column = "business_type")
    private String businessType;

    @CsvBindByName(column = "first_report_date")
    private String firstReportDate;

    @CsvBindByName(column = "company_address")
    private String companyAddress;

    @CsvBindByName(column = "business_status")
    private String businessStatus;

    @CsvBindByName(column = "overall_rating")
    private int overallRating;

    @CsvBindByName(column = "business_info_rating")
    private int businessInfoRating;

    @CsvBindByName(column = "withdrawal_policy_rating")
    private int withdrawalPolicyRating;

    @CsvBindByName(column = "payment_method_rating")
    private int paymentMethodRating;

    @CsvBindByName(column = "terms_of_service_rating")
    private int termsOfServiceRating;

    @CsvBindByName(column = "privacy_security_rating")
    private int privacySecurityRating;

    @CsvBindByName(column = "main_products")
    private String mainProducts;

    @CsvBindByName(column = "withdrawal_possible")
    private String withdrawalPossible;

    @CsvBindByName(column = "required_homepage_info")
    private String requiredHomepageInfo;

    @CsvBindByName(column = "payment_methods")
    private String paymentMethods;

    @CsvBindByName(column = "terms_compliance")
    private String termsCompliance;

    @CsvBindByName(column = "privacy_policy")
    private String privacyPolicy;

    @CsvBindByName(column = "extra_personal_info_request")
    private String extraPersonalInfoRequest;

    @CsvBindByName(column = "purchase_safety_service")
    private String purchaseSafetyService;

    @CsvBindByName(column = "security_server")
    private String securityServer;

    @CsvBindByName(column = "certification_marks")
    private String certificationMarks;

    @CsvBindByName(column = "estimated_delivery_display")
    private String estimatedDeliveryDisplay;

    @CsvBindByName(column = "withdrawal_shipping_fee")
    private String withdrawalShippingFee;

    @CsvBindByName(column = "customer_complaint_board")
    private String customerComplaintBoard;

    @CsvBindByName(column = "membership_cancellation")
    private String membershipCancellation;

    @CsvBindByName(column = "site_establishment_year")
    private String siteEstablishmentYear;

    @CsvBindByName(column = "monitoring_date")
    private String monitoringDate;

public record ShoppingMallDto(
        Long shoppingMallId,
        String businessName,
        String storeName,
        int overall_rating,
        String domainName,
        String phoneNumber,
        String operatorEmail,
        String business_status,
        LocalDate firstReportDate,
        LocalDate monitoringDate
) {
    public static ShoppingMallDto convertToDto(ShoppingMall shoppingMall) {
        return new ShoppingMallDto(
                shoppingMall.getShoppingMallId(),
                shoppingMall.getBusinessName(),
                shoppingMall.getStoreName(),
                shoppingMall.getOverallRating(),
                shoppingMall.getDomainName(),
                shoppingMall.getPhoneNumber(),
                shoppingMall.getOperatorEmail(),
                shoppingMall.getBusinessStatus(),
                shoppingMall.getFirstReportDate(),
                shoppingMall.getMonitoringDate()
        );
    }
}
