package com.tsis.hello.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.tsis.hello.domain.TbFoodOrderHist;

public interface TbFoodOrderHistRepository {

    TbFoodOrderHist save(TbFoodOrderHist tbFoodOrderHist);
    Optional<TbFoodOrderHist> findBySeq(Long seq);
    Optional<TbFoodOrderHist> findByOrderNo(String orderNo);
    Optional<TbFoodOrderHist> findByName(String name);
    Optional<TbFoodOrderHist> findById(Long id);
    Optional<TbFoodOrderHist> findByDept(String dept);
    Optional<TbFoodOrderHist> findByOrderMenu(String orderMenu);
    Optional<TbFoodOrderHist> findByOrderCnt(int orderCnt);
    List<TbFoodOrderHist> findByCreatedAt(LocalDateTime createdAt);
    Optional<TbFoodOrderHist> findByOrderStatus(String orderStatus);
    Optional<TbFoodOrderHist> findByUpdatedAt(LocalDateTime updatedAt);
    List<TbFoodOrderHist> findAll();

}
