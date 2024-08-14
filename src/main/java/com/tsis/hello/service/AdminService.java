package com.tsis.hello.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.tsis.hello.domain.Admin;
import com.tsis.hello.repository.AdminRepository;

@Transactional
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Admin 계정 등록
     */
    public Long join(Admin admin) {
        validateDuplicateAdmin(admin);
        adminRepository.save(admin);
        return admin.getId();
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
    
}
