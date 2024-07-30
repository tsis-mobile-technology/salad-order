package com.tsis.salad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tsis.hello.controller.MemberForm;
import com.tsis.hello.domain.Member;
import com.tsis.salad.domain.Admin;
import com.tsis.salad.service.AdminService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    private final AdminService adminService;

    @Autowired
    public LoginController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("contextPath", "/");
        return "login/login";
    }

    @PostMapping("/loginCheck")
    public String loginCheck(@RequestBody LoginForm form) {
        //TODO: process POST request
        Admin admin = adminService.findAdminById(form.getUserid());      
        // admin.setId(form.getUserid());
        // admin.setPwd(form.getPwd());

        //TODO: Dtabase administrator check
       

        //TODO:DB check 실패이면 로그인 실패 페이지로 리다이렉트
        //return "redirect:/login";
        return "redirect:/salad/orderList";
    }
    
}
