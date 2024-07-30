package com.tsis.salad.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tsis.salad.domain.Admin;

@Repository
public class MemoryAdminRepository implements AdminRepository {
    private static Map<Long, Admin> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Admin save(Admin admin) {
        admin.setIndex(++sequence);
        store.put(admin.getIndex(), admin);
        return admin;
    }

    @Override
    public Optional<Admin> findByIndex(Long index) {
        return Optional.ofNullable(store.get(index));
    }

    @Override
    public Optional<Admin> findById(String id) {
        return store.values().stream()
                .filter(admin -> admin.getId().equals(id))
                .findAny();
    }

    @Override
    public Optional<Admin> findByName(String name) {
        return store.values().stream()
                .filter(admin -> admin.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Admin> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
