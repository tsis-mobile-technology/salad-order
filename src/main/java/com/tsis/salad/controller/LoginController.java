package com.tsis.salad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tsis.hello.controller.MemberForm;
import com.tsis.hello.domain.Member;
import com.tsis.salad.domain.Admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("contextPath", "/");
        return "login/login";
    }

    @PostMapping("/loginCheck")
    public String loginCheck(LoginForm form) {
        //TODO: process POST request
        Admin admin = new Admin();
        admin.setId(form.getUserid());
        admin.setPwd(form.getPwd());

        return "redirect:/";
    }
    
}
