package com.cinemamate.cinema_mate.admin.services.adminServiceImpl;

import com.cinemamate.cinema_mate.admin.entity.Admin;
import com.cinemamate.cinema_mate.admin.repository.AdminRepository;
import com.cinemamate.cinema_mate.admin.services.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {
    private final AdminRepository adminRepository;

    @Override
    public Admin getAdmin(String username) {
        return adminRepository.findByUsername(username).orElse(null);
    }

    @Override
    public boolean adminExists(String username) {
        return adminRepository.existsByUsername(username);
    }

    @Override
    public Admin getAdminById(String id) {
        return adminRepository.getAdminById(id).orElseThrow(() -> new RuntimeException("not found"));
    }
}
