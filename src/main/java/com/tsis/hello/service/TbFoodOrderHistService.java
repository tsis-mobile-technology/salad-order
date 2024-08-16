package com.tsis.hello.service;

import com.tsis.hello.domain.TbFoodOrderHist;
import com.tsis.hello.repository.TbFoodOrderHistRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// @Service
@Transactional //jpa case
public class TbFoodOrderHistService {

    private final TbFoodOrderHistRepository tbFoodOrderHistRepository;

    // @Autowired
    public TbFoodOrderHistService(TbFoodOrderHistRepository tbFoodOrderHistRepository) {
        this.tbFoodOrderHistRepository = tbFoodOrderHistRepository;
    }

    /**
     * 회원가입
     */
    public Long join(TbFoodOrderHist tbFoodOrderHist) {
        //같은 이름이 있는 중복 회원 가입 X
        validateDuplicateTbFoodOrderHist(tbFoodOrderHist);
        tbFoodOrderHistRepository.save(tbFoodOrderHist);
        return tbFoodOrderHist.getSeq();
    }

    private void validateDuplicateTbFoodOrderHist(TbFoodOrderHist tbFoodOrderHist) {
        Optional<TbFoodOrderHist> result = tbFoodOrderHistRepository.findByOrderNo(tbFoodOrderHist.getOrderNo());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 주문번호입니다.");
        });
    }

    /**
     * 전체 회원 조회
     */
    public List<TbFoodOrderHist> findTbFoodOrderHists() {
        return tbFoodOrderHistRepository.findAll();
    }

    /** 예약일자(등록일자) 기준으로 전체 조회 */
    public List<TbFoodOrderHist> findTbFoodOrderHists(LocalDateTime createdAt) {
        return tbFoodOrderHistRepository.findByCreatedAt(createdAt);
    }

    public Optional<TbFoodOrderHist> findOne(Long seq) {
        return tbFoodOrderHistRepository.findBySeq(seq);
    }
}
