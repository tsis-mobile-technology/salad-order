package com.tsis.hello.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.tsis.hello.domain.TbFoodOrderInfo;

public interface TbFoodOrderInfoRepository {
    TbFoodOrderInfo save(TbFoodOrderInfo tbFoodOrderInfo);
    Optional<TbFoodOrderInfo> findBySeq(Long seq);
    Optional<TbFoodOrderInfo> findByReceiveDate(LocalDateTime receiveDate);
    Optional<TbFoodOrderInfo> findByWeekMenuImg(String weekMenuImg);
    Optional<TbFoodOrderInfo> findByMenuACnt(int menuACnt);
    Optional<TbFoodOrderInfo> findByMenuBCnt(int menuBCnt);
    Optional<TbFoodOrderInfo> findByCreatedAt(LocalDateTime createdAt);
    Optional<TbFoodOrderInfo> findByUpdatedAt(LocalDateTime updatedAt);
    List<TbFoodOrderInfo> findAll();
}
