package com.cinemamate.cinema_mate.user.repository;

import com.cinemamate.cinema_mate.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByUsername(String username);
    User findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<User> findUserByEmail(String email);

    User findUserById(String id);
}
