package com.tsis.salad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsis.salad.domain.Admin;
import com.tsis.salad.repository.AdminRepository;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin findAdminById(String id) {
        return adminRepository.findById(id).orElse(null);
    }
}
