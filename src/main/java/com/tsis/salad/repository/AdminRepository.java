package com.tsis.salad.repository;

import java.util.List;
import java.util.Optional;

import com.tsis.salad.domain.Admin;

public interface AdminRepository {
    Admin save(Admin admin);
    Optional<Admin> findByIndex(Long index);
    Optional<Admin> findById(String id);
    Optional<Admin> findByName(String name);
    List<Admin> findAll();
}
