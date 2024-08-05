package com.tsis.hello.controller;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

public class TbFoodOrderInfoForm {

    private String name;

    private LocalDateTime receiveDate;
    // private String weekMenuImg;
    private MultipartFile weekMenuImg;
    private int menuACnt;
    private int menuBCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDateTime receiveDate) {
        this.receiveDate = receiveDate;
    }

    public MultipartFile getWeekMenuImg() {
        return weekMenuImg;
    }

    public void setWeekMenuImg(MultipartFile weekMenuImg) {
        this.weekMenuImg = weekMenuImg;
    }


    // public String getWeekMenuImg() {
    //     return weekMenuImg;
    // }

    // public void setWeekMenuImg(String weekMenuImg) {
    //     this.weekMenuImg = weekMenuImg;
    // }

    public int getMenuACnt() {
        return menuACnt;
    }

    public void setMenuACnt(int menuACnt) {
        this.menuACnt = menuACnt;
    }

    public int getMenuBCnt() {
        return menuBCnt;
    }

    public void setMenuBCnt(int menuBCnt) {
        this.menuBCnt = menuBCnt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
