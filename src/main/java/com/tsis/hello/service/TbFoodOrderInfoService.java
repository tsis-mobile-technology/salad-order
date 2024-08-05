package com.tsis.hello.service;

import com.tsis.hello.domain.TbFoodOrderInfo;
import com.tsis.hello.repository.TbFoodOrderInfoRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// @Service
@Transactional //jpa case
public class TbFoodOrderInfoService {

    private final TbFoodOrderInfoRepository tbFoodOrderInfoRepository;

    // @Autowired
    public TbFoodOrderInfoService(TbFoodOrderInfoRepository tbFoodOrderInfoRepository) {
        this.tbFoodOrderInfoRepository = tbFoodOrderInfoRepository;
    }

    /**
     * 회원가입
     */
    public Long join(TbFoodOrderInfo tbFoodOrderInfo) {
        //같은 이름이 있는 중복 회원 가입 X
        validateDuplicateTbFoodOrderInfo(tbFoodOrderInfo);
        tbFoodOrderInfoRepository.save(tbFoodOrderInfo);
        return tbFoodOrderInfo.getSeq();
    }

    private void validateDuplicateTbFoodOrderInfo(TbFoodOrderInfo tbFoodOrderInfo) {
        Optional<TbFoodOrderInfo> result = tbFoodOrderInfoRepository.findByReceiveDate(tbFoodOrderInfo.getReceiveDate());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
     * 전체 회원 조회
     */
    public List<TbFoodOrderInfo> findTbFoodOrderInfos() {
        return tbFoodOrderInfoRepository.findAll();
    }

    public Optional<TbFoodOrderInfo> findOne(Long seq) {
        return tbFoodOrderInfoRepository.findBySeq(seq);
    }
}
