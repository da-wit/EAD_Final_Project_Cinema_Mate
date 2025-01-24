package com.cinemamate.cinema_mate.user.entity;

import com.cinemamate.cinema_mate.booking.entity.Booking;
import com.cinemamate.cinema_mate.core.base.AuditableEntity;
import com.cinemamate.cinema_mate.core.constant.Role;
import com.cinemamate.cinema_mate.core.security.CustomUserDetails;
import com.cinemamate.cinema_mate.watchlist.entity.WatchList;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "user"
)
public class User extends AuditableEntity implements CustomUserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)", nullable = false, unique = true)
    private String id;

    @Setter
    @Column(name = "username",nullable = false)
    private String username;

    @Setter
    @Column(name = "email",nullable = false)
    private String email;

    @Setter
    @Column(name = "password",nullable = false)
    private String password;

    @Setter
    @Column(name = "profile",nullable = true)
    private String profileImage;
    @Getter
    @Setter
    @Column(name = "isActive",nullable = false)
    private boolean isActive=true;

    @Setter
    @Column(name = "role",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<WatchList> watchLists;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Booking> bookings;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public String getId() {
        return id;
    }
}
