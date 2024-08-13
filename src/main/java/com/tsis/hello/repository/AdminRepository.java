package com.tsis.hello.repository;

import java.util.List;
import java.util.Optional;

import com.tsis.hello.domain.Admin;

public interface AdminRepository {

    Admin save(Admin admin);
    Optional<Admin> findById(Long id);
    Optional<Admin> findByUserId(String userId);
    Optional<Admin> findByUserPwd(String userPwd);
    Optional<Admin> findByUserName(String userName);
    List<Admin> findAll();
}
