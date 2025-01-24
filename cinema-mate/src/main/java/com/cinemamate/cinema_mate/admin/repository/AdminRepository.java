package com.cinemamate.cinema_mate.admin.repository;

import com.cinemamate.cinema_mate.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    boolean existsByUsername(String username);
    Optional<Admin> findByUsername(String username);

    Optional<Admin> getAdminById(String id);
}
