package com.cinemamate.cinema_mate.core.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

    @Column(name = "created_at",nullable = false,updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "lastmodified_at")
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
}
