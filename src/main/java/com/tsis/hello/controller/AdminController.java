package com.tsis.hello.controller;

import java.lang.StackWalker.Option;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.tsis.hello.domain.Admin;
import com.tsis.hello.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/check-session")
    public ResponseEntity<Admin> checkSession(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if (session != null) {
                Long id = (Long) session.getAttribute("adminId");
                Optional<Admin> admin = adminService.findOne(id);
                if (admin.isPresent())
                    return ResponseEntity.ok(admin.get());
                else
                    return ResponseEntity.notFound().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        
    }

    @GetMapping("/admin/login")
    public String loginForm() {
        return "/admin/login";
    }

    @PostMapping("/admin/login")
    public ResponseEntity<Admin> login(AdminForm form, HttpServletRequest request) throws NoSuchElementException {
        try {
            Optional<Admin> admin = adminService.findOne(form.getUserId(), form.getUserPwd());

            if (admin.isPresent()) {
                // Session 관리
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin.get());
                session.setAttribute("adminId", admin.get().getId());
                session.setAttribute("userName", admin.get().getUserName());
                // 현재 시간을 가져와서 YYYY-MM-DD HH:MI:SSS 포맷으로 String 포맷으로 가져옴
                String loginTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")); // Use LocalDateTime.now()
                session.setAttribute("loginTime", loginTime);
                // session.setAttribute("loginTime", time.now().format("YYYY-MM-DD HH:MI:SSS"));

                return ResponseEntity.ok(admin.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        
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
        
        return "redirect:/admins";
    }

    @GetMapping("/admins")
    public String list(Model model) {
        List<Admin> admins = adminService.findAdmins();
        model.addAttribute("admins", admins);
        model.addAttribute("totalCount", admins.size());
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

    @DeleteMapping("/admin/password/{adminId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable("adminId") Long id) {
        // adminId를 이용하여 데이터베이스에서 해당 관리자 정보를 가져옵니다.
        Optional<Admin> admin = adminService.findOne(id); 

        if (admin == null) {
            return ResponseEntity.notFound().build();
        } 

        adminService.deleteAdmin(id);

        // 암호화된 비밀번호를 그대로 반환합니다.
        return ResponseEntity.ok(admin.get().getUserPwd()); 
    }
    
    
    
}
