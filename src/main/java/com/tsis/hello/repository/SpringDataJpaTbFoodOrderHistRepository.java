package com.tsis.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tsis.hello.domain.TbFoodOrderHist;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SpringDataJpaTbFoodOrderHistRepository extends JpaRepository<TbFoodOrderHist, Long>, TbFoodOrderHistRepository{

    @Override
    Optional<TbFoodOrderHist> findByOrderNo(String orderNo);
    Optional<TbFoodOrderHist> findByName(String name);
    // Optional<TbFoodOrderHist> findById(Long id);
    Optional<TbFoodOrderHist> findByDept(String dept);
    // Optional<TbFoodOrderHist> findByOrderMenu(String orderMenu);
    // Optional<TbFoodOrderHist> findByOrderCnt(int orderCnt);
    Optional<TbFoodOrderHist> findByOrderACnt(int orderACnt);
    Optional<TbFoodOrderHist> findByOrderBCnt(int orderBCnt);
    List<TbFoodOrderHist> findByCreatedAt(LocalDateTime createdAt);
    Optional<TbFoodOrderHist> findByOrderStatus(String orderStatus);
    Optional<TbFoodOrderHist> findByUpdatedAt(LocalDateTime updatedAt);
}
