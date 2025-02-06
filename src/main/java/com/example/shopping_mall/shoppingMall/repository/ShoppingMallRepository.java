package com.example.shopping_mall.shoppingMall.repository;

import com.example.shopping_mall.entity.shoppingMall.ShoppingMall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ShoppingMallRepository extends JpaRepository<ShoppingMall, Long> {

    @Query("" +
            "SELECT s FROM ShoppingMall s " +
            "WHERE (s.overallRating = :overallRating OR :overallRating IS NULL) " +
            "AND (s.businessStatus = :businessStatus OR :businessStatus IS NULL) " +
            "ORDER BY s.monitoringDate DESC" +
    "")
    List<ShoppingMall> findAllByOverallRatingAndBusinessStatusOrderByMonitoringDateDesc(Integer overallRating, String businessStatus);

    @Query("" +
            "SELECT s FROM ShoppingMall s " +
            "WHERE (s.overallRating = :overallRating OR :overallRating IS NULL) " +
            "AND (s.businessStatus = :businessStatus OR :businessStatus IS NULL) " +
            "ORDER BY s.monitoringDate DESC" +
            "")
    Page<ShoppingMall> findAllByOverallRatingAndBusinessStatusOrderByMonitoringDateDesc(Integer overallRating, String businessStatus,Pageable pageable);
}
