package com.cinemamate.cinema_mate.core.data;

import com.cinemamate.cinema_mate.admin.entity.Admin;
import com.cinemamate.cinema_mate.admin.repository.AdminRepository;
import com.cinemamate.cinema_mate.core.constant.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        // Check if admin user already exists to avoid duplicate seeding
        if(adminRepository.count() ==0){
            Admin admin = Admin.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();
            adminRepository.save(admin);
            System.out.println("Admin user seeded with initial data.");
        }

    }
}
