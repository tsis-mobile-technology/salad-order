package com.tsis.hello.controller;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

public class TbFoodOrderHistForm {

    private Long seq; // seq bigint NOT NULL AUTO_INCREMENT
    private String orderNo; // order_no varchar(100) NOT NULL COMMENT '주문번호'
    private String name; // name varchar(100) NOT NULL COMMENT '성명'
    private String id; // id varchar(30) NOT NULL COMMENT '사번'
    private String dept; // dept varchar(100) NOT NULL COMMENT '부서'
    private char orderMenu; // order_menu char(1) NOT NULL COMMENT '샐러드:A, 하프:B'
    private int orderCnt; // order_cnt int NOT NULL COMMENT '주문수량'
    private LocalDateTime createdAt; // created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '예약일시'
    private char orderStatus; // order_status char(1) NOT NULL COMMENT '주문상태 예약:R, 수령:Y, 미수령:N'
    private LocalDateTime updatedAt; // updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수령일시'

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public char getOrderMenu() {
        return orderMenu;
    }

    public void setOrderMenu(char orderMenu) {
        this.orderMenu = orderMenu;
    }

    public int getOrderCnt() {
        return orderCnt;
    }

    public void setOrderCnt(int orderCnt) {
        this.orderCnt = orderCnt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public char getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(char orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "MemberForm{" +
                "name='" + name + '\'' +
                '}';
    }
}
