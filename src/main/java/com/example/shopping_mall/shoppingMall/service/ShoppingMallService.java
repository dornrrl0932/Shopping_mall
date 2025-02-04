package com.example.shopping_mall.shoppingMall.service;

import com.example.shopping_mall.entity.shoppingMall.ShoppingMall;
import com.example.shopping_mall.shoppingMall.dto.ShoppingMallDto;
import com.example.shopping_mall.shoppingMall.repository.ShoppingMallRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingMallService {

    private final ShoppingMallRepository shoppingMallRepository;

    //전체평가 조회
    public List<ShoppingMallDto> overallRatingInquiry(int overallRating) {

        //입력받은 별점 확인
        if(0 > overallRating || overallRating > 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "평가 별점은 최소 0점, 최대 3점입니다.");
        }

        //ShoppingMall 조회
        List<ShoppingMall> shoppingMallList = shoppingMallRepository.findAllByOverallRating(overallRating);

        // ShoppingMall -> ShoppingMallDto로 변환
        List<ShoppingMallDto> shoppingMallDtos = shoppingMallList.stream().map(shoppingMall ->
                        new ShoppingMallDto(
                                shoppingMall.getShoppingMallId(),
                                shoppingMall.getBusinessName(),
                                shoppingMall.getStoreName(),
                                shoppingMall.getOverallRating(),
                                shoppingMall.getDomainName(),
                                shoppingMall.getPhoneNumber(),
                                shoppingMall.getOperatorEmail(),
                                shoppingMall.getBusinessStatus(),
                                shoppingMall.getFirstReportDate(),
                                shoppingMall.getMonitoringDate()))
                .collect(Collectors.toList());

        return shoppingMallDtos;
    }

    //업소 상태 조회
    public List<ShoppingMallDto> businessStatusInquiry(String businessStatus) {

        List<ShoppingMall> shoppingMallList = shoppingMallRepository.findAllByBusinessStatusOrThrowException(businessStatus);

        // ShoppingMall -> ShoppingMallDto로 변환
        List<ShoppingMallDto> shoppingMallDtos = shoppingMallList.stream().map(shoppingMall ->
                        new ShoppingMallDto(
                                shoppingMall.getShoppingMallId(),
                                shoppingMall.getBusinessName(),
                                shoppingMall.getStoreName(),
                                shoppingMall.getOverallRating(),
                                shoppingMall.getDomainName(),
                                shoppingMall.getPhoneNumber(),
                                shoppingMall.getOperatorEmail(),
                                shoppingMall.getBusinessStatus(),
                                shoppingMall.getFirstReportDate(),
                                shoppingMall.getMonitoringDate()))
                .collect(Collectors.toList());

        return shoppingMallDtos;
    }

    //모니터링 날짜 내림차순 조회
    public List<ShoppingMallDto> monitoringDateDescendingOrder() {

        List<ShoppingMall> shoppingMallList = shoppingMallRepository.findAllByOrderByMonitoringDateDesc();

        // ShoppingMall -> ShoppingMallDto로 변환
        List<ShoppingMallDto> shoppingMallDtos = shoppingMallList.stream().map(shoppingMall ->
                        new ShoppingMallDto(
                                shoppingMall.getShoppingMallId(),
                                shoppingMall.getBusinessName(),
                                shoppingMall.getStoreName(),
                                shoppingMall.getOverallRating(),
                                shoppingMall.getDomainName(),
                                shoppingMall.getPhoneNumber(),
                                shoppingMall.getOperatorEmail(),
                                shoppingMall.getBusinessStatus(),
                                shoppingMall.getFirstReportDate(),
                                shoppingMall.getMonitoringDate()))
                .collect(Collectors.toList());

        return shoppingMallDtos;
    }

    private static final String CSV_FILE_PATH = "C:\\Users\\bette\\Downloads\\seoul.csv";  // 실제 파일 경로로 변경

    public void insertShoppingMallFromCSV() {
        try (Reader reader = new FileReader(CSV_FILE_PATH)) {
            CsvToBean<ShoppingMall> csvToBean = new CsvToBeanBuilder<ShoppingMall>(reader)
                    .withType(ShoppingMall.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<ShoppingMall> shoppingMallList = csvToBean.parse();

//            // DTO를 엔티티로 변환 후 저장
//            List<ShoppingMall> entities = shoppingMallList.stream()
//                    .map(dto -> new ShoppingMall(
//                            dto.getBusinessName(),
//                            dto.getStoreName(),
//                            dto.getDomainName(),
//                            dto.getPhoneNumber(),
//                            dto.getOperatorEmail(),
//                            dto.getBusinessStatus(),
//                            dto.getOverallRating(),
//                            dto.getFirstReportDate(),
//                            dto.getMonitoringDate()
//                    ))
//                    .collect(Collectors.toList());

            shoppingMallRepository.saveAll(shoppingMallList);  // 한 번에 저장하여 성능 향상

        } catch (IOException e) {
            log.error("CSV 파일을 읽는 중 오류 발생", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "CSV 파일을 읽는 중 오류 발생");
        }
    }

}
