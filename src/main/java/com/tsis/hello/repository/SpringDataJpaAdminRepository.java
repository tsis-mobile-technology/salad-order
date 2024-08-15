package com.tsis.hello.repository;

import com.tsis.hello.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaAdminRepository extends JpaRepository<Admin, Long>, AdminRepository {

    @Override
    Optional<Admin> findByUserId(String userId);
    Optional<Admin> findByUserPwd(String userPwd);
    Optional<Admin> findByUserName(String userName);
    Optional<Admin> findByUserIdAndUserPwd(String userId, String userPwd);
}
