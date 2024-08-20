package com.tsis.hello.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_FOOD_ORDER_HIST")
public class TbFoodOrderHist {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq; // seq bigint NOT NULL AUTO_INCREMENT
    @Column(name = "order_no")
    private String orderNo; // order_no varchar(100) NOT NULL COMMENT '주문번호'
    private String name; // name varchar(100) NOT NULL COMMENT '성명'
    private String id; // id varchar(30) NOT NULL COMMENT '사번'
    private String dept; // dept varchar(100) NOT NULL COMMENT '부서'
    // @Column(name = "order_menu")
    // private char orderMenu; // order_menu char(1) NOT NULL COMMENT '샐러드:A, 하프:B'
    // @Column(name = "order_cnt")
    // private int orderCnt; // order_cnt int NOT NULL COMMENT '주문수량'
    @Column(name = "order_a_cnt")
    private int orderACnt; // order_a_cnt int NOT NULL COMMENT '샐러드 A 주문 수량
    @Column(name = "order_b_cnt")
    private int orderBCnt; // order_b_cnt int NOT NULL COMMENT '샐러드 B 주문 수량
    @Column(name = "created_at")
    private LocalDateTime createdAt; // created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '예약일시'
    @Column(name = "order_status")
    private char orderStatus; // order_status char(1) NOT NULL COMMENT '주문상태 예약:R, 수령:Y, 미수령:N'
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수령일시'

    // Getter 및 Setter 메서드는 아래에 추가
    // ...
    // seq에 대한 getter 및 setter
    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    // orderNo에 대한 getter 및 setter
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    // name에 대한 getter 및 setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // id에 대한 getter 및 setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // dept에 대한 getter 및 setter
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

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

    // // orderMenu에 대한 getter 및 setter
    // public char getOrderMenu() {
    //     return orderMenu;
    // }

    // public void setOrderMenu(char orderMenu) {
    //     this.orderMenu = orderMenu;
    // }

    // // orderCnt에 대한 getter 및 setter
    // public int getOrderCnt() {
    //     return orderCnt;
    // }

    // public void setOrderCnt(int orderCnt) {
    //     this.orderCnt = orderCnt;
    // }

    // createdAt에 대한 getter
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // orderStatus에 대한 getter 및 setter
    public char getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(char orderStatus) {
        this.orderStatus = orderStatus;
    }

    // updatedAt에 대한 getter 및 setter
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
