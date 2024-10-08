package com.tsis.hello.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class TbFoodOrderHistForm {

    private Long seq; // seq bigint NOT NULL AUTO_INCREMENT
    private String orderNo; // order_no varchar(100) NOT NULL COMMENT '주문번호'
    private String name; // name varchar(100) NOT NULL COMMENT '성명'
    private String id; // id varchar(30) NOT NULL COMMENT '사번'
    private String dept; // dept varchar(100) NOT NULL COMMENT '부서'
    // private char orderMenu; // order_menu char(1) NOT NULL COMMENT '샐러드:A, 하프:B'
    // private int orderCnt; // order_cnt int NOT NULL COMMENT '주문수량'
    private int orderACnt; // order_a_cnt int NOT NULL COMMENT 'A주문수량'
    private int orderBCnt; // order_b_cnt int NOT NULL COMMENT 'B주문수량'
    private LocalDateTime createdAt; // created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '예약일시'
    private char orderStatus; // order_status char(1) NOT NULL COMMENT '주문상태 예약:R, 수령:Y, 미수령:N'
    private LocalDateTime updatedAt; // updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수령일시'
    private String search_type;
    private String search_value;
    private LocalDateTime date_start;
    private LocalDateTime date_end;

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

    // public char getOrderMenu() {
    //     return orderMenu;
    // }

    // public void setOrderMenu(char orderMenu) {
    //     this.orderMenu = orderMenu;
    // }

    // public int getOrderCnt() {
    //     return orderCnt;
    // }

    // public void setOrderCnt(int orderCnt) {
    //     this.orderCnt = orderCnt;
    // }
    public int getOrderACnt() {
        return orderACnt;
    }

    public void setOrderACnt(int orderACnt) {
        this.orderACnt = orderACnt;
    }

    public int getOrderBCnt() {
        return orderBCnt;
    }

    public void setOrderBCnt(int orderBCnt) {
        this.orderBCnt = orderBCnt;
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

    public String getSearch_type() {
        return search_type;
    }

    public void setSearch_type(String search_type) {
        this.search_type = search_type;
    }

    public String getSearch_value() {
        return search_value;
    }

    public void setSearch_value(String search_value) {
        this.search_value = search_value;
    }

    public LocalDateTime getDate_start() {
        return date_start;
    }

    public void setDate_start(LocalDateTime date_start) {
        this.date_start = date_start;
    }

    public LocalDateTime getDate_end() {
        return date_end;
    }

    public void setDate_end(LocalDateTime date_end) {
        this.date_end = date_end;
    }

    @Override
    public String toString() {
        return "MemberForm{" +
                "name='" + name + '\'' +
                '}';
    }
}
