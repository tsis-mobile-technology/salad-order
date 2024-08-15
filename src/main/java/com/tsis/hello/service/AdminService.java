package com.tsis.hello.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tsis.hello.domain.Admin;
import com.tsis.hello.repository.AdminRepository;

@Transactional
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Admin 계정 등록
     */
    public Long join(Admin admin) {
        validateDuplicateAdmin(admin);
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(admin.getUserPwd());
        admin.setUserPwd(encodedPassword);
        adminRepository.save(admin);
        return admin.getId();
    }

    /**
     * Admin 계정 삭제
     * @param admin
     */
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    /** Admin.userId가 중복인지 체크 
     * 
     */
    private void validateDuplicateAdmin(Admin admin) {
        Optional<Admin> result = adminRepository.findByUserId(admin.getUserId());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 Admin ID 입니다.");
        });
    }

    /**
     * 전체 Admin 조회
     */
    public List<Admin> findAdmins() {
        return adminRepository.findAll();
    }
    
    /**
     * Admin 정보 조회
     */
    public Optional<Admin> findOne(Long id) {
        return adminRepository.findById(id);
    }

    /**
     * Admin userId, userPwd를 받아 조회
     */
    public Optional<Admin> findOne(String userId, String userPwd) {
        try {
            
            Admin admin = adminRepository.findByUserId(userId).get();
            System.err.println("admin.getUserPwd() : " + admin.getUserPwd());
            System.err.println("passwordEncoder.encode(admin.getUserPwd()) : " + passwordEncoder.encode(admin.getUserPwd()));
            if(passwordEncoder.matches(userPwd, admin.getUserPwd())) {
                System.err.println("Success Check:" + admin.getUserName());
                return Optional.of(admin);
            }

            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }
    

}
