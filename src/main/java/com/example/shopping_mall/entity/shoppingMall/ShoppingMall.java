package com.example.shopping_mall.entity.shoppingMall;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "shopping_mall")
public class ShoppingMall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shoppingMallId;

    @Lob
    @Column(name = "business_name", columnDefinition = "LONGTEXT")
    @CsvBindByName(column = "business_name")
    private String businessName; // 상호

    @Lob
    @Column(name = "store_name", length = 50)
    @CsvBindByName(column = "store_name")
    private String storeName; // 쇼핑몰명

    @Lob
    @Column(name = "domain_name", nullable = false, columnDefinition = "LONGTEXT")
    @CsvBindByName(column = "domain_name")
    private String domainName; // 도메인명

    @Lob
    @Column(name = "phone_number", length = 50)
    @CsvBindByName(column = "phone_number")
    private String phoneNumber; // 전화번호

    @Lob
    @Column(name = "operator_email", length = 100)
    @CsvBindByName(column = "operator_email")
    private String operatorEmail; // 운영자이메일

    @Lob
    @Column(name = "e_commerce_license")
    @CsvBindByName(column = "e_commerce_license")
    private String eCommerceLicense; // 통신판매번호

    @Lob
    @Column(name = "business_type", length = 50)
    @CsvBindByName(column = "business_type")
    private String businessType; // 영업형태

    @Lob
    @Column(name = "first_report_date")
    @CsvBindByName(column = "first_report_date")
    private String firstReportDate; // 최초신고일자

    @Lob
    @Column(name = "company_address", length = 100)
    @CsvBindByName(column = "company_address")
    private String companyAddress; // 회사주소

    @Lob
    @Column(name = "business_status", length = 50)
    @CsvBindByName(column = "business_status")
    private String businessStatus; // 업소상태

    @Lob
    @Column(name = "overall_rating")
    @CsvBindByName(column = "overall_rating")
    private int overallRating; // 전체평가

    @Lob
    @Column(name = "business_info_rating")
    @CsvBindByName(column = "business_info_rating")
    private int businessInfoRating; // 사업자정보표시평가

    @Lob
    @Column(name = "withdrawal_policy_rating")
    @CsvBindByName(column = "withdrawal_policy_rating")
    private int withdrawalPolicyRating; // 청약철회평가

    @Lob
    @Column(name = "payment_method_rating ")
    @CsvBindByName(column = "payment_method_rating")
    private int paymentMethodRating; // 결재방법평가

    @Lob
    @Column(name = "terms_of_service_rating")
    @CsvBindByName(column = "terms_of_service_rating")
    private int termsOfServiceRating; // 이용약관평가

    @Lob
    @Column(name = "privacy_security_rating")
    @CsvBindByName(column = "privacy_security_rating")
    private int privacySecurityRating; // 개인정보보안평가

    @Lob
    @Column(name = "main_products", columnDefinition = "MEDIUMTEXT")
    @CsvBindByName(column = "main_products")
    private String mainProducts; // 주요취급품목

    @Lob
    @Column(name = "withdrawal_possible", length = 50)
    @CsvBindByName(column = "withdrawal_possible")
    private String withdrawalPossible; //청약철회가능여부

    @Lob
    @Column(name = "required_homepage_info", length = 100)
    @CsvBindByName(column = "required_homepage_info")
    private String requiredHomepageInfo; // 초기화면필수항목중표시사항

    @Lob
    @Column(name = "payment_methods", length = 100)
    @CsvBindByName(column = "payment_methods")
    private String paymentMethods; // 결제방법

    @Lob
    @Column(name = "terms_compliance", length = 100)
    @CsvBindByName(column = "terms_compliance")
    private String termsCompliance; // 이용약관준수정도

    @Lob
    @Column(name = "privacy_policy", length = 100)
    @CsvBindByName(column = "privacy_policy")
    private String PrivacyPolicy; // 개인정보취급방침

    @Lob
    @Column(name = "extra_personal_info_request", length = 50)
    @CsvBindByName(column = "extra_personal_info_request")
    private String extraPersonalInfoRequest; // 표준약관이상개인정보항목요구

    @Lob
    @Column(name = "purchase_safety_service", length = 50)
    @CsvBindByName(column = "purchase_safety_service")
    private String purchaseSafetyService; // 구매안전서비스

    @Lob
    @Column(name = "security_server", length = 50)
    @CsvBindByName(column = "security_server")
    private String SecurityServer; // 보안서버설치

    @Lob
    @Column(name = "certification_marks", length = 50)
    @CsvBindByName(column = "certification_marks")
    private String certificationMarks; // 인증마크

    @Lob
    @Column(name = "estimated_delivery_display", length = 50)
    @CsvBindByName(column = "estimated_delivery_display")
    private String estimatedDeliveryDisplay; // 배송예정일표시

    @Lob
    @Column(name = "withdrawal_shipping_fee", length = 50)
    @CsvBindByName(column = "withdrawal_shipping_fee")
    private String withdrawalShippingFee; // 철회시배송비부담여부

    @Lob
    @Column(name = "customer_complaint_board", length = 50)
    @CsvBindByName(column = "customer_complaint_board")
    private String customerComplaintBoard; // 고객불만게시판운영

    @Lob
    @Column(name = "membership_cancellation", length = 50)
    @CsvBindByName(column = "membership_cancellation")
    private String membershipCancellation; // 회원탈퇴방법

    @Lob
    @Column(name = "site_establishment_year")
    @CsvBindByName(column = "site_establishment_year")
    private String siteEstablishmentYear; // 사이트개설년도

    @Lob
    @Column(name = "monitoring_date")
    @CsvBindByName(column = "monitoring_date")
    private String monitoringDate; // 모니터링 날짜

}
