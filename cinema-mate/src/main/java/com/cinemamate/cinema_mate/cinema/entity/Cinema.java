package com.cinemamate.cinema_mate.cinema.entity;

import com.cinemamate.cinema_mate.core.base.AuditableEntity;
import com.cinemamate.cinema_mate.core.constant.Role;
import com.cinemamate.cinema_mate.movie.entity.Movie;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "cinema"
)
public class Cinema extends AuditableEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)", nullable = false, unique = true)
    private String id;

    @Setter
    @Column(name = "cinemaname", nullable = false)
    private String cinemaname;

    @Setter
    @Column(name = "email", nullable = false)
    private String email;

    @Setter
    @Column(name = "password", nullable = false)
    private String password;

    @Setter
    @Column(name = "description", nullable = false)
    private String description;

    @Setter
    @Column(name = "isActive", nullable = false)
    private boolean isActive = true;

    @Setter
    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "cinema",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Movie> movies;


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
        return cinemaname;
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
}
