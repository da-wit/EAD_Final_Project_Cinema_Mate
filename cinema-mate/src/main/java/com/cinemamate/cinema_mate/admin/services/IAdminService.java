package com.cinemamate.cinema_mate.admin.services;

import com.cinemamate.cinema_mate.admin.entity.Admin;

public interface IAdminService {
    Admin getAdmin(String username);
    boolean adminExists(String username);
    Admin getAdminById(String id);
}
