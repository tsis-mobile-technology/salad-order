package com.tsis.hello.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_FOOD_ORDER_INFO")
public class TbFoodOrderInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq; // seq bigint NOT NULL AUTO_INCREMENT
    @Column(name = "receive_date")
    private LocalDateTime receiveDate; // receive_date datetime NOT NULL COMMENT '수령일'
    @Column(name = "week_menu_img")
    private String weekMenuImg; // week_menu_img varchar(1024) NOT NULL COMMENT '주간메뉴이미지'
    @Column(name = "menu_a_cnt")
    private int menuACnt; // menu_a_cnt int NOT NULL COMMENT '샐러드 남은수량'
    @Column(name = "menu_b_cnt")
    private int menuBCnt; // menu_b_cnt int NOT NULL COMMENT '하프 남은수량'
    @Column(name = "created_at")
    private LocalDateTime createdAt; // created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '등록일시'
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'


    // seq에 대한 getter 및 setter
    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    // receive_date에 대한 getter 및 setter
    public LocalDateTime getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDateTime receiveDate) {
        this.receiveDate = receiveDate;
    }

    // weekMenuImg에 대한 getter 및 setter
    public String getWeekMenuImg() {
        return weekMenuImg;
    }

    public void setWeekMenuImg(String weekMenuImg) {
        this.weekMenuImg = weekMenuImg;
    }

    // menuACnt에 대한 getter 및 setter
    public int getMenuACnt() {
        return menuACnt;
    }

    public void setMenuACnt(int menuACnt) {
        this.menuACnt = menuACnt;
    }

    // menuBCnt에 대한 getter 및 setter
    public int getMenuBCnt() {
        return menuBCnt;
    }

    public void setMenuBCnt(int menuBCnt) {
        this.menuBCnt = menuBCnt;
    }

    // createdAt에 대한 getter 
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // updatedAt에 대한 getter
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
