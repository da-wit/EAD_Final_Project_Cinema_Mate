package com.cinemamate.cinema_mate.core.security;

import com.cinemamate.cinema_mate.admin.entity.Admin;
import com.cinemamate.cinema_mate.admin.repository.AdminRepository;
import com.cinemamate.cinema_mate.admin.services.IAdminService;
import com.cinemamate.cinema_mate.admin.services.adminServiceImpl.AdminService;
import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import com.cinemamate.cinema_mate.cinema.exceptions.CinemaExceptions;
import com.cinemamate.cinema_mate.cinema.repository.CinemaRepository;
import com.cinemamate.cinema_mate.cinema.services.ICinemaService;
import com.cinemamate.cinema_mate.cinema.services.cinemaServiceImpl.CinemaService;
import com.cinemamate.cinema_mate.core.constant.Role;
import com.cinemamate.cinema_mate.user.entity.User;
import com.cinemamate.cinema_mate.user.exceptions.UserExceptions;
import com.cinemamate.cinema_mate.user.repository.UserRepository;
import com.cinemamate.cinema_mate.user.services.IUserService;
import com.cinemamate.cinema_mate.user.services.userServiceImpl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements ICustomUserDetailsService {

//    private final IUserService userService;
//    private final ICinemaService cinemaService;
//    private final IAdminService adminService;
    private  final UserRepository userRepository;
    private final CinemaRepository cinemaRepository;
    private final AdminRepository adminRepository;

    @Override
    public CustomUserDetails loadUser(String username, Role role) {
        return loadUserByUsernameAndRole(username,role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUser(username,null);
    }

    @Override
    public CustomUserDetails loadUserByUsernameRoleAndId(String username, Role role, String id) throws UsernameNotFoundException{

        if(role != null && id != null){
            if(role == Role.USER){
                User user=  userRepository.findUserById(id);
                if(user == null){
                    throw UserExceptions.notFound(id);
                }
                return user;
            } else if (role == Role.CINEMA) {
                return cinemaRepository.findCinemaById(id).orElseThrow(() -> CinemaExceptions.notFound(id));
            } else if (role == Role.ADMIN) {
                return adminRepository.getAdminById(id).orElseThrow(() -> new RuntimeException("not found"));
            }
        } else if (role == null){
            System.out.println("with role null");
            System.out.println(username);

            User user = userRepository.findByUsername(username);
            if(user != null){
                return user;
            }

            Cinema cinema =cinemaRepository.findCinemaByCinemaName(username).orElse(null);
            if(cinema != null){
                return  cinema;
            }
            Admin admin = adminRepository.findByUsername(username).orElse(null);
            if(admin !=null){
                return admin;
            }

        }else if (role == Role.USER){
            User user = userRepository.findByUsername(username);
//            System.out.println(username);
//            System.out.println(role);
            if(user == null){
                throw UserExceptions.usernameNotFound(username);
            }
            return user;
        }
        else if (role == Role.CINEMA){
//            // ToDo use the Cinema repository here
            Cinema cinema = cinemaRepository.findCinemaByCinemaName(username).orElse(null);
            if(cinema == null) {
                throw CinemaExceptions.cinemaNameNotFound(username);
            }
            return cinema;
        } else if (role == Role.ADMIN) {
            // ToDo use the User repository here
            Admin admin = adminRepository.findByUsername(username).orElse(null);
            if(admin !=null){
                return admin;
            }
        };

        throw new UsernameNotFoundException("User/Cinema not found with username: " + username);
    }




    public CustomUserDetails loadUserByUsernameAndRole(String username, Role role) throws UsernameNotFoundException{

        if (role == null){
            // ToDo try to find the user in either user or cinema talbe
            // user implmented
            System.out.println("with role null");
            System.out.println(username);

            User user = userRepository.findByUsername(username);
            if(user != null){
                return user;
            }

            Cinema cinema =cinemaRepository.findCinemaByCinemaName(username).orElse(null);
            if(cinema != null){
                return  cinema;
            }
            Admin admin = adminRepository.findByUsername(username).orElse(null);
            if(admin !=null){
                return admin;
            }

        }else if (role == Role.USER){
            User user = userRepository.findByUsername(username);
            System.out.println(username);
            System.out.println(role);
            if(user == null){
                UserExceptions.usernameNotFound(username);
            }
            return user;
        }
        else if (role == Role.CINEMA){
            // ToDo use the Cinema repository here
            Cinema cinema = cinemaRepository.findCinemaByCinemaName(username).orElse(null);
            if(cinema == null) {
                throw CinemaExceptions.cinemaNameNotFound(username);
            }
            return cinema;
        } else if (role == Role.ADMIN) {
            // ToDo use the User repository here
            Admin admin = adminRepository.findByUsername(username).orElse(null);
            if(admin !=null){
                return admin;
            }
        };

        throw new UsernameNotFoundException("User/Cinema not found with username: " + username);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return loadUserByUsernameAndRole(username, null);
//    }
//
//    public UserDetails loadUserByUsernameAndRole(String username, Role role) throws UsernameNotFoundException{
//
//        if (role == null){
//            // ToDo try to find the user in either user or cinema talbe
//            // user implmented
//            System.out.println("with role null");
//            System.out.println(username);
//
//            User user = userService.getUser(username);
//            if(user != null){
//                return user;
//            }
//
//            Cinema cinema =cinemaService.getCinema(username);
//            if(cinema != null){
//                return  cinema;
//            }
//            Admin admin = adminService.getAdmin(username);
//            if(admin !=null){
//                return admin;
//            }
//
//        }else if (role == Role.USER){
//            User user = userService.getUser(username);
//            System.out.println(username);
//            System.out.println(role);
//            if(user == null){
//                UserExceptions.usernameNotFound(username);
//            }
//            return user;
//        }else if (role == Role.CINEMA){
//            // ToDo use the Cinema repository here
//            Cinema cinema = cinemaService.getCinema(username);
//            if(cinema == null) {
//                CinemaExceptions.cinemaNameNotFound(username);
//            }
//            return cinema;
//        } else if (role == Role.ADMIN) {
//            // ToDo use the User repository here
//            Admin admin = adminService.getAdmin(username);
//            if(admin !=null){
//                return admin;
//            }
//        };
//
//        throw new UsernameNotFoundException("User/Cinema not found with username: " + username);
//    }
}
