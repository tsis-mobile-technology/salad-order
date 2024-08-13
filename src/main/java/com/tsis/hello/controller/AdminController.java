package com.tsis.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.tsis.hello.domain.Admin;
import com.tsis.hello.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin/new")
    public String createForm() {
        return "admin/createAdminForm";
    }

    @PostMapping("/admin/new")
    public String create(AdminForm form) {
        Admin admin = new Admin();
        admin.setUserId(form.getUserId());
        admin.setUserPwd(form.getUserPwd());
        admin.setUserName(form.getUserName());

        adminService.join(admin);
        
        return "redirect:/";
    }

    @GetMapping("/admins")
    public String list(Model model) {
        List<Admin> admins = adminService.findAdmins();
        model.addAttribute("admins", admins);
        return "/admin/adminLists";
    }
    
    
    
}
