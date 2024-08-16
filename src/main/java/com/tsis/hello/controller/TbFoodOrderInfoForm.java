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
    private String search_type;
    private String search_value;
    private String date_start;
    private String date_end;

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

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }
    
    @Override
    public String toString() {
        return "MemberForm{" +
                "name='" + name + '\'' +
                '}';
    }
}
