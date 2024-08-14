package com.tsis.hello.controller;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.tsis.hello.domain.Admin;
import com.tsis.hello.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/admin/password/{adminId}")
    public ResponseEntity<String> getAdminPassword(@PathVariable("adminId") Long id) {
        // adminId를 이용하여 데이터베이스에서 해당 관리자 정보를 가져옵니다.
        Optional<Admin> admin = adminService.findOne(id); 

        if (admin == null) {
            return ResponseEntity.notFound().build();
        }

        // 암호화된 비밀번호를 그대로 반환합니다.
        return ResponseEntity.ok(admin.get().getUserPwd()); 
    }
    
}
