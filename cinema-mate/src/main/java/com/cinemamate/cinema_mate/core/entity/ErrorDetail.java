package com.cinemamate.cinema_mate.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDetail {
    private String message;
    private String details;
    private Date timestamp;
}
